package org.hexpresso.soulevspy;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import org.hexpresso.soulevspy.util.KiaVinParser;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-07.
 */
public class KiaVinParserTest extends AndroidTestCase {

    // Bogus strings
    private final static String VIN_EMPTY      = "";
    private final static String VIN_NOT_KIA    = "KNXJX3AE5F7123456";
    private final static String VIN_NOT_SOUL   = "KNDXX3AE5F7123456";
    private final static String VIN_NOT_SOULEV = "KNDJX3AX5F7123456";

    // Test VIN strings
    private final static String VIN_2015_LUXURY_WHITE    = "KNDJX3AE5F7123456"; // Mario
    private final static String VIN_2016_LUXURY_TITANIUM = "KNDJX3AEXG7123456"; // Our car
    private final static String VIN_2016_LUXURY_WHITE    = "KNDJX3AE1G7123456"; // EEKO, 2016 luxury white
    private final static String VIN_2017_FAKE            = "KNDJX3AE0H0123456"; // Fake 2017
    private final static String VIN_2016_TYREL           = "KNDJX3AE2G7006329";

    // AVT
    private final static String VIN_2015_AVT_1908        = "KNDJX3AE6F7001908"; // AVT VIN 1908
    private final static String VIN_2015_AVT_1918        = "KNDJX3AE9F7001918"; // AVT VIN 1918
    private final static String VIN_2015_AVT_1919        = "KNDJX3AE0F7001919"; // AVT VIN 1919
    private final static String VIN_2015_AVT_1920        = "KNDJX3AE7F7001920"; // AVT VIN 1920

    // On the web
    private final static String VIN_2016_5477            = "KNDJX3AE1G7005477"; // westonkia.com
    private final static String VIN_2015_3847            = "KNDJP3AE0F7003847"; // 2015 Kia Soul EV Base (kia.mycommunitycar.com)
    private final static String VIN_2015_3819            = "KNDJP3AE6F7003819"; // 2015 Kia Soul EV Base (kia.mycommunitycar.com)
    private final static String VIN_2015_3798            = "KNDJX3AE2F7003798"; // 2015 Kia Soul EV Plus (kia.mycommunitycar.com)
    private final static String VIN_2015_3644            = "KNDJX3AE8F7003644"; // 2015 Kia Soul EV Plus (kia.mycommunitycar.com)

    // Text constants
    private final static String SOULEV_BASE       = "Base";
    private final static String SOULEV_PLUS       = "Plus/Luxury";
    private final static String SOULEV_ENGINE     = "Battery [LiPB 350 V, 75 Ah] + Motor [3-phase AC 80KW]";
    private final static String SOULEV_PROD_PLANT = "Gwangju (South Korea)";

    /**
     * Illegal Strings
     */
    public void testIllegalStrings() {
        Assert.assertTrue(new KiaVinParser(getContext(), VIN_2015_LUXURY_WHITE).isValid());
        Assert.assertTrue(new KiaVinParser(getContext(), VIN_2016_LUXURY_TITANIUM).isValid());
        Assert.assertTrue(new KiaVinParser(getContext(), VIN_2016_LUXURY_WHITE).isValid());
        Assert.assertTrue(new KiaVinParser(getContext(), VIN_2017_FAKE).isValid());
        Assert.assertTrue(new KiaVinParser(getContext(), VIN_2015_AVT_1908).isValid());
        Assert.assertTrue(new KiaVinParser(getContext(), VIN_2015_AVT_1918).isValid());
        Assert.assertTrue(new KiaVinParser(getContext(), VIN_2015_AVT_1919).isValid());
        Assert.assertTrue(new KiaVinParser(getContext(), VIN_2015_AVT_1920).isValid());
        Assert.assertTrue(new KiaVinParser(getContext(), VIN_2016_5477).isValid());
        Assert.assertTrue(new KiaVinParser(getContext(), VIN_2015_3847).isValid());
        Assert.assertTrue(new KiaVinParser(getContext(), VIN_2015_3819).isValid());
        Assert.assertTrue(new KiaVinParser(getContext(), VIN_2015_3798).isValid());
        Assert.assertTrue(new KiaVinParser(getContext(), VIN_2015_3644).isValid());
        Assert.assertTrue(new KiaVinParser(getContext(), VIN_2016_TYREL).isValid());
    }

    /**
     * LEgal Strings
     */
    public void testLegalStrings() {
        Assert.assertFalse(new KiaVinParser(getContext(), VIN_EMPTY).isValid());
        Assert.assertFalse(new KiaVinParser(getContext(), VIN_NOT_KIA).isValid());
        Assert.assertFalse(new KiaVinParser(getContext(), VIN_NOT_SOUL).isValid());
        Assert.assertFalse(new KiaVinParser(getContext(), VIN_NOT_SOULEV).isValid());
    }

