package com.github.android.research.mock.server.infrastructure;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

public class DefaultResponseHandler implements MockResponseHandler {

    String path;

    public MockResponse response(final RecordedRequest request) {

        MockResponseFactory mockResponseFactory = new MockResponseFactory();

        path = request.getPath();

        int responseCode = 200;
        String responseBody = "";

        try {
            if (path.contains("/user/auth/")) {

                if (path.contains("rodrigo/1234")) {
                    responseBody = mockResponseFactory.loginSuccess();
                    MockResponse mockResponse = new MockResponse().setResponseCode(responseCode).setBody(responseBody);
                    mockResponse.addHeader("token", "ASDH3-23R3ER-WEFWQEFE-EFW");
                    return mockResponse;
                } else {
                    responseBody = mockResponseFactory.loginFail();
                }
            } else if (path.contains("/research")) {

                if (request.getHeaders().get("token") != null) {
                    responseBody = mockResponseFactory.researchSuccess();
                } else {
                    responseCode = 401;
                    responseBody = mockResponseFactory.unauthorized();
                }
            } else {
                responseCode = 404;
            }
        } catch (Exception e) {
            return new MockResponse().setResponseCode(404);
        }

        return new MockResponse().setResponseCode(responseCode).setBody(responseBody);
    }
}
