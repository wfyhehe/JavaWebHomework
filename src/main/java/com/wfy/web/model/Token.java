package com.wfy.web.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by wfy on 18-3-25, good luck.
 */

public class Token implements Serializable {
    private String id;
    private String credentials;

    public Token(String userId, String credentials) {
        this.id = userId;
        this.credentials = credentials;
    }

    public Token(Object userId, Object credentials) {
        this.id = (String) userId;
        this.credentials = (String) credentials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(id, token.id) &&
                Objects.equals(credentials, token.credentials);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + credentials.hashCode();
        return result;
    }
}
