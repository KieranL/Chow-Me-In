package com.chowpals.chowmein;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;

public class SignupActivity extends AppCompatActivity {

    EditText userGivenNameEditText;
    EditText phoneNumberEditText;
    EditText emailAddressEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initVariables();

    }

    private void initVariables() {
        userGivenNameEditText = findViewById(R.id.userNameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        emailAddressEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passEditText);
    }

    public void signUp(View v) {
        // Create a CognitoUserAttributes object and add user attributes
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        CognitoUserPool userPool = new CognitoUserPool(this, "us-east-2_sfzkvLZDl", "6s28e9fcoakqhdm5nrud946d5p", "7kt43skg3flg6ip2hfup5if2of5erqttej10hseahvaihau4p1g", clientConfiguration);
        CognitoUserAttributes userAttributes = new CognitoUserAttributes();

        String userId = String.valueOf(userGivenNameEditText.getText());
        String phoneNumber = String.valueOf(phoneNumberEditText.getText());
        String email = String.valueOf(emailAddressEditText.getText());
        String password = String.valueOf(passwordEditText.getText());
        userAttributes.addAttribute("given_name", userId);
        userAttributes.addAttribute("phone_number", phoneNumber);
        userAttributes.addAttribute("email", email);

        SignUpHandler signupCallback = new SignUpHandler() {

            @Override
            public void onSuccess(CognitoUser cognitoUser, boolean userConfirmed, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
                // Sign-up was successful

                // Check if this user (cognitoUser) needs to be confirmed
                if (!userConfirmed) {
                    // This user must be confirmed and a confirmation code was sent to the user
                    // cognitoUserCodeDeliveryDetails will indicate where the confirmation code was sent
                    // Get the confirmation code from user
                    Toast.makeText(SignupActivity.this, "A confirmation code has already been sent to you please confirm your account", Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity.currentUser = cognitoUser;
                    Toast.makeText(SignupActivity.this, "Your account has already been confirmed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(SignupActivity.this, "Your signup was not successful", Toast.LENGTH_SHORT).show();
            }
        };
        
    }
}
