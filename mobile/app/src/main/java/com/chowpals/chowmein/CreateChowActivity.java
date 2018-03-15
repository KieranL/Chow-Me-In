package com.chowpals.chowmein;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import business.ChowHandler;
import objects.Chows;

public class CreateChowActivity extends NavBarActivity {

    EditText chowPostNameEditText;
    EditText chowPostLocationEditText;
    DatePicker chowPostDatePicker;
    TimePicker chowPostTimePicker;
    EditText chowPostDescriptionEditText;
    Button createChowButton;
    Spinner categorySpinner;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chow);

        initVariables();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initVariables() {
        chowPostNameEditText = findViewById(R.id.chowPostNameEditText);
        chowPostLocationEditText = findViewById(R.id.chowPostLocationEditText);
        chowPostTimePicker = findViewById(R.id.chowPostTimePicker);
        chowPostDatePicker = findViewById(R.id.chowPostDatePicker);
        chowPostDescriptionEditText = findViewById(R.id.chowPostDescriptionEditText);
        createChowButton = findViewById(R.id.createChowButton);
        categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ChowHandler.getAllCategories());
        categorySpinner.setAdapter(categoryAdapter);
        createChowButton.setOnClickListener(view -> {
            boolean noInputErrors = false;
            if (!String.valueOf(chowPostNameEditText.getText()).equals("")
                    && !String.valueOf(chowPostDescriptionEditText.getText()).equals("")
                    && !String.valueOf(chowPostLocationEditText.getText()).equals(""))
                noInputErrors = true;
            if (noInputErrors) {
                ArrayList<String> categories = ChowHandler.getAllCategories();
                Chows newChow = new Chows(-1, "Mobile Guest", String.valueOf(new Date()), false,
                        String.valueOf(chowPostNameEditText.getText()).trim(), String.valueOf(new Date()),
                        String.valueOf(chowPostLocationEditText.getText()).trim(),
                        String.valueOf(chowPostDatePicker.getYear() + "-" + chowPostDatePicker.getMonth() + "-" + chowPostDatePicker.getDayOfMonth()
                                + " " + chowPostTimePicker.getHour() + ":" + chowPostTimePicker.getMinute()),
                        String.valueOf(chowPostDescriptionEditText.getText()).trim(), categories.get(categorySpinner.getSelectedItemPosition()), "Mobile Guest", "Mobile Guest User", "Poster has no phonenumber");
                Intent verifyChow = new Intent(this, VerifyChowActivity.class);
                verifyChow.putExtra("Chow", newChow);
                startActivity(verifyChow);
            } else {
                Toast.makeText(this, "Your Chow seems to be missing some information. Please fill out the empty fields.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
