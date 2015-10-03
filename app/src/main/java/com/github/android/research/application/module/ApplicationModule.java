package com.github.android.research.application.module;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.github.android.research.BuildConfig;
import com.github.android.research.application.Constants;
import com.github.android.research.infrastructure.helper.SharedPreferencesHelper;
import com.github.android.research.application.service.ApplicationService;
import com.github.android.research.application.service.login.LoginInput;
import com.github.android.research.application.service.login.LoginOutput;
import com.github.android.research.application.service.login.LoginService;
import com.github.android.research.application.service.research.ListResearchesInput;
import com.github.android.research.application.service.research.ListResearchesService;
import com.github.android.research.domain.model.Research;
import com.github.android.research.infrastructure.backend.BackendService;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ApplicationModule extends AbstractModule {

    public static final String LOGIN_SERVICE = "login-service";
    public static final String LIST_RESEARCHES_SERVICE = "list-researches-service";

    private final SharedPreferencesHelper sharedPreferencesHelper;

    Application application;

    public ApplicationModule(Application application) {
        this.application = application;

        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(application);
    }

    @Override
    protected void configure() {
        bind(new TypeLiteral<ApplicationService<LoginInput, LoginOutput>>() {
        }).annotatedWith(Names.named(LOGIN_SERVICE)).to(LoginService.class);

        bind(new TypeLiteral<ApplicationService<ListResearchesInput, List<Research>>>() {
        }).annotatedWith(Names.named(LIST_RESEARCHES_SERVICE)).to(ListResearchesService.class);

        bind(BackendService.class).toInstance(getBackendServiceInstance());

        bind(SharedPreferencesHelper.class).toInstance(SharedPreferencesHelper.getInstance(application));
    }

    private String printNow() {
        return DateFormat.getDateTimeInstance().format(new Date());
    }

    private BackendService getBackendServiceInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BACKEND_SERVICE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.client().interceptors().add(new MyInterceptor());

        return retrofit.create(BackendService.class);
    }

    class MyInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Request newRequest = prepareRequest(request);

            logRequestData(newRequest);

            return chain.proceed(newRequest);
        }

        private void logRequestData(Request newRequest) throws IOException {
            Buffer buffer = new Buffer();

            Log.i("CustomHttpClient", newRequest.method() + " - " + newRequest.urlString());
            Log.i("CustomHttpClient", newRequest.headers().toString());

            RequestBody body = newRequest.body();

            if (body != null) {

                newRequest.body().writeTo(buffer);

                Log.i("CustomHttpClient", buffer.readUtf8());
            }
        }

        private Request prepareRequest(Request request) {
            Request newRequest;
            newRequest = request.newBuilder()
                    .addHeader(Constants.Headers.PLATFORM, "ANDROID")
                    .addHeader(Constants.Headers.PLATFORM_VERSION, Build.VERSION.CODENAME)
                    .addHeader(Constants.Headers.TIMESTAMP, printNow())
                    .addHeader(Constants.Headers.VERSION_NAME, BuildConfig.VERSION_NAME)
                    .build();

            if (sharedPreferencesHelper.isLoggedIn()) {
                newRequest = newRequest.newBuilder()
                        .addHeader(Constants.Headers.TOKEN, sharedPreferencesHelper.getToken())
                        .build();
            }
            return newRequest;
        }
    }
}
