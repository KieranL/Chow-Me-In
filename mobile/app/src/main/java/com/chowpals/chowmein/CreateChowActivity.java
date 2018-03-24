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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import helpers.ChowHelper;
import helpers.UserHelper;
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
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ChowHelper.getAllCategories());
        categorySpinner.setAdapter(categoryAdapter);
        createChowButton.setOnClickListener(view -> {
            boolean noInputErrors = false;
            if (!String.valueOf(chowPostNameEditText.getText()).equals("")
                    && !String.valueOf(chowPostDescriptionEditText.getText()).equals("")
                    && !String.valueOf(chowPostLocationEditText.getText()).equals(""))
                noInputErrors = true;
            if (noInputErrors) {
                ArrayList<String> categories = ChowHelper.getAllCategories();
                Chows chow = new Chows()
                        .setId(-1)
                        .setPosterUser(UserHelper.getUsername())
                        .setPosterName(UserHelper.getUsersName())
                        .setPosterEmail(UserHelper.getUserEmail())
                        .setDeleted(false)
                        .setFood(String.valueOf(chowPostNameEditText.getText()).trim())
                        .setLastUpdated(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))
                        .setMeetLocation(String.valueOf(chowPostLocationEditText.getText()).trim())
                        .setMeetTime(getDataPickerString())
                        .setNotes(String.valueOf(chowPostDescriptionEditText.getText()).trim())
                        .setCategory(categories.get(categorySpinner.getSelectedItemPosition()));

                Intent verifyChow = new Intent(this, VerifyChowActivity.class);
                verifyChow.putExtra("Chow", chow);
                startActivity(verifyChow);
            } else {
                Toast.makeText(this, "Your Chow seems to be missing some information. Please fill out the empty fields.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getDataPickerString() {
        if (chowPostDatePicker != null) {
            int day = chowPostDatePicker.getDayOfMonth();
            int month = chowPostDatePicker.getMonth();
            int year =  chowPostDatePicker.getYear();
            int hour = chowPostTimePicker.getHour();
            int min = chowPostTimePicker.getMinute();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day,hour,min,0);

            Date dateObj = calendar.getTime();
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(dateObj);
        } else {
            return "";
        }
    }

}
