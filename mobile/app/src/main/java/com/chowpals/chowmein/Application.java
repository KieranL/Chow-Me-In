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

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
import com.amazonaws.mobile.auth.userpools.CognitoUserPoolsSignInProvider;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;

import business.AbstractApplicationLifeCycleHelper;

/**
 * Application class responsible for initializing singletons and other common components.
 */
public class Application extends MultiDexApplication {
    private static final String LOG_TAG = Application.class.getSimpleName();
    public static AWSConfiguration awsConfiguration;
    public static PinpointManager pinpointManager;
    private AbstractApplicationLifeCycleHelper applicationLifeCycleHelper;

    /**
     * To change the logo and background color, use the following API
     *
     * AuthUIConfiguration sAuthUIConfiguration =
     *              new AuthUIConfiguration.Builder()
     *                  .logoResId(R.drawable.image);
     *                  .backgroundColor(Color.BLACK);
     *
     */
    public static AuthUIConfiguration sAuthUIConfiguration =
        new AuthUIConfiguration.Builder()
                .userPools(true)
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
        IdentityManager.getDefaultIdentityManager().addSignInProvider(CognitoUserPoolsSignInProvider.class);


        try {
            final PinpointConfiguration config =
                    new PinpointConfiguration(this,
                            IdentityManager.getDefaultIdentityManager().getCredentialsProvider(),
                            awsConfiguration);
            Application.pinpointManager = new PinpointManager(config);
        } catch (final AmazonClientException ex) {
            Log.e(LOG_TAG, "Unable to initialize PinpointManager. " + ex.getMessage(), ex);
        }

        // The Helper registers itself to receive application lifecycle events when it is constructed.
        // A reference is kept here in order to pass through the onTrimMemory() call from
        // the Application class to properly track when the application enters the background.
        applicationLifeCycleHelper = new AbstractApplicationLifeCycleHelper(this) {
            @Override
            protected void applicationEnteredForeground() {
                Application.pinpointManager.getSessionClient().startSession();
                // handle any events that should occur when your app has come to the foreground...
            }

            @Override
            protected void applicationEnteredBackground() {
                Log.d(LOG_TAG, "Detected application has entered the background.");
                Application.pinpointManager.getSessionClient().stopSession();
                Application.pinpointManager.getAnalyticsClient().submitEvents();
                // handle any events that should occur when your app has gone into the background...
            }
        };
    }

    @Override
    public void onTrimMemory(final int level) {
        Log.d(LOG_TAG, "onTrimMemory " + level);
        applicationLifeCycleHelper.handleOnTrimMemory(level);
        super.onTrimMemory(level);
    }
}
