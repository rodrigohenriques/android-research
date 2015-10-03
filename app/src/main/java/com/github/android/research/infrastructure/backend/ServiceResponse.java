package com.github.android.research.infrastructure.backend;

public class ServiceResponse {
    public String code;
    public String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return "0000".equals(code);
    }
}
