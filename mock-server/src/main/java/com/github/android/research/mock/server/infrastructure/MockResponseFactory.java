package com.github.android.research.mock.server.infrastructure;


import com.github.android.research.mock.server.domain.model.Research;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class MockResponseFactory {

    private final Gson gson;

    public MockResponseFactory() {
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    public String loginSuccess() {
        ServiceResponse authResponseMock = new ServiceResponse();
        authResponseMock.code = "0000";
        authResponseMock.message = "Sucesso";
        return gson.toJson(authResponseMock);
    }

    public String loginFail() {
        ServiceResponse authResponseMock = new ServiceResponse();
        authResponseMock.code = "9999";
        authResponseMock.message = "Login ou senha inválidos. Tente novamente.";
        return gson.toJson(authResponseMock);
    }

    public String researchSuccess() {
        ResearchResponse researchResponse = new ResearchResponse();
        researchResponse.code = "0000";
        researchResponse.message = "Sucesso";
        researchResponse.researches = new ArrayList<>();
        researchResponse.researches.add(new Research(1, "Aprovação do governo Dilma", "Encomendada pelo governo federal para ter uma ideia mais clara da opinião pública"));
        researchResponse.researches.add(new Research(2, "Mulheres no mercado de trabalho", "Encomendada pelo grupo feminista da UERJ pra arrumar tumulto nas ruas"));
        researchResponse.researches.add(new Research(3, "Estatuto da família", "Encomendada pelo pastor Marco Feliciano pra incomodar as bancada LGBT"));
        return gson.toJson(researchResponse);
    }

    public String unauthorized() {
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.code = "0401";
        serviceResponse.message = "Operação não autorizada";
        return gson.toJson(serviceResponse);
    }
}
