package helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkHelper {

    public static boolean networkConnectionAvailable(Activity activity) {
        ConnectivityManager connectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectionManager != null;
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void checkConnectionAndNotify(Activity activity) {
        if (!networkConnectionAvailable(activity))
            Toast.makeText(activity.getApplicationContext(), "You are not connected to the Internet. To send and receive Chows please connect to the Internet", Toast.LENGTH_SHORT).show();
    }

    public static void checkConnectionAndStartActivity(Activity activity, Intent newActivity) {
        checkConnectionAndNotify(activity);
        if (networkConnectionAvailable(activity))
            activity.startActivity(newActivity);
    }
}
