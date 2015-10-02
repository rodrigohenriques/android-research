package com.github.android.research.mock.server.application.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.android.research.mock.server.BuildConfig;
import com.github.android.research.mock.server.R;
import com.github.android.research.mock.server.infrastructure.DefaultResponseHandler;
import com.github.android.research.mock.server.infrastructure.MockResponseHandler;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

@EActivity
public class MainActivity extends AppCompatActivity {

    private MockWebServer mockWebServer;

    private SharedPreferences settingsPreferences;
    private View buttonStart;

    private MockResponseHandler mockResponseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mockResponseHandler = new DefaultResponseHandler();

        buttonStart = findViewById(R.id.button_start);
        settingsPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Background
    public void startMockWebServer(View view) {
        try {
            mockWebServer = new MockWebServer();

            mockWebServer.setDispatcher(new Dispatcher() {
                @Override
                public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                    String responseDelayInMillis = settingsPreferences.getString(getString(R.string.key_pref_response_delay), SettingsActivity.DEFAULT_RESPONSE_DELAY);

                    Thread.sleep(Long.parseLong(responseDelayInMillis));

                    return mockResponseHandler.response(request);
                }
            });

            mockWebServer.start(BuildConfig.MOCK_WEB_SERVER_PORT);

            Snackbar.make(buttonStart, "Your mock server is running at " + BuildConfig.MOCK_WEB_SERVER_PORT, Snackbar.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "An error occurred on startMockWebServer", e);
        }
    }

    @Background
    public void shutdownMockWebServer(View view) {
        try {
            String message = "You stopped your mock server";

            if (mockWebServer == null) {
                message = "Your mock server is not running yet";
            } else {
                mockWebServer.shutdown();
                mockWebServer = null;
            }

            Snackbar.make(buttonStart, message, Snackbar.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "An error occurred on shutdownMockWebServer", e);
        }
    }
}
