package com.chowpals.chowmein;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import helpers.NetworkHelper;
import helpers.UserHelper;
import interfaces.ChowMeInService;
import objects.Chows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static helpers.ChowHelper.ensureChowFields;

public class ViewMyChowsActivity extends NavBarActivity {

    SearchView chowsPostedByMeSearchViewMain;
    ListView chowsPostedByMeSearchResultsMain;
    ArrayList<Chows> chowsPostedByMeListedMain;
    ArrayList<Chows> masterchowsPostedByMeChowListMain;
    SearchView chowsJoinedByMeSearchViewMain;
    ListView chowsJoinedByMeSearchResultsMain;
    ArrayList<Chows> chowsJoinedByMeListedMain;
    ArrayList<Chows> masterchowsJoinedByMeChowListMain;
    private String token ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_chows);

        initVariables();
        prepopulateJoinedList();
        prepopulatePostedList();
    }

    private void prepopulateJoinedList() {
        ArrayList<String> searchResultList = new ArrayList<>();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Retrofit retrofit = builder.build();

        ChowMeInService apiClient = retrofit.create(ChowMeInService.class);

        Call<List<Chows>> join = apiClient.getJoinedChows(token);
        join.enqueue(new Callback<List<Chows>>() {
            @Override
            public void onResponse(@NonNull Call<List<Chows>> call, @NonNull Response<List<Chows>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        Chows currentChow = response.body().get(i);
                        currentChow = (ensureChowFields(currentChow));
                        chowsJoinedByMeListedMain.add(currentChow);
                        searchResultList.add(currentChow.getFood());
                    }
                    masterchowsJoinedByMeChowListMain = chowsJoinedByMeListedMain;
                    ArrayAdapter<? extends String> resultAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, searchResultList);
                    chowsJoinedByMeSearchResultsMain.setAdapter(resultAdapter);
                    chowsJoinedByMeSearchResultsMain.setOnItemClickListener((adapterView, view, i, l) -> {
                        Chows selectedChow = masterchowsJoinedByMeChowListMain.get(i);
                        viewChow(selectedChow);
                    });
                } else {
                    searchResultList.add("You haven't joined any Chows!");
                    ArrayAdapter<? extends String> resultAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, searchResultList);
                    chowsJoinedByMeSearchResultsMain.setAdapter(resultAdapter);
                    chowsJoinedByMeSearchResultsMain.setOnItemClickListener(null);
                }
            }

            @Override
            public void onFailure(Call<List<Chows>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"There was an issue getting your Chows back",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepopulatePostedList() {
        ArrayList<String> searchResultList = new ArrayList<>();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Retrofit retrofit = builder.build();

        ChowMeInService apiClient = retrofit.create(ChowMeInService.class);

        Call<List<Chows>> join = apiClient.getPostedChows(token);
        join.enqueue(new Callback<List<Chows>>() {
            @Override
            public void onResponse(@NonNull Call<List<Chows>> call, @NonNull Response<List<Chows>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        Chows currentChow = response.body().get(i);
                        currentChow = (ensureChowFields(currentChow));
                        chowsPostedByMeListedMain.add(currentChow);
                        searchResultList.add(currentChow.getFood());
                    }
                    masterchowsPostedByMeChowListMain = chowsPostedByMeListedMain;
                    ArrayAdapter<? extends String> resultAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, searchResultList);
                    chowsPostedByMeSearchResultsMain.setAdapter(resultAdapter);
                    chowsPostedByMeSearchResultsMain.setOnItemClickListener((adapterView, view, i, l) -> {
                        Chows selectedChow = masterchowsPostedByMeChowListMain.get(i);
                        viewChow(selectedChow);
                    });
                } else {
                    searchResultList.add("You haven't posted any Chows!");
                    ArrayAdapter<? extends String> resultAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, searchResultList);
                    chowsPostedByMeSearchResultsMain.setAdapter(resultAdapter);
                    chowsPostedByMeSearchResultsMain.setOnItemClickListener(null);
                }
            }

            @Override
            public void onFailure(Call<List<Chows>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"There was an issue getting your Chows back",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initVariables() {
        token = UserHelper.getAccessToken();

        chowsPostedByMeListedMain = new ArrayList<>();
        masterchowsPostedByMeChowListMain = new ArrayList<>();
        chowsJoinedByMeListedMain = new ArrayList<>();
        masterchowsJoinedByMeChowListMain = new ArrayList<>();
        chowsPostedByMeSearchViewMain = findViewById(R.id.chowsSearchViewMain);
        chowsPostedByMeSearchResultsMain = findViewById(R.id.searchChowsCreatedByUserListViewMain);
        chowsJoinedByMeSearchViewMain = findViewById(R.id.chowsUserHasJoinedSearchViewMain);
        chowsJoinedByMeSearchResultsMain = findViewById(R.id.searchchowsUserHasJoinedListViewMain);
        chowsPostedByMeSearchViewMain.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getResultsAdapterPostedByMe(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getResultsAdapterPostedByMe(s);
                return false;
            }
        });
        chowsPostedByMeSearchResultsMain.setOnItemClickListener((adapterView, view, i, l) -> {
            Chows selectedChow = masterchowsPostedByMeChowListMain.get(i);
            viewChow(selectedChow);
        });
        chowsJoinedByMeSearchViewMain.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getResultsAdapterJoinedByMe(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getResultsAdapterJoinedByMe(s);
                return false;
            }
        });
    }

    private void viewChow(Chows selectedChow) {
        Intent viewSelectedChow = new Intent(this, ViewChowActivity.class);
        viewSelectedChow.putExtra("Selected Chow", selectedChow);
        viewSelectedChow.putExtra("Calling Class","ViewMyChowsActivity");
        NetworkHelper.checkConnectionAndStartActivity(this, viewSelectedChow);
    }

    private void getResultsAdapterPostedByMe(CharSequence query) {
        ArrayList<String> searchResultList = new ArrayList<>();
        ArrayList<Chows> temp = new ArrayList<>();
        chowsPostedByMeListedMain = masterchowsPostedByMeChowListMain;
        for (int i = 0; i < chowsPostedByMeListedMain.size(); i++) {
            if (chowsPostedByMeListedMain.get(i).getFood().contains(query)) {
                searchResultList.add(chowsPostedByMeListedMain.get(i).getFood());
                temp.add(chowsPostedByMeListedMain.get(i));
            }
        }
        chowsPostedByMeListedMain = temp;
        ArrayAdapter<? extends String> resultAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, searchResultList);
        chowsPostedByMeSearchResultsMain.setAdapter(resultAdapter);
    }

    private void getResultsAdapterJoinedByMe(CharSequence query) {
        ArrayList<String> searchResultList = new ArrayList<>();
        ArrayList<Chows> temp = new ArrayList<>();
        chowsJoinedByMeListedMain = masterchowsJoinedByMeChowListMain;
        for (int i = 0; i < chowsJoinedByMeListedMain.size(); i++) {
            if (chowsJoinedByMeListedMain.get(i).getFood().contains(query)) {
                searchResultList.add(chowsJoinedByMeListedMain.get(i).getFood());
                temp.add(chowsJoinedByMeListedMain.get(i));
            }
        }
        chowsJoinedByMeListedMain = temp;
        ArrayAdapter<? extends String> resultAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, searchResultList);
        chowsJoinedByMeSearchResultsMain.setAdapter(resultAdapter);
    }

}
