package com.mysampleapp.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.amazonaws.mobile.auth.core.IdentityHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.SignInStateChangeListener;
import com.mysampleapp.R;

public class IdentityDemoFragment extends DemoFragmentBase implements SignInStateChangeListener {
    /** Logging tag for this class. */
    private static final String LOG_TAG = IdentityDemoFragment.class.getSimpleName();

    /** The identity manager used to keep track of the current user account. */
    private IdentityManager identityManager;

    /** This fragment's view. */
    private View mFragmentView;

    /** Text view for showing the user identity. */
    private TextView userIdTextView;

    /** Text view for showing the user name. */
    private TextView userNameTextView;

    /** Image view for showing the user image. */
    private ImageView userImageView;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mFragmentView = inflater.inflate(R.layout.fragment_demo_identity, container, false);
        userNameTextView = (TextView) mFragmentView.findViewById(R.id.textView_demoIdentityUserName);
        userIdTextView = (TextView) mFragmentView.findViewById(R.id.textView_demoIdentityUserID);
        userImageView = (ImageView)mFragmentView.findViewById(R.id.imageView_demoIdentityUserImage);

        // Obtain a reference to the identity manager.
        identityManager = IdentityManager.getDefaultIdentityManager();
        identityManager.addSignInStateChangeListener(this);
        fetchUserIdentity();
        return mFragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        identityManager.removeSignInStateChangeListener(this);
    }

    /**
     * Fetches the user identity safely on the background thread.  It may make a network call.
     */
    private void fetchUserIdentity() {
        Log.d(LOG_TAG, "fetchUserIdentity");

        // Pre-fetched to avoid race condition where fragment is no longer active.
        final String unknownUserIdentityText =
                getString(R.string.identity_demo_unknown_identity_text);

        IdentityManager.getDefaultIdentityManager()
                .getUserID(new IdentityHandler() {

            @Override
            public void onIdentityId(String identityId) {

                clearUserInfo();

                // We have successfully retrieved the user's identity. You can use the
                // user identity value to uniquely identify the user. For demonstration
                // purposes here, we will display the value in a text view.
                userIdTextView.setText(identityId);

                if (identityManager.isUserSignedIn()) {
                    userNameTextView.setText("Authenticated User");
                } else {
                    userNameTextView.setText("Guest User");
                }
            }

            @Override
            public void handleError(Exception exception) {

                clearUserInfo();

                // We failed to retrieve the user's identity. Set unknown user identifier
                // in text view.
                userIdTextView.setText(unknownUserIdentityText);

                final Context context = getActivity();

                if (context != null && isAdded()) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.identity_demo_error_dialog_title)
                            .setMessage(getString(R.string.identity_demo_error_message_failed_get_identity)
                                    + exception.getMessage())
                            .setNegativeButton(R.string.identity_demo_dialog_dismiss_text, null)
                            .create()
                            .show();
                }
            }
        });
    }

    private void clearUserInfo() {

        clearUserImage();

        if (isAdded()) {
            try {
                userNameTextView.setText(getString(R.string.unknown_user));
            } catch (final IllegalStateException e) {
                // This can happen when app shuts down and activity is gone
                Log.w(LOG_TAG, "Unable to reset user name back to default.");
            }
        }
    }

    private void clearUserImage() {

        if (isAdded()) {
            try {
                userImageView.setImageResource(R.mipmap.user);
            } catch (final IllegalStateException e) {
                // This can happen when app shuts down and activity is gone
                Log.w(LOG_TAG, "Unable to reset user image back to default image.");
            }
        }
    }

    @Override
    public void onUserSignedIn() {
        // Update the user identity to account for the user signing in.
        fetchUserIdentity();
    }

    @Override
    public void onUserSignedOut() {
        // Update the user identity to account for the user signing out.
        fetchUserIdentity();
    }
}
