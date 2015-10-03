package com.github.android.research.infrastructure.backend;

import android.location.Location;

import com.github.android.research.application.Constants;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

public interface BackendService {
    @GET("user/auth/{user}/{password}")
    Call<ServiceResponse> auth(
            @Path("user") String user,
            @Path("password") String password,
            @Header(Constants.Headers.DEVICE_ID) String deviceId,
            @Header(Constants.Headers.LOCATION) Location location,
            @Header(Constants.Headers.CONNECTION_TYPE) String connectionType,
            @Header(Constants.Headers.BATTERY_LEVEL) String batteryLevel);

    @GET("research")
    Call<ResearchResponse> research(
            @Header(Constants.Headers.DEVICE_ID) String deviceId,
            @Header(Constants.Headers.LOCATION) Location location,
            @Header(Constants.Headers.CONNECTION_TYPE) String connectionType,
            @Header(Constants.Headers.BATTERY_LEVEL) String batteryLevel);
}
