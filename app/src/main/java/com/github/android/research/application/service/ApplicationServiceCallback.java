package com.github.android.research.application.service;

public interface ApplicationServiceCallback<Output> {
    void onSuccess(Output output);
    void onError(ApplicationServiceError error);
}
