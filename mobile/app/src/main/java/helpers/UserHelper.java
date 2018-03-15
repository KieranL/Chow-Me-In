package helpers;

import android.content.Context;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;

public class UserHelper {

    public static CognitoUser getCurrentUser(Context context) {
        CognitoUserPool userPool = new CognitoUserPool(context, new AWSConfiguration(context));
        return userPool.getCurrentUser();
    }

    public static void handleUserDetails(Context context, GetDetailsHandler handler) {
        getCurrentUser(context).getDetailsInBackground(handler);
    }

}
