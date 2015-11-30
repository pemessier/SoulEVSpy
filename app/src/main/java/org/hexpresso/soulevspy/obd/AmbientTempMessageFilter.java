package org.hexpresso.soulevspy.obd;

import org.hexpresso.obd.ObdMessageData;
import org.hexpresso.obd.ObdMessageFilter;

/**
 * Created by Tyrel Haveman <tyrel@binarypeople.net> on 11/30/2015.
 */
public class AmbientTempMessageFilter extends ObdMessageFilter {
    private double ambientTemperature;

    public AmbientTempMessageFilter() {
        super("653");
    }

    @Override
    protected boolean doProcessMessage(ObdMessageData messageData) {
        if ( messageData.getSize() != 8 ) {
            return false;
        }

        ambientTemperature = messageData.getDataByte(5) / 2.0 - 40.0;

        return true;
    }

    public double getAmbientTemperature() {
        return ambientTemperature;
    }
}
