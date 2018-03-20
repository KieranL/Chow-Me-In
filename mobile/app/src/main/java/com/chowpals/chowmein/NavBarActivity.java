package com.chowpals.chowmein;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.amazonaws.mobile.auth.core.DefaultSignInResultHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.IdentityProvider;
import com.amazonaws.mobile.auth.ui.SignInActivity;

import helpers.NetworkHelper;
import helpers.UserHelper;

public class NavBarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public IdentityManager identityManager;
    public static String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        baseUrl = getResources().getString(R.string.base_url);

        super.onCreate(savedInstanceState);
        NetworkHelper.checkConnectionAndNotify(this);

        identityManager = IdentityManager.getDefaultIdentityManager();
    }

    @Override
    protected void onResume() {
        super.onResume();

        NavigationView navigationView = findViewById(R.id.nav_view);

        // Only show either the LoginActivity(3) or logout(4) button
        if(UserHelper.isUserSignedIn(getApplicationContext())) {
            navigationView.getMenu().getItem(3).setVisible(false);
            navigationView.getMenu().getItem(4).setVisible(true);
        } else {
            navigationView.getMenu().getItem(3).setVisible(true);
            navigationView.getMenu().getItem(4).setVisible(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            finish();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        else if (id == R.id.nav_create_chow)
            NetworkHelper.checkConnectionAndStartActivity(this, new Intent(getApplicationContext(), CreateChowActivity.class));
        else if (id == R.id.nav_search_chow)
            NetworkHelper.checkConnectionAndStartActivity(this, new Intent(getApplicationContext(), SearchChowActivity.class));
        else if (id == R.id.nav_login) {
            NetworkHelper.checkConnectionAndNotify(this);
            if(NetworkHelper.networkConnectionAvailable(this)) {
                try {
                    identityManager.setUpToAuthenticate(getApplicationContext(), new DefaultSignInResultHandler() {

                        @Override
                        public void onSuccess(Activity activity, IdentityProvider identityProvider) {
                            // User has signed in
                            Log.e("Success", "User signed in");
                            activity.finish();
                        }

                        @Override
                        public boolean onCancel(Activity activity) {
                            return true;
                        }
                    });

                    LoginActivity.startSignInActivity(getApplicationContext(), Application.sAuthUIConfiguration);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (id == R.id.nav_logout) {
            UserHelper.getCurrentUser(getApplicationContext()).signOut();

            // refresh the activity -- easier to reinit everything this way
            this.finish();
            startActivity(this.getIntent());
        }

        NavigationView navigationView = findViewById(R.id.nav_view);

        for (int i = 0; i < navigationView.getMenu().size() ; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}