    /**
     * Lower case input
     */
    public void testLowerCase() {
        KiaVinParser vin = new KiaVinParser(getContext(), VIN_2016_LUXURY_TITANIUM.toLowerCase());
        Assert.assertTrue(vin.isValid());
        Assert.assertEquals(vin.getVIN(), VIN_2016_LUXURY_TITANIUM);
        Assert.assertEquals(vin.getBrand(), "Kia");
        Assert.assertEquals(vin.getModel(), "Soul EV");
        Assert.assertEquals(vin.getTrim(), SOULEV_PLUS);
        Assert.assertEquals(vin.getEngine(), SOULEV_ENGINE);
        Assert.assertEquals(vin.getYear(), "2016");
        Assert.assertEquals(vin.getSequentialNumber(), "123456");
        Assert.assertEquals(vin.getProductionPlant(), SOULEV_PROD_PLANT);
    }

    /**
     * Valid VIN from a 2016 Luxury canadian model
     */
    public void test2016LuxuryTitanium() {
        KiaVinParser vin = new KiaVinParser(getContext(), VIN_2016_LUXURY_TITANIUM);
        Assert.assertTrue(vin.isValid());
        Assert.assertEquals(vin.getVIN(), VIN_2016_LUXURY_TITANIUM);
        Assert.assertEquals(vin.getBrand(), "Kia");
        Assert.assertEquals(vin.getModel(), "Soul EV");
        Assert.assertEquals(vin.getTrim(), SOULEV_PLUS);
        Assert.assertEquals(vin.getEngine(), SOULEV_ENGINE);
        Assert.assertEquals(vin.getYear(), "2016");
        Assert.assertEquals(vin.getSequentialNumber(), "123456");
        Assert.assertEquals(vin.getProductionPlant(), SOULEV_PROD_PLANT);
    }

    /**
     * Valid VIN from a 2016 Luxury canadian model
     */
    public void test2016LuxuryWhite() {
        KiaVinParser vin = new KiaVinParser(getContext(), VIN_2016_LUXURY_WHITE);
        Assert.assertTrue(vin.isValid());
        Assert.assertEquals(vin.getVIN(), VIN_2016_LUXURY_WHITE);
        Assert.assertEquals(vin.getBrand(), "Kia");
        Assert.assertEquals(vin.getModel(), "Soul EV");
        Assert.assertEquals(vin.getTrim(), SOULEV_PLUS);
        Assert.assertEquals(vin.getEngine(), SOULEV_ENGINE);
        Assert.assertEquals(vin.getYear(), "2016");
        Assert.assertEquals(vin.getSequentialNumber(), "123456");
        Assert.assertEquals(vin.getProductionPlant(), SOULEV_PROD_PLANT);
    }

    /**
     * Valid VIN from a 2016 Base US model
     */
    public void test2015BaseWhite() {
        KiaVinParser vin = new KiaVinParser(getContext(), VIN_2015_3847);
        Assert.assertTrue(vin.isValid());
        Assert.assertEquals(vin.getVIN(), VIN_2015_3847);
        Assert.assertEquals(vin.getBrand(), "Kia");
        Assert.assertEquals(vin.getModel(), "Soul EV");
        Assert.assertEquals(vin.getTrim(), SOULEV_BASE);
        Assert.assertEquals(vin.getEngine(), SOULEV_ENGINE);
        Assert.assertEquals(vin.getYear(), "2015");
        Assert.assertEquals(vin.getSequentialNumber(), "003847");
        Assert.assertEquals(vin.getProductionPlant(), SOULEV_PROD_PLANT);
    }

    /**
     * Valid VIN from a 2017 fake model (shows unknown plant)
     */
    public void test2017Fake() {
        KiaVinParser vin = new KiaVinParser(getContext(), VIN_2017_FAKE);
        Assert.assertTrue(vin.isValid());
        Assert.assertEquals(vin.getVIN(), VIN_2017_FAKE);
        Assert.assertEquals(vin.getBrand(), "Kia");
        Assert.assertEquals(vin.getModel(), "Soul EV");
        Assert.assertEquals(vin.getTrim(), SOULEV_PLUS);
        Assert.assertEquals(vin.getEngine(), SOULEV_ENGINE);
        Assert.assertEquals(vin.getYear(), "2017");
        Assert.assertEquals(vin.getSequentialNumber(), "123456");
        Assert.assertEquals(vin.getProductionPlant(), "Unknown (0)");
    }
}