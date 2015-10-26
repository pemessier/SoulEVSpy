package org.hexpresso.elm327;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import org.hexpresso.elm327.commands.AbstractCommand;
import org.hexpresso.elm327.commands.Response;
import org.hexpresso.elm327.commands.ResponseFilter;
import org.hexpresso.soulevspy.obd.commands.BatteryManagementSystemCommand;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-24.
 */
public class CommandTest extends AndroidTestCase {

    ByteArrayInputStream input = null;
    ByteArrayOutputStream output = null;

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

    class ElmCommand extends AbstractCommand implements ResponseFilter {

        public ElmCommand() {
            super("Command");
            withResponseFilter(this);
        }

        @Override
        public void onResponseReceived(Response response) {
            response.getLines().clear();
        }
    }

    /**
     *
     */
    public void testBasicCommand() {
        final String response = new String("ABCD");
        input = new ByteArrayInputStream(response.getBytes());

        ElmCommand cmd = new ElmCommand();
        try {
            input.read(response.getBytes());
            cmd.execute(input, output);
        }
        catch(Exception e)
        {
            // ...
        }

        Assert.assertEquals("Command\r", output.toString());
        Assert.assertTrue(cmd.getResponse().getLines().isEmpty());
    }

    /**
     *
     */
    public void testBmsCommand() {
        final String response = new String("ABCD");
        input = new ByteArrayInputStream(msg2101.getBytes());

        BatteryManagementSystemCommand cmd = new BatteryManagementSystemCommand();
        try {
            input.read(response.getBytes());
            cmd.execute(input, output);
        }
        catch(Exception e)
        {
            // ...
        }

        Assert.assertEquals(8, cmd.getResponse().getLines().size());
        Assert.assertEquals(33, cmd.getResponse().get(0));
        Assert.assertEquals(232, cmd.getResponse().get(7, 6));

        Assert.assertEquals(10.5, cmd.getStateOfCharge());
        Assert.assertEquals(10.5, cmd.getStateOfCharge());
    }

    /**
     *
     */
    @Override
    protected void setUp() {
        output = new ByteArrayOutputStream();
    }
}
