package com.chowpals.chowmein;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import objects.Chows;

public class ViewChowActivity extends NavBarActivity {

    TextView chowInfoTextView;
    Button acceptChowButton;
    Chows selectedChow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();
        populateChowInfo();
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
