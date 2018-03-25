package com.chowpals.chowmein;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import helpers.NetworkHelper;
import helpers.UserHelper;
import interfaces.ChowMeInService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import objects.APISuccessObject;
import objects.Chows;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.chowpals.chowmein.EditChowActivity.CHOW_EXTRA;

public class ViewChowActivity extends NavBarActivity {

    public static final int REQUEST_CODE = 42069;
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
            NetworkHelper.checkConnectionAndDoRunnable(
                    this, () -> UserHelper.checkLoginAndDoRunnable(this, UserHelper.chowMeIn(this, this, selectedChow)));
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (isUserChowOwner()) {
            getMenuInflater().inflate(R.menu.menu_view_chow, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_chow) {
            if (isUserChowOwner()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure you want to delete this chow?");
                builder.setPositiveButton("Yes", (dialogInterface, i) -> deleteChow());
                builder.setNegativeButton("No", (dialogInterface, i) -> {
                });
                builder.show();
            }
        } else if (id == R.id.action_edit_chow) {
            if (isUserChowOwner()) {
                editChow();
            }
        }

        return true;
    }

    private void editChow() {
        Intent intent = new Intent(getApplicationContext(), EditChowActivity.class);
        intent.putExtra(CHOW_EXTRA, selectedChow);
        NetworkHelper.checkConnectionAndDoRunnable(this, () ->
                UserHelper.checkLoginAndStartActivityForResult(this, intent, REQUEST_CODE));
    }

    private boolean isUserChowOwner() {
        return UserHelper.isUserSignedIn() && selectedChow.getPosterUser().equals(UserHelper.getUsername());
    }

    private void deleteChow() {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Retrofit retrofit = builder.build();
        ChowMeInService apiClient = retrofit.create(ChowMeInService.class);

        apiClient.deleteSelectChows(selectedChow.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe((APISuccessObject response) -> {
                    if (response.isSuccess()) {
                        Log.i("Success", "Success");
                        Toast.makeText(ViewChowActivity.this, "Your Chow was deleted!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ViewChowActivity.this, MainActivity.class));
                    } else {
                        Log.i("error", "Error");
                        Toast.makeText(ViewChowActivity.this, "Your Chow was not deleted. We are experiencing difficulties, please hold on!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                selectedChow = (Chows) data.getSerializableExtra(CHOW_EXTRA);
                populateChowInfo();
            }
        }
    }

}
