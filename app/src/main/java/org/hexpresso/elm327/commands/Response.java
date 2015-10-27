package org.hexpresso.elm327.commands;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-24.
 */
public class Response {
    private String            mRawResponse   = null;  // The raw ELM327 response as string
    private ArrayList<String> mResponseLines = null;  // Each array element represents a line

    /**
     * Constructor
     * @param rawResponse The raw ELM327 response as string
     */
    public Response(String rawResponse) {
        mRawResponse = new String(rawResponse);

        // Split the raw response into lines
        final String [] lines = rawResponse.replaceAll("\\r", "").split("\\n");
        mResponseLines = new ArrayList(Arrays.asList(lines));
    }

    private Response() {
    }

    public ArrayList<String> getLines() {
        return mResponseLines;
    }

    public String rawResponse() {
        return mRawResponse;
    }

    public int get(int byteIndex) {
        return get(0, byteIndex);
    }

    public int get(int lineIndex, int byteIndex) {
        final String line = mResponseLines.get(lineIndex);
        final int start = byteIndex * 2;
        return HexToInteger(line.substring(start, start + 2));
    }

    public static int HexToInteger(String hex) {
        return Integer.parseInt(hex, 16);
    }
}
