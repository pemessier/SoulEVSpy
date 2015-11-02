package org.hexpresso.elm327;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import org.hexpresso.elm327.commands.general.VehicleIdentifierNumberCommand;
import org.hexpresso.elm327.io.Message;
import org.hexpresso.elm327.io.Protocol;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-11-01.
 */
public class ProtocolTest extends AndroidTestCase {

    public void testProtocolVinCommand() {
        Protocol protocol = new Protocol();
        protocol.registerOnMessageReceivedListener(new Protocol.MessageReceivedListener() {
            @Override
            public void onMessageReceived(Message message) {
                if(message.getCommand() instanceof VehicleIdentifierNumberCommand) {
                    VehicleIdentifierNumberCommand cmd = (VehicleIdentifierNumberCommand)message.getCommand();
                    Assert.assertEquals("KNDJX3AE1G7123456", cmd.getValue());
                }
            }
        });

        final String vin = "7EA 10 14 49 02 01 4B 4E 44 \n" +
                           "7EA 21 4A 58 33 41 45 31 47 \n" +
                           "7EA 22 37 31 32 33 34 35 36";

        ByteArrayInputStream input = new ByteArrayInputStream(vin.getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        protocol.start(input, output);
        protocol.addCommand(new VehicleIdentifierNumberCommand());
    }
}
