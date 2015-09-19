package com.github.android.research.application.service;


public interface ApplicationService<Input, Output> {
    void execute(Input input, ApplicationServiceCallback<Output> callback);
}
