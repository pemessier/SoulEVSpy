package org.hexpresso.elm327.commands.general;

import org.hexpresso.elm327.commands.AbstractCommand;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-29.
 */
public class VehicleIdentifierNumberCommand extends AbstractCommand {

    private String mVIN = null;

    public VehicleIdentifierNumberCommand() {
        super("09 02");
    }
}
