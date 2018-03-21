package helpers;

import android.widget.TextView;
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

    public static void updateTextViewWithName(TextView view) {
        IdentityProvider provider = getIdentityProvider();
        StringBuilder builder = new StringBuilder();
        builder.append("Howdy, ");

        if(provider instanceof GoogleSignInProvider) {
            builder.append(((GoogleSignInProvider) provider).getSignedInAccount().getGivenName());
            builder.append("!");

            view.setText(builder);
        } else if (provider instanceof ChowmeinUserPoolsSignInProvider) {
            CognitoUser user = ((ChowmeinUserPoolsSignInProvider) provider).getCognitoUserPool().getCurrentUser();

            user.getDetailsInBackground(new GetDetailsHandler() {
                @Override
                public void onSuccess(CognitoUserDetails cognitoUserDetails) {
                    builder.append(cognitoUserDetails.getAttributes().getAttributes().get("name"));

                    builder.append("!");
                    view.setText(builder);
                }

                @Override
                public void onFailure(Exception exception) {
                    exception.printStackTrace();
                }
            });
        }
    }

    private static IdentityProvider getIdentityProvider() {
        return IdentityManager.getDefaultIdentityManager().getCurrentIdentityProvider();
    }
}
