package com.xcw.web.common;

import com.xcw.web.model.User;

/**
 * Created by xcw on 18-3-30, good luck.
 */
public class SignInResponse {
    private String jwtToken;
    private String message;
    private User user;

    public SignInResponse(String message) {
        this.message = message;
    }

    public SignInResponse(String jwtToken, User user) {
        this.jwtToken = jwtToken;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
