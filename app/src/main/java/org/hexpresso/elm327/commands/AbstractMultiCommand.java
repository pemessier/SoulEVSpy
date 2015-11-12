package org.hexpresso.elm327.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-26.
 */
public abstract class AbstractMultiCommand implements Command {

    private ArrayList<Command> mCommands = new ArrayList<>();
    protected Response mRawResponse = null;                    // Raw response data (for all commands)

    @Override
    public void execute(InputStream in, OutputStream out) throws IOException, InterruptedException {
        String rawResponse = "";
        for(Command command : mCommands) {
            command.execute(in, out);
            rawResponse += command.getResponse().rawResponse() + "\\n";
        }

        mRawResponse = new Response();
        mRawResponse.setRawResponse(rawResponse);
    }

    @Override
    public Response getResponse() {
        return mRawResponse;
    }

    protected AbstractMultiCommand addCommand(Command command) {
        mCommands.add(command);
        return this;
    }
}
