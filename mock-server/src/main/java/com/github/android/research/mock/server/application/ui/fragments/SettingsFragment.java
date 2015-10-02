package com.github.android.research.mock.server.application.ui.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.github.android.research.mock.server.R;


public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
