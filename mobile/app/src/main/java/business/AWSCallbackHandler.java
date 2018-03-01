package business;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;

public class AWSCallbackHandler {

    public boolean checkUser() {
        GenericHandler confirmationCallback = new GenericHandler() {

            @Override
            public void onSuccess() {
                // User was successfully confirmed
            }

            @Override
            public void onFailure(Exception exception) {
                // User confirmation failed. Check exception for the cause.
            }
        };
        return false;
    }
}
