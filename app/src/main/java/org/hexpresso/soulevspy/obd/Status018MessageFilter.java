package org.hexpresso.soulevspy.obd;

import org.hexpresso.obd.ObdMessageData;
import org.hexpresso.obd.ObdMessageFilter;

import java.util.ArrayList;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-13.
 */
public class Status018MessageFilter extends ObdMessageFilter {

    public Status018MessageFilter() {
        super("018");
    }

    @Override
    protected boolean doProcessMessage(ObdMessageData messageData) {
        ArrayList<String> data = messageData.getData();
        if (data.size() != 8) {
            return false;
        }

        return false;
    }
}
