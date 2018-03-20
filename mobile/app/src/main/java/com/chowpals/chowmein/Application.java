//
// Copyright 2017 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to 
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.19
//
package com.chowpals.chowmein;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.google.GoogleButton;
import com.amazonaws.mobile.auth.google.GoogleSignInProvider;
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.chowpals.chowmein.login.ChowmeinUserPoolsSignInProvider;
import com.google.android.gms.common.Scopes;

import business.AbstractApplicationLifeCycleHelper;

import static android.graphics.Color.rgb;

/**
 * Application class responsible for initializing singletons and other common components.
 */
public class Application extends MultiDexApplication {
    private static final String LOG_TAG = Application.class.getSimpleName();
    public static AWSConfiguration awsConfiguration;
    private AbstractApplicationLifeCycleHelper applicationLifeCycleHelper;


    public static AuthUIConfiguration sAuthUIConfiguration =
        new AuthUIConfiguration.Builder()
                .userPools(true)
                .logoResId(R.drawable.chowmein_cat)
                .backgroundColor(rgb(245, 245, 245))
                .isBackgroundColorFullScreen(true)
                .signInButton(GoogleButton.class)
                .build();

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "Application.onCreate - Initializing application...");
        super.onCreate();

        initializeApplication();
        Log.d(LOG_TAG, "Application.onCreate - Application initialized OK");
    }


    private void initializeApplication() {
        awsConfiguration = new AWSConfiguration(this);

        if (IdentityManager.getDefaultIdentityManager() == null) {
            final IdentityManager identityManager = new IdentityManager(getApplicationContext(), awsConfiguration);
            IdentityManager.setDefaultIdentityManager(identityManager);
        }

        // Add UserPools as an SignIn Provider.
        IdentityManager.getDefaultIdentityManager().addSignInProvider(ChowmeinUserPoolsSignInProvider.class);
        IdentityManager.getDefaultIdentityManager().addSignInProvider(com.amazonaws.mobile.auth.google.GoogleSignInProvider.class);

        GoogleSignInProvider.setPermissions(Scopes.EMAIL, Scopes.PROFILE);
    }

    @Override
    public void onTrimMemory(final int level) {
        Log.d(LOG_TAG, "onTrimMemory " + level);
        if(applicationLifeCycleHelper != null)
            applicationLifeCycleHelper.handleOnTrimMemory(level);
        super.onTrimMemory(level);
    }
}
