package com.chowpals.chowmein;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import helpers.NetworkHelper;
import helpers.UserHelper;
import interfaces.ChowMeInService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import objects.Chows;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends NavBarActivity {

    SearchView chowSearchViewMain;
    ListView chowSearchResultsMain;
    ArrayList<Chows> chowsListedMain;
    ArrayList<Chows> masterChowListMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariables();
        prepopulateList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView welcomeMsg = findViewById(R.id.welcomeMessageTextView);
        if(UserHelper.isUserSignedIn()) {
            // async update the textview
            new Thread(() -> {
                StringBuilder sb = new StringBuilder();

                sb.append("Howdy, ");
                sb.append(UserHelper.getUsersName());
                sb.append("!");

                runOnUiThread(()-> {
                    welcomeMsg.setText(sb);
                });

            }).start();
        }
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
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create());

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

    public void createChow(View view) {
        NetworkHelper.checkConnectionAndStartActivity(this, new Intent(getApplicationContext(), CreateChowActivity.class));
    }
}
