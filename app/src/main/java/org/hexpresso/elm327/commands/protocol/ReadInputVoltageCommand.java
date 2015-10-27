package org.hexpresso.elm327.commands.protocol;

import android.util.Log;

import org.hexpresso.elm327.commands.AbstractCommand;
import org.hexpresso.elm327.filters.RegularExpressionResponseFilter;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-26.
 */
public class ReadInputVoltageCommand extends AbstractCommand {

    private Double voltage;

    public ReadInputVoltageCommand() {
        super("AT RV");
        addResponseFilter(new RegularExpressionResponseFilter("(\\d+\\.?\\d+)V?"));
    }

    public double getInputVoltage() {
        if (voltage == null) {
            voltage = new Double(getResponse().getLines().get(0));
        }
        return voltage.doubleValue();
    }
}
