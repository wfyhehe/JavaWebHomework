package com.wfy.web.common;

/**
 * Created by wfy on 18-3-30, good luck.
 */
public class ResponseMessage {
    String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
