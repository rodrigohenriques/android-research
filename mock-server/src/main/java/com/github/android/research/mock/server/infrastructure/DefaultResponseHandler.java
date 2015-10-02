package com.github.android.research.mock.server.infrastructure;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

public class DefaultResponseHandler implements MockResponseHandler {

    String path;

    public MockResponse response(final RecordedRequest request) {

        MockOperationResponse mockOperationResponse = new MockOperationResponse();

        path = request.getPath();

        int responseCode = 200;
        String responseBody = "";

        try {
            if (path.contains("/user/auth/")) {

                if (path.contains("rodrigo/1234")) {
                    responseBody = mockOperationResponse.loginSuccess();
                } else {
                    responseBody = mockOperationResponse.loginFail();
                    responseCode = 201;
                }
            } else {
                responseCode = 404;
            }
        } catch (Exception e) {
            new MockResponse().setResponseCode(404);
        }

        return new MockResponse().setResponseCode(responseCode).setBody(responseBody);
    }
}
