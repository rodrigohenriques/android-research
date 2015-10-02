package com.github.android.research.mock.server.infrastructure;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

public interface MockResponseHandler {
    MockResponse response(final RecordedRequest request);
}
