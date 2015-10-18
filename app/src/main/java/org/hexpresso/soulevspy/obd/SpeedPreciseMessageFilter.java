package org.hexpresso.soulevspy.obd;

import org.hexpresso.obd.ObdMessageData;
import org.hexpresso.obd.ObdMessageFilter;

import java.util.ArrayList;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-13.
 */
public class SpeedPreciseMessageFilter extends ObdMessageFilter {

    double mSpeedKmH = 0.0;

    public SpeedPreciseMessageFilter() {
        super("4F2");
    }

    protected boolean doProcessMessage(ObdMessageData messageData) {
        ArrayList<String> data = messageData.getData();
        if (data.size() != 8) {
            return false;
        }

        mSpeedKmH = (messageData.getDataByte(1) | ((messageData.getDataByte(2) & 0x80) << 1)) / 2.0;

        return true;
    }

    public double getSpeedInKmH() {
        return mSpeedKmH;
    }
}
