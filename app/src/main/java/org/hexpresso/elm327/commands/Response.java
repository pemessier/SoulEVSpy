package org.hexpresso.elm327.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-24.
 */
public class Response {
    private String            mRawResponse   = null;            // The raw ELM327 response as string
    private List<String> mResponseLines = null;            // Each array element represents a line
    private List<ResponseFilter> mResponseFilters = null;  // Response filters

    /**
     * Constructor
     */
    Response() {
    }

    /**
     * Sets the raw ELM327 response string
     * @param rawResponse
     */
    public void setRawResponse(String rawResponse) {
        mRawResponse = rawResponse;
    }

    /**
     * Adds a response filter to be executed when processing the response
     * @param responseFilter A ResponseFilter object
     * @return Current response object
     */
    public Response addResponseFilter(ResponseFilter responseFilter) {
        if(mResponseFilters == null) {
            mResponseFilters = new ArrayList<>();
        }
        mResponseFilters.add(responseFilter);
        return this;
    }

    /**
     * Processes the current Response object.
     */
    public void process() {
        if (mResponseLines == null) {
            final String[] lines = mRawResponse.replaceAll("\\r", "").split("\\n");
            mResponseLines = new ArrayList<>(Arrays.asList(lines));
        }

        // Execute response filters (if any)
        if(mResponseFilters != null) {
            for(ResponseFilter filter: mResponseFilters) {
                filter.onResponseReceived(this);
            }
        }
    }

    public List<String> getLines() {
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