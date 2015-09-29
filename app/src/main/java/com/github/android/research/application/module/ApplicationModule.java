package com.github.android.research.application.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;

import com.github.android.research.BuildConfig;
import com.github.android.research.application.Constants;
import com.github.android.research.application.service.ApplicationService;
import com.github.android.research.application.service.login.LoginInput;
import com.github.android.research.application.service.login.LoginOutput;
import com.github.android.research.application.service.login.LoginService;
import com.github.android.research.infrastructure.backend.BackendService;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import retrofit.Retrofit;

public class ApplicationModule extends AbstractModule {

    public static final String LOGIN_SERVICE = "login-service";

    Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Override
    protected void configure() {
        bind(new TypeLiteral<ApplicationService<LoginInput, LoginOutput>>() {
        }).annotatedWith(Names.named(LOGIN_SERVICE)).to(LoginService.class);

        bind(BackendService.class).toInstance(getBackendServiceInstance());
    }

    private String printNow() {
        return DateFormat.getDateTimeInstance().format(new Date());
    }

    private BackendService getBackendServiceInstance() {
        OkHttpClient customOkHttpClient = new OkHttpClient();
        customOkHttpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                Request newRequest;

                newRequest = request.newBuilder()
                        .addHeader(Constants.Headers.PLATFORM, "ANDROID")
                        .addHeader(Constants.Headers.PLATFORM_VERSION, Build.VERSION.CODENAME)
                        .addHeader(Constants.Headers.TIMESTAMP, printNow())
                        .addHeader(Constants.Headers.VERSION_NAME, printNow())
                        .build();

                Response response = chain.proceed(newRequest);

                String token = response.header("token");

                if (token != null) {
                    SharedPreferences preferences = application.getSharedPreferences();
                }

                return response;
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BACKEND_SERVICE_BASE_URL)
                .client(customOkHttpClient)
                .build();

        return retrofit.create(BackendService.class);
    }
}
