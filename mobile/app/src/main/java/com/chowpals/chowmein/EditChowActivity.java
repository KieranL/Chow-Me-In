package com.chowpals.chowmein;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import helpers.ChowHelper;
import helpers.UserHelper;
import interfaces.ChowMeInService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import objects.APISuccessObject;
import objects.Chows;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditChowActivity extends NavBarActivity {

    public static final SimpleDateFormat ISO_DATA_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    EditText chowPostNameEditText;
    EditText chowPostLocationEditText;
    DatePicker chowPostDatePicker;
    TimePicker chowPostTimePicker;
    EditText chowPostDescriptionEditText;
    Button createChowButton;
    Spinner categorySpinner;
    Chows selectedChow;
    private boolean isNewChow = true;
    public static final String CHOW_EXTRA = "Chow";


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chow);
        Intent intent = getIntent();

        if(intent.hasExtra(CHOW_EXTRA)) {
            isNewChow = false;
            selectedChow = (Chows) intent.getSerializableExtra(CHOW_EXTRA);
        }

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

                if(isNewChow) {
                    selectedChow = new Chows();
                }

                selectedChow
                        .setId(-1)
                        .setPosterUser(UserHelper.getUsername())
                        .setPosterName(UserHelper.getUsersName())
                        .setPosterEmail(UserHelper.getUserEmail())
                        .setDeleted(false)
                        .setFood(String.valueOf(chowPostNameEditText.getText()).trim())
                        .setLastUpdated(ISO_DATA_FORMAT.format(new Date()))
                        .setMeetLocation(String.valueOf(chowPostLocationEditText.getText()).trim())
                        .setMeetTime(getDataPickerString())
                        .setNotes(String.valueOf(chowPostDescriptionEditText.getText()).trim())
                        .setCategory(categories.get(categorySpinner.getSelectedItemPosition()));

                if(isNewChow) {
                    Intent verifyChow = new Intent(this, VerifyChowActivity.class);
                    verifyChow.putExtra("Chow", selectedChow);
                    startActivity(verifyChow);
                }else {
                    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create());

                    Retrofit retrofit = builder.build();
                    ChowMeInService apiClient = retrofit.create(ChowMeInService.class);

                    apiClient.updateSelectChows(selectedChow.getId(), selectedChow).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe((APISuccessObject response) -> {
                                if (response.isSuccess()) {
                                    Log.i("Success", "Success");
                                    Toast.makeText(EditChowActivity.this, "Your Chow was updated!", Toast.LENGTH_SHORT).show();
                                    Intent data = new Intent();
                                    data.putExtra(CHOW_EXTRA, selectedChow);
                                    setResult(ViewChowActivity.REQUEST_CODE, data);
                                } else {
                                    Log.i("error", "Error");
                                    Toast.makeText(EditChowActivity.this, "Your Chow was not updated. We are experiencing difficulties, please hold on!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                    finish();
                }
            } else {
                Toast.makeText(this, "Your Chow seems to be missing some information. Please fill out the empty fields.", Toast.LENGTH_SHORT).show();
            }
        });

        if(!isNewChow)
            setChowFields();
    }

    private void setChowFields() {
        ArrayList<String> categories = ChowHelper.getAllCategories();
        chowPostNameEditText.setText(selectedChow.getFood());
        chowPostLocationEditText.setText(selectedChow.getMeetLocation());
        chowPostDescriptionEditText.setText(selectedChow.getNotes());
        categorySpinner.setSelection(categories.indexOf(selectedChow.getCategory()));
        try {
            Date chowTime = ISO_DATA_FORMAT.parse(selectedChow.getMeetTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(chowTime);
            chowPostTimePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            chowPostTimePicker.setMinute(calendar.get(Calendar.MINUTE));
            chowPostDatePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String getDataPickerString() {
        if (chowPostDatePicker != null) {
            int day = chowPostDatePicker.getDayOfMonth();
            int month = chowPostDatePicker.getMonth();
            int year =  chowPostDatePicker.getYear();
            int hour = chowPostTimePicker.getHour();
            int minute = chowPostTimePicker.getMinute();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hour, minute);

            Date dateObj = calendar.getTime();
            return ISO_DATA_FORMAT.format(dateObj);
        } else {
            return "";
        }
    }

}
