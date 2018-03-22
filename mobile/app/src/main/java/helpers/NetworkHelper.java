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
            showToast(activity);
    }

    public static void showToast(Activity activity) {
        activity.runOnUiThread(()-> Toast.makeText(
                activity.getApplicationContext(),
                "You are not connected to the Internet. To send and receive Chows please connect to the Internet",
                Toast.LENGTH_SHORT
        ).show());
    }

    public static void checkConnectionAndStartActivity(Activity activity, Intent newActivity) {
        if (networkConnectionAvailable(activity))
            activity.startActivity(newActivity);
        else
            showToast(activity);
    }

    public static void checkConnectionAndDoRunnable(Activity activity, Runnable func) {
        if (networkConnectionAvailable(activity)) {
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
}
