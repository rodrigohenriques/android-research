package com.github.android.research.application.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.EditText;

import com.github.android.research.R;
import com.github.android.research.application.Constants;
import com.github.android.research.infrastructure.helper.DialogHelper;
import com.github.android.research.infrastructure.helper.SharedPreferencesHelper;
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

    @Inject
    LocationManager locationManager;

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    ProgressDialog progressDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (sharedPreferencesHelper.isLoggedIn()) {
            startMainActivity(sharedPreferencesHelper.getUsername());
        }
    }

    @OnClick(R.id.login_button)
    public void performLogin() {
        String username = mEditTextUsername.getText().toString();
        String password = mEditTextPassword.getText().toString();

        LoginInput input = new LoginInput(username, password);

        input.setLocation(locationManager);

        loginService.execute(input, this);

        progressDialog = ProgressDialog.show(this, "Aguarde", "Conectando no sistema...", true, false);
    }

    @Override
    public void onSuccess(LoginOutput loginOutput) {
        progressDialog.dismiss();

        sharedPreferencesHelper.setToken(loginOutput.token());
        sharedPreferencesHelper.setUsername(loginOutput.username());

        startMainActivity(loginOutput.username());
    }

    private void startMainActivity(String username) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.Extras.USERNAME, username);
        startActivity(intent);
    }

    @Override
    public void onError(ApplicationServiceError error) {
        DialogHelper.showErrorDialog(this, error.getCode(), error.getMessage());
        progressDialog.dismiss();
    }
}
