package org.hexpresso.elm327.commands.obd;

import org.hexpresso.elm327.commands.AbstractCommand;
import org.hexpresso.elm327.commands.obd.OBDAdaptiveTimingModes;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-26.
 */
public class OBDAdaptiveTimingCommand extends AbstractCommand {

    public OBDAdaptiveTimingCommand(final OBDAdaptiveTimingModes mode) {
        super("AT AT" + mode.getValue());
    }
}
