package helpers;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.IdentityProvider;
import com.amazonaws.mobile.auth.google.GoogleSignInProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.chowpals.chowmein.login.ChowmeinUserPoolsSignInProvider;

public class UserHelper {
    public static boolean isUserSignedIn() {
        return getIdentityProvider() != null;
    }

    public static void checkLoginAndStartActivity(Activity activity, Intent newActivity) {
        checkLoginAndDoRunnable(activity, ()->activity.startActivity(newActivity));
    }

    public static void checkLoginAndStartActivityForResult(Activity activity, Intent newActivity, int requestCode) {
        checkLoginAndDoRunnable(activity, ()->activity.startActivityForResult(newActivity, requestCode));
    }

    public static void checkLoginAndDoRunnable(Activity activity, Runnable func) {
        if (isUserSignedIn()) {
            try {
                Thread thread = new Thread(func);
                thread.start();
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else
            showToast(activity);
    }

    public static void showToast(Activity activity) {
        activity.runOnUiThread(()-> Toast.makeText(
                activity.getApplicationContext(),
                "You must be signed in first to do this.",
                Toast.LENGTH_SHORT
        ).show());
    }

    public static String getUsersName() {
        IdentityProvider provider = getIdentityProvider();
        String name = null;

        if(provider instanceof GoogleSignInProvider)
            name = getUsersName((GoogleSignInProvider) provider);
        else if(provider instanceof ChowmeinUserPoolsSignInProvider)
            name = getUsersName((ChowmeinUserPoolsSignInProvider) provider);

        return name;
    }

    private static String getUsersName(GoogleSignInProvider provider) {
        return provider == null ? null : provider.getSignedInAccount().getGivenName();
    }

    private static String getUsersName(ChowmeinUserPoolsSignInProvider provider) {
        // Synchronously get the users name from the user pool
        final String[] name = {null};

        Thread cognitoDetails = new Thread(() -> {
            CognitoUser user = provider.getCognitoUserPool().getCurrentUser();

            user.getDetails(new GetDetailsHandler() {
                @Override
                public void onSuccess(CognitoUserDetails cognitoUserDetails) {
                    name[0] = cognitoUserDetails.getAttributes().getAttributes().get("name");
                }

                @Override
                public void onFailure(Exception exception) {
                    exception.printStackTrace();
                }
            });
        });

        try {
            cognitoDetails.start();
            cognitoDetails.join();
        } catch (InterruptedException err) {
            err.printStackTrace();
        }

        return name[0];
    }

    public static String getUsername() {
        IdentityProvider provider = getIdentityProvider();
        String username = null;

        if(provider instanceof GoogleSignInProvider)
            username = getUsername((GoogleSignInProvider) provider);
        else if(provider instanceof ChowmeinUserPoolsSignInProvider)
            username = getUsername((ChowmeinUserPoolsSignInProvider) provider);

        return username;
    }

    private static String getUsername(GoogleSignInProvider provider) {
        return provider == null ? null : "Google_" + provider.getSignedInAccount().getId();
    }

    private static String getUsername(ChowmeinUserPoolsSignInProvider provider) {
        return provider == null ? null : provider.getCognitoUserPool().getCurrentUser().getUserId();
    }

    public static String getUserEmail() {
        IdentityProvider provider = getIdentityProvider();
        String email = null;

        if(provider instanceof GoogleSignInProvider)
            email = getUserEmail((GoogleSignInProvider) provider);
        else if(provider instanceof ChowmeinUserPoolsSignInProvider)
            email = getUserEmail((ChowmeinUserPoolsSignInProvider) provider);

        return email;
    }

    private static String getUserEmail(GoogleSignInProvider provider) {
        return provider == null ? null : provider.getSignedInAccount().getEmail();
    }

    private static String getUserEmail(ChowmeinUserPoolsSignInProvider provider) {
        // Synchronously get the users email from the user pool
        final String[] email = {null};

        Thread cognitoDetails = new Thread(() -> {
            CognitoUser user = provider.getCognitoUserPool().getCurrentUser();

            user.getDetails(new GetDetailsHandler() {
                @Override
                public void onSuccess(CognitoUserDetails cognitoUserDetails) {
                    email[0] = cognitoUserDetails.getAttributes().getAttributes().get("email");
                }

                @Override
                public void onFailure(Exception exception) {
                    exception.printStackTrace();
                }
            });
        });

        try {
            cognitoDetails.start();
            cognitoDetails.join();
        } catch (InterruptedException err) {
            err.printStackTrace();
        }

        return email[0];
    }

    private static IdentityProvider getIdentityProvider() {
        return IdentityManager.getDefaultIdentityManager().getCurrentIdentityProvider();
    }
}
