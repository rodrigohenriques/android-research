package com.m4u.mockserver;

import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

/**
 * Created by admin on 30/09/15.
 */
public class MockServer {

    private MockWebServer mockWebServer;

    public MockServer() {
        try {
            mockWebServer = new MockWebServer();
            mockWebServer.start(8080);
            configureResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void configureResponse(){
        mockWebServer.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {


                return response(request);
            }
        });

    }

    private MockResponse response(RecordedRequest request) {
        String path = request.getPath();


        if(path.contains("/user/auth/")){
            return new MockResponse().setBody("{ \n" +
                    "  \"researches\": [\n" +
                    "    {\n" +
                    "      \"id\": 1,\n" +
                    "      \"name\":\"Pesquisa 1\",\n" +
                    "      \"description\": \"Lorem ipsum sit amet\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 2,\n" +
                    "      \"name\": \"Pesquisa 2\",\n" +
                    "      \"description\": \"Lorem ipsum dolor sit amet\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}").setStatus("200");
        }

        return null;
    }


}
