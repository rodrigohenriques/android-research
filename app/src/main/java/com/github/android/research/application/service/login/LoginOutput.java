package com.github.android.research.application.service.login;

public class LoginOutput {
    String username;
    String token;

    public LoginOutput(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String username() {
        return username;
    }

    public String token() {
        return token;
    }
}
