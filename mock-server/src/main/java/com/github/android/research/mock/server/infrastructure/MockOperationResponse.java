package com.github.android.research.mock.server.infrastructure;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import com.github.android.research.mock.server.domain.model.Research;

public class MockOperationResponse {

    private final Gson gson;

    public MockOperationResponse() {
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    public String loginSuccess() {
        AuthServiceResponse authResponseMock = new AuthServiceResponse();
        authResponseMock.code = "0000";
        authResponseMock.message = "Sucesso";
        authResponseMock.researches = new ArrayList<>();
        authResponseMock.researches.add(new Research(1, "Aprovação do governo Dilma"));
        authResponseMock.researches.add(new Research(2, "Mulheres no mercado de trabalho"));
        authResponseMock.researches.add(new Research(3, "Estatuto da família"));
        return gson.toJson(authResponseMock);
    }

    public String loginFail() {
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.code = "9999";
        serviceResponse.message = "Login ou senha inválidos. Tente novamente.";
        return gson.toJson(serviceResponse);
    }
}
