package org.hexpresso.elm327.filters;

import org.hexpresso.elm327.commands.Response;
import org.hexpresso.elm327.commands.ResponseFilter;

import java.util.ListIterator;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-26.
 */
public class RemoveSpacesResponseFilter implements ResponseFilter {

    public RemoveSpacesResponseFilter() {
    }

    @Override
    public void onResponseReceived(Response response) {
        ListIterator<String> it = response.getLines().listIterator();
        while(it.hasNext()) {
            String line = it.next();
            line = line.replaceAll(" ", "");
            it.set(line);
        }
    }
}
