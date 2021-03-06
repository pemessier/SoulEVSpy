package org.hexpresso.soulevspy.obd;

import org.hexpresso.obd.ObdMessageData;
import org.hexpresso.obd.ObdMessageFilter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-13.
 */
public class ClockMessageFilter extends ObdMessageFilter {
    GregorianCalendar mTime = null;

    public ClockMessageFilter() {
        super("567");
    }

    @Override
    protected boolean doProcessMessage(ObdMessageData messageData) {
        ArrayList<String> data = messageData.getData();
        if ( data.size() != 8 )
        {
            return false;
        }

        // Get the current time
        Calendar calendar = Calendar.getInstance();

        int hour   = messageData.getDataByte(1);
        int minute = messageData.getDataByte(2);
        int second = messageData.getDataByte(3);
        mTime = new GregorianCalendar(calendar.get(Calendar.YEAR),
                                      calendar.get(Calendar.MONTH),
                                      calendar.get(Calendar.DATE),
                                      hour,
                                      minute,
                                      second);

        return true;
    }

    public GregorianCalendar getTime() {
        return mTime;
    }
}
