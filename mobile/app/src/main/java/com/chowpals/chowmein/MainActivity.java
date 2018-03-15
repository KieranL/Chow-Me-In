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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.amazonaws.mobile.auth.core.DefaultSignInResultHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.IdentityProvider;
import com.amazonaws.mobile.auth.ui.SignInActivity;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import business.NetworkHelper;
import business.UserHelper;
import interfaces.ChowMeInService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import objects.Chows;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SearchView chowSearchViewMain;
    ListView chowSearchResultsMain;
    ArrayList<Chows> chowsListedMain;
    ArrayList<Chows> masterChowListMain;
    private IdentityManager identityManager;

    private static final String BASE_URL = "https://api.chowme-in.com"; //Previous working API location: "http://chowmein.ca-central-1.elasticbeanstalk.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkHelper.checkConnectionAndNotify(this);

        identityManager = IdentityManager.getDefaultIdentityManager();
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        initVariables();
        prepopulateList();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        NavigationView navigationView = findViewById(R.id.nav_view);

        // Only show either the login or logout button
        if(identityManager.isUserSignedIn()) {
            navigationView.getMenu().getItem(3).setVisible(false);
            navigationView.getMenu().getItem(4).setVisible(true);
        } else {
            navigationView.getMenu().getItem(3).setVisible(true);
            navigationView.getMenu().getItem(4).setVisible(false);
        }

        TextView welcomeMsg = findViewById(R.id.welcomeMessageTextView);

        // provide a callback that will update the activity welcome message
        GetDetailsHandler handler = new GetDetailsHandler() {
            @Override
            public void onSuccess(final CognitoUserDetails list) {
                // Successfully retrieved user details, update welcome message for a friendly greeting!!
                welcomeMsg.setText("Welcome, " + list.getAttributes().getAttributes().get("name") + "!");
            }

            @Override
            public void onFailure(final Exception exception) {
                // Failed to retrieve the user details, probe exception for the cause
                System.err.println(exception);
            }
        };

        UserHelper.handleUserDetails(getApplicationContext(), handler);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            finish();
    }

    private void initVariables() {
        chowsListedMain = new ArrayList<>();
        masterChowListMain = new ArrayList<>();
        chowSearchResultsMain = findViewById(R.id.searchChowListViewMain);
        chowSearchViewMain = findViewById(R.id.chowSearchViewMain);
        chowSearchViewMain.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getResultsAdapter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getResultsAdapter(s);
                return false;
            }
        });
        chowSearchResultsMain.setOnItemClickListener((adapterView, view, i, l) -> {
            Chows selectedChow = chowsListedMain.get(i);
            viewChow(selectedChow);
        });
    }

    private void viewChow(Chows selectedChow) {
        Intent viewSelectedChow = new Intent(this, ViewChowActivity.class);
        viewSelectedChow.putExtra("Selected Chow", selectedChow);
        NetworkHelper.checkConnectionAndStartActivity(this, viewSelectedChow);
    }

    private void getResultsAdapter(CharSequence query) {
        ArrayList<String> searchResultList = new ArrayList<>();
        ArrayList<Chows> temp = new ArrayList<>();
        chowsListedMain = masterChowListMain;
        for (int i = 0; i < chowsListedMain.size(); i++) {
            if (chowsListedMain.get(i).getFood().contains(query)) {
                searchResultList.add(chowsListedMain.get(i).getFood());
                temp.add(chowsListedMain.get(i));
            }
        }
        chowsListedMain = temp;
        ArrayAdapter<? extends String> resultAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, searchResultList);
        chowSearchResultsMain.setAdapter(resultAdapter);
    }

    private void prepopulateList() {
        ArrayList<String> searchResultList = new ArrayList<>();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Retrofit retrofit = builder.build();
        ChowMeInService apiClient = retrofit.create(ChowMeInService.class);

        apiClient.listChows().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<Chows> response) -> {
                    for (Chows currentChow : response) {
                        currentChow = (verifyChow(currentChow));
                        chowsListedMain.add(currentChow);
                        searchResultList.add(currentChow.getFood());
                    }
                    masterChowListMain = chowsListedMain;
                    ArrayAdapter<? extends String> resultAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, searchResultList);
                    chowSearchResultsMain.setAdapter(resultAdapter);
                }, error -> Log.i("error", "Error"));
    }


    private static Chows verifyChow(Chows currentChow) {
        if (currentChow.getCreatedTime() == null)
            currentChow.setCreatedTime("");

        if (currentChow.getFood() == null)
            currentChow.setFood("");

        if (currentChow.getLastUpdated() == null)
            currentChow.setLastUpdated("");

        if (currentChow.getMeetLocation() == null)
            currentChow.setMeetLocation("");

        if (currentChow.getMeetTime() == null)
            currentChow.setMeetTime("");

        if (currentChow.getNotes() == null)
            currentChow.setNotes("");

        return currentChow;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_create_chow)
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

                    SignInActivity.startSignInActivity(getApplicationContext(), Application.sAuthUIConfiguration);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (id == R.id.nav_logout) {
            identityManager.signOut();

            // refresh the activity -- easier to reinit everything this way
            this.finish();
            startActivity(this.getIntent());
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void createChow(View view) {
        NetworkHelper.checkConnectionAndStartActivity(this, new Intent(getApplicationContext(), CreateChowActivity.class));
    }
}
