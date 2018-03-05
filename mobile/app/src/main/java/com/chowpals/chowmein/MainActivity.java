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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.amazonaws.mobile.auth.core.DefaultSignInResultHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.IdentityProvider;
import com.amazonaws.mobile.auth.ui.SignInActivity;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import business.Application;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        startActivity(viewSelectedChow);
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
        ArrayAdapter<? extends String> resultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResultList);
        chowSearchResultsMain.setAdapter(resultAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
                    ArrayAdapter<? extends String> resultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResultList);
                    chowSearchResultsMain.setAdapter(resultAdapter);
                }, error -> Log.i("error", "Error"));
    }

    private static final String BASE_URL = "https://api.chowme-in.com"; //Previous working API location: "http://chowmein.ca-central-1.elasticbeanstalk.com";


    private static Chows verifyChow(Chows currentChow) {
        if (currentChow.getCreatedTime() == null) {
            currentChow.setCreatedTime("");
        }

        if (currentChow.getFood() == null) {
            currentChow.setFood("");
        }

        if (currentChow.getLastUpdated() == null) {
            currentChow.setLastUpdated("");
        }

        if (currentChow.getMeetLocation() == null) {
            currentChow.setMeetLocation("");
        }

        if (currentChow.getMeetTime() == null) {
            currentChow.setMeetTime("");
        }

        if (currentChow.getNotes() == null) {
            currentChow.setNotes("");
        }
        return currentChow;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_create_chow) {
            startActivity(new Intent(this, CreateChowActivity.class));
        } else if (id == R.id.nav_search_chow) {
            startActivity(new Intent(this, SearchChowActivity.class));
        } else if (id == R.id.nav_login) {
            final WeakReference<MainActivity> self = new WeakReference<MainActivity>(this);

            identityManager.setUpToAuthenticate(this, new DefaultSignInResultHandler() {

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

            SignInActivity.startSignInActivity(this, Application.sAuthUIConfiguration);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void createChow(View view) {
        startActivity(new Intent(this, CreateChowActivity.class));
    }
}
