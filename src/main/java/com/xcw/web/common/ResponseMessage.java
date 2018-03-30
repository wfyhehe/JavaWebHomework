package com.xcw.web.common;

/**
 * Created by xcw on 18-3-30, good luck.
 */
public class ResponseMessage {
    String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public ResponseMessage() {
        this.message = "Ok";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
