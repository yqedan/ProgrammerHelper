package com.yusufqedan.programmerhelper.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yusufqedan.programmerhelper.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivityNoActionBar {
    //private static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nvView)
    NavigationView nvDrawer;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ActionBarDrawerToggle drawerToggle;
    private Fragment[] tabs = {null, null};
    private FirebaseUser mUser;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupDrawerContent(nvDrawer);
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);

        mAuth = FirebaseAuth.getInstance();
        final Context context = this;
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    if (tabs[0] == null) {
                        tabs[0] = MainFragment.newInstance(context, mUser.getDisplayName());
                    }
                    if (tabs[1] == null) {
                        tabs[1] = SettingsFragment.newInstance(context);
                    }
                    MenuItem menuItem = nvDrawer.getMenu().findItem(R.id.nav_home_fragment);
                    selectDrawerItem(menuItem);
                }
            }
        };
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home_fragment:
                getSupportActionBar().setTitle("Home");
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, tabs[0]).commit();
                position = 0;
                break;
            case R.id.nav_settings_fragment:
                getSupportActionBar().setTitle("Settings");
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, tabs[1]).commit();
                position = 1;
                break;
            case R.id.nav_logout:
                logout();
                return;
            default:
                //never should go here
                return;
        }
        menuItem.setChecked(true);
        mDrawer.closeDrawers();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
