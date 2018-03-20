/*
  * Copyright 2013-2017 Amazon.com, Inc. or its affiliates.
  * All Rights Reserved.
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *     http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */

package com.chowpals.chowmein;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.auth.userpools.FormEditText;
import com.amazonaws.mobile.auth.userpools.FormView;
import com.amazonaws.mobile.auth.userpools.SignUpView;


/**
 * Activity to prompt for account sign up information.
 */
public class SignUpActivity extends Activity {
    private SignUpView signUpView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.amazonaws.mobile.auth.userpools.R.layout.activity_sign_up);

        signUpView = (SignUpView) findViewById(com.amazonaws.mobile.auth.userpools.R.id.signup_view);

        // Remove "phone" field
        FormView form = (FormView) signUpView.getChildAt(1);
        form.removeViews(7, 2);

        // Rename from "Given Name" to "Name"
        FormEditText name = (FormEditText) form.getChildAt(4);
        name.getEditTextView().setHint("Name");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Retrieve input and return to caller.
     * @param view the Android View
     */
    public void signUp(final View view) {

        final String username = signUpView.getUserName();
        final String password = signUpView.getPassword();
        final String givenName = signUpView.getGivenName();
        final String email = signUpView.getEmail();

        final Intent intent = new Intent();
        intent.putExtra(ChowmeinUserPoolsSignInProvider.AttributeKeys.USERNAME, username);
        intent.putExtra(ChowmeinUserPoolsSignInProvider.AttributeKeys.PASSWORD, password);
        intent.putExtra(ChowmeinUserPoolsSignInProvider.AttributeKeys.GIVEN_NAME, givenName);
        intent.putExtra(ChowmeinUserPoolsSignInProvider.AttributeKeys.EMAIL_ADDRESS, email);

        setResult(RESULT_OK, intent);

        finish();
    }
}
