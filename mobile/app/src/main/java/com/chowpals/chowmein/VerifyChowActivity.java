package com.chowpals.chowmein;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import business.NetworkHelper;
import interfaces.ChowMeInService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import objects.APISuccessObject;
import objects.Chows;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerifyChowActivity extends NavBarActivity {

    TextView chowInfoTextView;
    Button submitChowButton;
    Chows selectedChow;

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
        Activity self = this;

        Intent submittedChow = getIntent();
        selectedChow = (Chows) submittedChow.getSerializableExtra("Chow");
        chowInfoTextView = findViewById(R.id.chowInfoTextView);
        submitChowButton = findViewById(R.id.submitChowButton);
        submitChowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (NetworkHelper.networkConnectionAvailable(self)) {
                        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create());

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
                    }
                } catch (Exception e) {
                    Toast.makeText(VerifyChowActivity.this, "Your Chow was not posted. We are experiencing difficulties, please hold on!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
