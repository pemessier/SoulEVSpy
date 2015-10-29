package org.hexpresso.elm327.io;

import org.hexpresso.elm327.commands.Command;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-27.
 */
public class Protocol {
    private LinkedBlockingDeque<Message> mMessageQueue = new LinkedBlockingDeque<>();

    private Protocol() {

    }

    public void addCommand(Command command) {
        try {
            mMessageQueue.put(new Message(command));
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }

    void execute(InputStream in, OutputStream out) throws IOException, InterruptedException {
        Message message = mMessageQueue.take();

        if(message != null) {
            message.setState(Message.State.EXECUTING);

            message.getCommand().execute(in, out);

            message.setState(Message.State.FINISHED);
        }
    }
}
