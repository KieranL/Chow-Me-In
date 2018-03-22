package com.chowpals.chowmein;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import helpers.UserHelper;
import objects.Chows;

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
    }

}
