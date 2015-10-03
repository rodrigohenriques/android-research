package com.github.android.research.application.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.android.research.R;
import com.github.android.research.application.Constants;
import com.github.android.research.application.ui.fragments.ResearchesFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import roboguice.inject.InjectExtra;

public class MainActivity extends BaseActivity {

    private final int RESEARCHES_FRAGMENT_INDEX = 0;
    private final int PROFILE_FRAGMENT_INDEX = 1;
    private final int ABOUT_US_FRAGMENT_INDEX = 2;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.nav_header_username)
    TextView textViewNavHeaderUsername;

    @Bind(R.id.home_nav_view)
    NavigationView navigationView;

    @InjectExtra(value = Constants.Extras.USERNAME, optional = false)
    String username;

    List<Fragment> fragments;

    int selectedIndex;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        rootView = (ViewGroup) findViewById(R.id.home_frame);

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        openResearchesFragment();
                        return true;
                    // For rest of the options we just show a toast on click
                    case R.id.nav_profile:
                        openProfileFragment();
                        return true;
                    case R.id.nav_about_us:
                        openAboutUsFragment();
                        return true;
                }

                return false;
            }
        });

        textViewNavHeaderUsername.setText(username);

        fragments = new ArrayList<>(3);

        fragments.add(RESEARCHES_FRAGMENT_INDEX, new ResearchesFragment());
        //fragments.add(PROFILE_FRAGMENT_INDEX, new ProfileFragment());
        //fragments.add(ABOUT_US_FRAGMENT_INDEX, new AboutUsFragment());

        openResearchesFragment();
    }

    private void openProfileFragment() {
        openFragment(PROFILE_FRAGMENT_INDEX, R.string.title_profile);
    }

    private void openAboutUsFragment() {
        openFragment(ABOUT_US_FRAGMENT_INDEX, R.string.title_about_us_fragment);
    }

    private void openResearchesFragment() {
        openFragment(RESEARCHES_FRAGMENT_INDEX, R.string.title_researches);
    }

    private void openFragment(int index, int titleId) {
        setTitle(titleId);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.home_frame, fragments.get(index));
        fragmentTransaction.commit();
        selectedIndex = index;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.nav_header_logout)
    public void logout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (selectedIndex != RESEARCHES_FRAGMENT_INDEX) {
            openResearchesFragment();
        } else {
            super.onBackPressed();
        }
    }
}
