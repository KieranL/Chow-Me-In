package com.chowpals.chowmein;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import helpers.NetworkHelper;
import helpers.UserHelper;
import interfaces.ChowMeInService;
import objects.APISuccessObject;
import objects.Chows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewChowActivity extends NavBarActivity {

    TextView chowInfoTextView;
    Button acceptChowButton;
    Chows selectedChow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chow);

        initVariables();
        populateChowInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (UserHelper.isUserSignedIn() && !UserHelper.getUsername().equals(selectedChow.getPosterUser())) {
            acceptChowButton.setEnabled(true);
            acceptChowButton.setAlpha(1.0f);
        } else {
            acceptChowButton.setEnabled(false);
            acceptChowButton.setAlpha(0.3f);
        }
    }

    private void populateChowInfo() {
        chowInfoTextView.setText(selectedChow.toString());
    }

    private void initVariables() {
        Intent searchChowResult = getIntent();
        selectedChow = (Chows) searchChowResult.getSerializableExtra("Selected Chow");
        chowInfoTextView = findViewById(R.id.chowInfoTextView);
        acceptChowButton = findViewById(R.id.acceptChowButton);
        acceptChowButton.setOnClickListener(view -> {
            if (NetworkHelper.networkConnectionAvailable(this) && UserHelper.isUserSignedIn()) {
                Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create());

                Retrofit retrofit = builder.build();
                ChowMeInService apiClient = retrofit.create(ChowMeInService.class);

                selectedChow.setJoinedUser(UserHelper.getUsername());
                selectedChow.setJoinedName(UserHelper.getUsersName());
                selectedChow.setJoinedEmail(UserHelper.getUserEmail());
                Call<APISuccessObject> joinChow = apiClient.updateSelectChows(selectedChow.getId(), selectedChow);
                joinChow.enqueue(new Callback<APISuccessObject>() {
                    @Override
                    public void onResponse(@NonNull Call<APISuccessObject> call, @NonNull Response<APISuccessObject> response) {
                        if (response.code() == 200) {
                            Log.i("Success", "Success");
                            Toast.makeText(ViewChowActivity.this, "You Chowed in!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ViewChowActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(ViewChowActivity.this, response.code() + " Error", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<APISuccessObject> call, @NonNull Throwable t) {
                        Log.i("error", "Error");
                        Toast.makeText(ViewChowActivity.this, "You did not Chow in. We are experiencing difficulties, please hold on!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
//*/
            }
        });
    }
}
