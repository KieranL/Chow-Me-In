package helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSettings;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.chowpals.chowmein.Application;
import com.chowpals.chowmein.R;

import org.json.JSONObject;

import java.util.Map;

public class UserHelper {

    public static CognitoUser getCurrentUser(Context context) {
        CognitoUserPool userPool = new CognitoUserPool(context, new AWSConfiguration(context));
        return userPool.getCurrentUser();
    }

    public static void handleUserDetails(Context context, GetDetailsHandler handler) {
        getCurrentUser(context).getDetailsInBackground(handler);
    }

    public static boolean isUserSignedIn(Context context) {
        CognitoUser user = getCurrentUser(context);
        JSONObject settings = new AWSConfiguration(context).optJsonObject("CognitoUserPool");
        boolean signedIn = false;

        try {
            // Apparently the cognito sdk has "isSignedIn" method, found a workaround https://github.com/aws/aws-sdk-android/issues/260
            String appClientId = settings.getString("AppClientId");
            SharedPreferences prefs = context.getSharedPreferences("CognitoIdentityProviderCache", 0);
            String csiIdTokenKey = "CognitoIdentityProvider." + appClientId + "." + user.getUserId() + ".idToken";
            Map result = prefs.getAll();

            signedIn = result.containsKey(csiIdTokenKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return signedIn;
    }

}
