package com.chowpals.chowmein;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import interfaces.ChowMeInService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import objects.APISuccessObject;
import objects.Chows;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerifyChowActivity extends AppCompatActivity {

    TextView chowInfoTextView;
    Button submitChowButton;
    Chows selectedChow;
    private static final String BASE_URL = "https://api.chowme-in.com"; //Previous working API location: "http://chowmein.ca-central-1.elasticbeanstalk.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_chow);
        initVariables();
        populateChowInfo();
    }

    private void populateChowInfo() {
        chowInfoTextView.setText(selectedChow.toString());
    }

    private void initVariables() {
        Intent submittedChow = getIntent();
        selectedChow = (Chows) submittedChow.getSerializableExtra("Chow");
        chowInfoTextView = findViewById(R.id.chowInfoTextView);
        submitChowButton = findViewById(R.id.submitChowButton);
        submitChowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (networkConnectionAvailable()) {
                        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create());

                        Retrofit retrofit = builder.build();
                        ChowMeInService apiClient = retrofit.create(ChowMeInService.class);

                        apiClient.createChows(selectedChow).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                .subscribe((APISuccessObject response) -> {
                                    if (response.isSuccess()) {
                                        Log.i("Success", "Success");
                                        Toast.makeText(VerifyChowActivity.this, "Your Chow was posted!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(VerifyChowActivity.this,MainActivity.class));
                                    } else {
                                        Log.i("error", "Error");
                                        Toast.makeText(VerifyChowActivity.this, "Your Chow was not posted. We are experiencing difficulties, please hold on!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                });
                    } else {
                        Toast.makeText(VerifyChowActivity.this, "It seems your not connected to the Internet. Please connect to the Internet to post your chow.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(VerifyChowActivity.this, "Your Chow was not posted. We are experiencing difficulties, please hold on!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private boolean networkConnectionAvailable() {
        ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectionManager != null;
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
