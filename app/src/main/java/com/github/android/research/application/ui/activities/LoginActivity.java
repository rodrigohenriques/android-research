package com.github.android.research.application.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.github.android.research.R;
import com.github.android.research.application.module.ApplicationModule;
import com.github.android.research.application.service.ApplicationService;
import com.github.android.research.application.service.ApplicationServiceCallback;
import com.github.android.research.application.service.ApplicationServiceError;
import com.github.android.research.application.service.login.LoginInput;
import com.github.android.research.application.service.login.LoginOutput;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ApplicationServiceCallback<LoginOutput> {
    @Bind(R.id.login_edittext_username)
    EditText mEditTextUsername;
    @Bind(R.id.login_edittext_password)
    EditText mEditTextPassword;

    @Inject
    @Named(ApplicationModule.LOGIN_SERVICE)
    ApplicationService<LoginInput, LoginOutput> loginService;

//    @Inject
//    LocationManager locationManager;
//
//    @Inject
//    PowerManager powerManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.login_button)
    public void performLogin() {
        String username = mEditTextUsername.getText().toString();
        String password = mEditTextPassword.getText().toString();

        LoginInput input = new LoginInput(username, password);

        //input.setLocation(locationManager);

        loginService.execute(input, this);
    }

    @Override
    public void onSuccess(LoginOutput loginOutput) {
        Intent intent = new Intent(this, MainActivity.class);
    }

    @Override
    public void onError(ApplicationServiceError error) {

    }
}
