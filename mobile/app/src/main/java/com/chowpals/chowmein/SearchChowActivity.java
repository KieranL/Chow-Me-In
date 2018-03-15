package com.chowpals.chowmein;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import business.NetworkHelper;
import interfaces.ChowMeInService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import objects.Chows;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchChowActivity extends NavBarActivity {

    SearchView chowSearchView;
    ListView chowSearchResults;
    ArrayList<Chows> chowsListed;
    ArrayList<Chows> masterChowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chow);

        initVariables();
        prepopulateList();
    }

    private void getResultsAdapter(CharSequence query) {
        ArrayList<String> searchResultList = new ArrayList<>();
        ArrayList<Chows> temp = new ArrayList<>();
        chowsListed = masterChowList;
        for (int i = 0; i < chowsListed.size(); i++) {
            if (chowsListed.get(i).getFood().contains(query)) {
                searchResultList.add(chowsListed.get(i).getFood());
                temp.add(chowsListed.get(i));
            }
        }
        chowsListed = temp;
        ArrayAdapter<? extends String> resultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResultList);
        chowSearchResults.setAdapter(resultAdapter);
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
                        chowsListed.add(currentChow);
                        searchResultList.add(currentChow.getFood());
                    }
                    masterChowList = chowsListed;
                    ArrayAdapter<? extends String> resultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResultList);
                    chowSearchResults.setAdapter(resultAdapter);
                }, error -> Log.i("error", "Error"));
    }


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

        if (currentChow.getCategory() == null) {
            currentChow.setCategory("None");
        }

        if (currentChow.getPosterPhone() == null) {
            currentChow.setPosterPhone("");
        }

        if (currentChow.getPosterName() == null) {
            currentChow.setPosterName("");
        }

        if (currentChow.getPosterUser() == null) {
            currentChow.setPosterUser("");
        }
        return currentChow;
    }

    private void initVariables() {
        chowsListed = new ArrayList<>();
        masterChowList = new ArrayList<>();
        chowSearchView = findViewById(R.id.chowSearchView);
        chowSearchResults = findViewById(R.id.chowSearchResults);
        chowSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        chowSearchResults.setOnItemClickListener((adapterView, view, i, l) -> {
            Chows selectedChow = chowsListed.get(i);
            viewChow(selectedChow);
        });
    }

    private void viewChow(Chows selectedChow) {
        Intent viewSelectedChow = new Intent(this, ViewChowActivity.class);
        viewSelectedChow.putExtra("Selected Chow", selectedChow);
        NetworkHelper.checkConnectionAndStartActivity(this, viewSelectedChow);
    }

}
