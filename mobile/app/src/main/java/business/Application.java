//
// Copyright 2017 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to 
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.19
//
package business;

import android.graphics.Color;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
import com.amazonaws.mobile.auth.userpools.CognitoUserPoolsSignInProvider;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.chowpals.chowmein.R;

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
                    .backgroundColor(Color.BLACK)
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
    }

    @Override
    public void onTrimMemory(final int level) {
        Log.d(LOG_TAG, "onTrimMemory " + level);
        applicationLifeCycleHelper.handleOnTrimMemory(level);
        super.onTrimMemory(level);
    }
}
