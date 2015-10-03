package com.github.android.research.application.ui.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.research.R;

import butterknife.ButterKnife;
import roboguice.activity.RoboActionBarActivity;

public abstract class BaseActivity extends RoboActionBarActivity {
    protected String logTag;
    protected ViewGroup rootView;
    protected View viewComponentProgressBar;
    protected ActionBar supportActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());

        ButterKnife.bind(this);

        logTag = getString(R.string.log_tag);

        viewComponentProgressBar = getLayoutInflater().inflate(R.layout.component_progressbar, null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected abstract int getContentView();

    protected void logError(String message, Exception e) {
        Log.e(logTag, message, e);
    }

    protected void logError(Exception e) {
        Log.e(logTag, e.getMessage(), e);
    }

    public void startProgress(ViewGroup container) {
        rootView = container;
        rootView.addView(viewComponentProgressBar);
    }

    public void startProgress() {
        rootView = (ViewGroup) getWindow().getDecorView().getRootView();
        rootView.addView(viewComponentProgressBar);
    }

    public void finishProgress() {
        rootView.removeView(viewComponentProgressBar);
    }

    public void justSnackIt(int messageResId) {
        final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), messageResId, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.default_snackbar_action, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });

        snackbar.show();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);

        if (supportActionBar != null) {
            supportActionBar.setTitle(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);

        if (supportActionBar != null) {
            supportActionBar.setTitle(titleId);
        }
    }
}
