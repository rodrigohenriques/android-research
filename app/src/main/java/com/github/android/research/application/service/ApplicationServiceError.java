package com.github.android.research.application.service;

public class ApplicationServiceError {
    private String code;
    private String message;
    private Exception e;

    public ApplicationServiceError(String code, String message, Exception e) {
        this.code = code;
        this.message = message;
        this.e = e;
    }

    public ApplicationServiceError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Exception getE() {
        return e;
    }
}
