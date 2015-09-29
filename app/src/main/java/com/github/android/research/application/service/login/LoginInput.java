package com.github.android.research.application.service.login;

import com.github.android.research.application.service.AbstractInput;

public class LoginInput extends AbstractInput {
    protected String username;
    protected String password;

    public LoginInput(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
