package org.hexpresso.elm327.exceptions;

/**
 * Sent when there is a "STOPPED" message.
 */
public class StoppedException extends ResponseException {

    public StoppedException() {
        super("STOPPED");
    }

}
