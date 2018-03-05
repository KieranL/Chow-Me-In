//
// Copyright 2017 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to 
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.19
//
package com.mysampleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
import com.amazonaws.mobile.auth.core.DefaultSignInResultHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.IdentityProvider;
import com.amazonaws.mobile.auth.core.StartupAuthErrorDetails;
import com.amazonaws.mobile.auth.core.StartupAuthResult;
import com.amazonaws.mobile.auth.core.StartupAuthResultHandler;
import com.amazonaws.mobile.auth.core.signin.AuthException;

import com.amazonaws.mobile.auth.ui.SignInActivity;

import java.lang.ref.WeakReference;

/**
 * Splash Activity is the start-up activity that appears until a delay is expired
 * or the user taps the screen.  When the splash activity starts, various app
 * initialization operations are performed.
 */
public class SplashActivity extends Activity implements StartupAuthResultHandler {

    private static final String LOG_TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final IdentityManager identityManager = IdentityManager.getDefaultIdentityManager();

        identityManager.resumeSession(this, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Touch event bypasses waiting for the splash timeout to expire.
        IdentityManager.getDefaultIdentityManager().expireSignInTimeout();
        return true;
    }

    @Override
    public void onComplete(StartupAuthResult authResult) {
        final IdentityManager identityManager = authResult.getIdentityManager();

        if (authResult.isUserSignedIn()) {
            // User has successfully signed in with an identity provider.
            final IdentityProvider provider = identityManager.getCurrentIdentityProvider();
            Log.d(LOG_TAG, "Signed in with " + provider.getDisplayName());
            // If we were signed in previously with a provider indicate that to the user with a toast.
            Toast.makeText(SplashActivity.this, String.format("Signed in with %s",
                    provider.getDisplayName()), Toast.LENGTH_LONG).show();
        } else if (authResult.isUserAnonymous()) {
            // User has an unauthenticated anonymous (guest) identity, either because the user never previously
            // signed in with any identity provider or because refreshing the provider credentials failed.

            // Optionally, you can check whether refreshing a previously signed in provider failed.
            final StartupAuthErrorDetails errors = authResult.getErrorDetails();
            if (errors.didErrorOccurRefreshingProvider()) {
                final AuthException providerAuthException = errors.getProviderRefreshException();
                Log.w(LOG_TAG, String.format(
                        "Credentials for Previously signed-in provider %s could not be refreshed.",
                        providerAuthException.getProvider().getDisplayName()),
                        providerAuthException);
            }

            Log.d(LOG_TAG, "Continuing with unauthenticated (guest) identity.");
        } else {
            // User has no identity because authentication was unsuccessful due to a failure.
            final StartupAuthErrorDetails errors = authResult.getErrorDetails();
            Log.e(LOG_TAG, "No Identity could be obtained. Continuing with no identity.",
                    errors.getUnauthenticatedErrorException());
        }
        this.startActivity(new Intent(this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        this.finish();
    }
}
