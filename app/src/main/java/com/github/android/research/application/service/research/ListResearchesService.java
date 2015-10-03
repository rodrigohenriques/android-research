package com.github.android.research.application.service.research;

import android.util.Log;

import com.github.android.research.application.service.ApplicationService;
import com.github.android.research.application.service.ApplicationServiceCallback;
import com.github.android.research.application.service.ApplicationServiceError;
import com.github.android.research.domain.model.Research;
import com.github.android.research.infrastructure.backend.BackendService;
import com.github.android.research.infrastructure.backend.ResearchResponse;
import com.google.gson.Gson;
import com.google.inject.Inject;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ListResearchesService implements ApplicationService<ListResearchesInput, List<Research>> {

    @Inject
    BackendService backendService;

    @Override
    public void execute(ListResearchesInput researchInput, final ApplicationServiceCallback<List<Research>> callback) {
        final Call<ResearchResponse> researchCall = backendService.research(researchInput.getDeviceId(),
                researchInput.getLocation(),
                researchInput.getConnectionType(),
                researchInput.getBatteryLevel());

        researchCall.enqueue(new Callback<ResearchResponse>() {
            @Override
            public void onResponse(Response<ResearchResponse> response, Retrofit retrofit) {
                Log.i("ListResearchesService", "RESULT - " + response.code());
                Log.i("ListResearchesService", response.headers().toString());

                ResearchResponse researchResponse = response.body();

                if (response.isSuccess()) {
                    Log.i("ListResearchesService", new Gson().toJson(researchResponse));

                    if (researchResponse.isSuccess()) {
                        List<Research> outuput = researchResponse.getResearches();
                        callback.onSuccess(outuput);
                    } else {
                        ApplicationServiceError error = new ApplicationServiceError(researchResponse.getCode(), researchResponse.getMessage());
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
                ApplicationServiceError error = new ApplicationServiceError("0002", t.getMessage(), new Exception(t));
                callback.onError(error);
            }
        });
    }
}
