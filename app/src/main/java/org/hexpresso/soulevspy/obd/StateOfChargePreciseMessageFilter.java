package org.hexpresso.soulevspy.obd;

import org.hexpresso.obd.ObdMessageData;
import org.hexpresso.obd.ObdMessageFilter;

import java.util.ArrayList;

/**
 * Created by pemessier on 2015-10-13.
 */
public class StateOfChargePreciseMessageFilter extends ObdMessageFilter {

    double mSOCValue = 0.0;

    public StateOfChargePreciseMessageFilter() {
        super("598");
    }

    @Override
    protected boolean doProcessMessage(ObdMessageData messageData) {
        ArrayList<String> data = messageData.getData();
        if (data.size() != 8) {
            return false;
        }

        // Full SOC value is Little Endian on bytes 4-5
        mSOCValue = ( ( messageData.getDataByte(5) << 8 ) +
                      ( messageData.getDataByte(4) ) ) / 256.0;

        return true;
    }

    public double getSOC() {
        return mSOCValue;
    }

}
