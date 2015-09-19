package com.github.android.research.infrastructure.modules;

import com.github.android.research.application.service.ApplicationService;
import com.github.android.research.application.service.login.LoginInput;
import com.github.android.research.application.service.login.LoginOutput;
import com.github.android.research.application.service.login.LoginService;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

public class ApplicationModule extends AbstractModule {

    public static final String LOGIN_SERVICE = "login-service";

    @Override
    protected void configure() {
        bind(new TypeLiteral<ApplicationService<LoginInput, LoginOutput>>() {
        }).annotatedWith(Names.named(LOGIN_SERVICE)).to(LoginService.class);
    }
}
