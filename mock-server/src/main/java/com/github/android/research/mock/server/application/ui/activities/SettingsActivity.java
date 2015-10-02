package com.github.android.research.mock.server.application.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.android.research.mock.server.application.ui.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    public static final String DEFAULT_RESPONSE_DELAY = "5000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
