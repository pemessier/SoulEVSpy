package org.hexpresso.soulevspy;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import org.hexpresso.soulevspy.util.BatteryManagementSystemParser;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-15.
 *
 * Note : this structure needs to be serializable into a database so that we can plot graphs later.
 */
public class ObdBmsTest extends AndroidTestCase {
    public void test2101() {
        final String msg2101 = new String("7EC 10 3D 61 01 FF FF FF FF \n" +
                                          "7EA 10 0E 61 01 F0 00 00 00 \n" +
                                          "7EC 21 15 23 28 1E C8 03 00 \n" +
                                          "7EA 21 ED 05 02 03 00 00 00 \n" +
                                          "7EC 22 1E 0C DD 0E 0D 0E 0D \n" +
                                          "7EA 22 00 00 00 00 00 00 00 \n" +
                                          "7EC 23 0D 0D 0C 00 0F AB 34 \n" +
                                          "7EC 24 AB 43 00 00 84 00 00 \n" +
                                          "7EC 25 44 D4 00 00 49 F8 00 \n" +
                                          "7EC 26 00 19 B3 00 00 1A EA \n" +
                                          "7EC 27 00 09 EC 96 45 01 45 \n" +
                                          "7EC 28 00 00 00 00 03 E8 00 \n");

        BatteryManagementSystemParser parser = new BatteryManagementSystemParser();
        Assert.assertTrue(parser.parseMessage2101(msg2101));

        BatteryManagementSystemParser.Data parsedData = parser.getParsedData();
        Assert.assertEquals(10.5, parsedData.stateOfCharge);
        Assert.assertEquals(329.3, parsedData.batteryDcVoltage);
        Assert.assertEquals(3.42, parsedData.maxCellVoltage);
        Assert.assertEquals(3.42, parsedData.minCellVoltage);
        Assert.assertEquals(90.0, parsedData.availableChargePower);
        Assert.assertEquals(78.8, parsedData.availableDischargePower);
        Assert.assertEquals(13.2, parsedData.auxiliaryBatteryVoltage, 1e-6);
        Assert.assertEquals(14, parsedData.batteryModuleTemperature[0]);
        Assert.assertEquals(13, parsedData.batteryModuleTemperature[1]);
        Assert.assertEquals(14, parsedData.batteryModuleTemperature[2]);
        Assert.assertEquals(13, parsedData.batteryModuleTemperature[3]);
        Assert.assertEquals(13, parsedData.batteryModuleTemperature[4]);
        Assert.assertEquals(13, parsedData.batteryModuleTemperature[5]);
        Assert.assertEquals(12, parsedData.batteryModuleTemperature[6]);
        Assert.assertEquals(15, parsedData.batteryModuleTemperature[7]);
        Assert.assertEquals(15, parsedData.batteryModuleTemperature[7]);
        Assert.assertEquals(52, parsedData.maxCellVoltageNo);
        Assert.assertEquals(1762.0, parsedData.accumulativeChargeCurrent);
        Assert.assertEquals(1893.6, parsedData.accumulativeDischargeCurrent, 1e-6);
        Assert.assertEquals(657.9, parsedData.accumulativeChargePower, 1e-6);
        Assert.assertEquals(689.0, parsedData.accumulativeDischargePower, 1e-6);
        Assert.assertEquals(650390, parsedData.accumulativeOperatingTime);
    }

    public void test2102() {
        final String msg2102 = new String();
    }

    public void test2103() {
        final String msg2103 = new String();
    }

    public void test2104() {
        final String msg2104 = new String();
    }

    public void test2105() {
        final String msg2105 = new String();
    }
/*
                "\n" +
                ">2102\n" +
                "7EA 10 21 61 02 FF FF 80 00 \n" +
                "7EC 10 26 61 02 FF FF FF FF \n" +
                "7EA 21 83 A2 7D 7E 00 00 00 \n" +
                "7EC 21 AB AB AB AB AB AB AB \n" +
                "7EA 22 00 00 00 04 00 00 00 \n" +
                "7EC 22 AB AB AB AB AB AB AB \n" +
                "7EA 23 00 00 1C 00 22 21 21 \n" +
                "7EC 23 AB AB AB AB AB AB AB \n" +
                "7EA 24 FE 07 FF 07 AE 51 00 \n" +
                "7EC 24 AB AB AB AB AB AB AB \n" +
                "7EC 25 AB AB AB AB 00 00 00 \n" +
                "\n" +
                ">2103\n" +
                "7EA 03 7F 21 12 \n" +
                "7EC 10 26 61 03 FF FF FF FF \n" +
                "7EC 21 AB AB AB AB AB AB AB \n" +
                "7EC 22 AB AB AB AB AB AB AB \n" +
                "7EC 23 AB AB AB AB AB AB AB \n" +
                "7EC 24 AB AB AB AB AB AB AB \n" +
                "7EC 25 AB AB AB AB 00 00 00 \n" +
                "\n" +
                ">2104\n" +
                "7EA 03 7F 21 12 \n" +
                "7EC 10 26 61 04 FF FF FF FF \n" +
                "7EC 21 AB AB AB AB AB AB AB \n" +
                "7EC 22 AB AB AB AB AB AB AB \n" +
                "7EC 23 AB AB AB AB AB AB AB \n" +
                "7EC 24 AB AB AB AB AB AB AB \n" +
                "7EC 25 AB AB AB AB 00 00 00 \n" +
                "\n" +
                ">2105\n" +
                "7EA 03 7F 21 12 \n" +
                "7EC 10 2C 61 05 FF FF FF FF \n" +
                "7EC 21 00 00 00 00 00 0D 0D \n" +
                "7EC 22 0E 00 00 00 00 23 28 \n" +
                "7EC 23 1E C8 00 01 50 0D 0C \n" +
                "7EC 24 00 28 07 00 08 06 13 \n" +
                "7EC 25 00 00 00 00 00 00 00 \n" +
                "7EC 26 00 00 00 00 00 00 00 \n" +
                "\n" +
                ">2106\n" +
                "7EA 03 7F 21 12 \n" +
                "7EC 03 7F 21 12 \n" +
                "\n" +
                ">"
*/
}
