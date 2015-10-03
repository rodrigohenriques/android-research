package com.github.android.research.application.service.login;

import android.util.Log;

import com.github.android.research.application.Constants;
import com.github.android.research.application.service.ApplicationService;
import com.github.android.research.application.service.ApplicationServiceCallback;
import com.github.android.research.application.service.ApplicationServiceError;
import com.github.android.research.infrastructure.backend.BackendService;
import com.github.android.research.infrastructure.backend.ServiceResponse;
import com.google.gson.Gson;
import com.google.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginService implements ApplicationService<LoginInput, LoginOutput> {
    @Inject
    BackendService backendService;

    @Override
    public void execute(final LoginInput loginInput, final ApplicationServiceCallback<LoginOutput> callback) {
        Call<ServiceResponse> call = backendService.auth(loginInput.username,
                loginInput.password,
                loginInput.getDeviceId(),
                loginInput.getLocation(),
                loginInput.getConnectionType(),
                loginInput.getBatteryLevel());

        call.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Response<ServiceResponse> response, Retrofit retrofit) {
                Log.i("LoginService", "RESULT - " + response.code());
                Log.i("LoginService", response.headers().toString());

                ServiceResponse serviceResponse = response.body();

                if (response.isSuccess()) {
                    Log.i("LoginService", new Gson().toJson(serviceResponse));

                    if (serviceResponse.isSuccess()) {
                        String token = response.headers().get(Constants.Headers.TOKEN);
                        LoginOutput loginOutput = new LoginOutput(loginInput.username, token);
                        callback.onSuccess(loginOutput);
                    } else {
                        ApplicationServiceError error = new ApplicationServiceError(serviceResponse.getCode(), serviceResponse.getMessage());
                        callback.onError(error);
                    }
                } else {
                    ApplicationServiceError error = new ApplicationServiceError(String.valueOf(response.code()), response.message());
                    callback.onError(error);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("LoginService", t.getMessage(), t);
                ApplicationServiceError error = new ApplicationServiceError("0001", t.getMessage(), new Exception(t));
                callback.onError(error);
            }
        });
    }
}
