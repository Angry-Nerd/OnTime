package com.akshit.ontime.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.akshit.ontime.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AppUtils {

    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;
    public static final int NETWORK_STATUS_NOT_CONNECTED = 0;
    public static final int NETWORK_STATUS_WIFI = 1;
    public static final int NETWORK_STATUS_MOBILE = 2;

    public static boolean checkInternetConnectivity(final Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        }
        return false;

    }

    /**
     * Convert a object to string form.
     *
     * @param object to be converted
     * @return the JSON string
     */
    public static String toJSON(final Object object) {
        return gson.toJson(object);
    }

    /**
     * Convert string form of an object in Object.
     *
     * @param json  string
     * @param clazz type to be converted in
     * @param <T>   the generic type
     * @return the object
     */
    public static <T> T fromJSON(final String json, final Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static int getConnectivityStatus(final Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static int getConnectivityStatusString(final Context context) {
        final int conn = getConnectivityStatus(context);
        int status = 0;
        if (conn == TYPE_WIFI) {
            status = NETWORK_STATUS_WIFI;
        } else if (conn == TYPE_MOBILE) {
            status = NETWORK_STATUS_MOBILE;
        }
        return status;
    }

    public static boolean isNull(final Object object) {
        return object == null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setTopBar(final Activity activity) {
        final Window window = activity.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
    }

    public static void disableTouch(final Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void enableTouch(final Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static boolean checkPermissions(final Activity activity, final int requestCode, final String... permissions) {
        boolean isAllGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity,
                    permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        requestCode);
                isAllGranted = false;
            }
        }
        return isAllGranted;
    }

    public static void showNoInternetConnectivityDialog(final Context ctx, final DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("Internet connection not available.");
        builder.setTitle("No connectivity");
        builder.setCancelable(false);
        builder.setPositiveButton("Retry", onClickListener);
        builder.show();
    }
}
