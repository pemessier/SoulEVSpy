package org.hexpresso.soulevspy.obd;

import junit.framework.TestCase;

import org.hexpresso.obd.ObdMessageData;

/**
 * Created by Tyrel Haveman <tyrel@binarypeople.net> on 11/30/2015.
 */
public class AmbientTempMessageFilterTest extends TestCase {
    public void testGetsTemperature() {
        AmbientTempMessageFilter filter = new AmbientTempMessageFilter();
        ObdMessageData messageData = new ObdMessageData("653 00 1E 00 00 00 74 00 00");
        filter.doProcessMessage(messageData);
        assertEquals(18.0, filter.getAmbientTemperature());
    }
}