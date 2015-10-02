package com.github.android.research.application.service.login;

import com.github.android.research.application.service.ApplicationService;
import com.github.android.research.application.service.ApplicationServiceCallback;
import com.github.android.research.application.service.ApplicationServiceError;
import com.github.android.research.domain.model.Research;
import com.github.android.research.infrastructure.backend.AuthResponse;
import com.github.android.research.infrastructure.backend.BackendService;
import com.google.inject.Inject;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class LoginService implements ApplicationService<LoginInput, LoginOutput> {
    @Inject
    BackendService backendService;

    @Override
    public void execute(LoginInput loginInput, final ApplicationServiceCallback<LoginOutput> callback) {
        Call<AuthResponse> call = backendService.auth(loginInput.username,
                loginInput.password,
                loginInput.getDeviceId(),
                loginInput.getLocation(),
                loginInput.getConnectionType(),
                loginInput.getBatteryLevel());

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Response<AuthResponse> response) {
                if (response.isSuccess()){
                    List<Research> researches = response.body().getResearches();
                    LoginOutput loginOutput = new LoginOutput(researches);
                    callback.onSuccess(loginOutput);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                ApplicationServiceError error = new ApplicationServiceError("561", t.getMessage(), new Exception(t));
                callback.onError(error);
            }
        });
    }
}
