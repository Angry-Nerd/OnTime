package com.akshit.ontime;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import com.akshit.ontime.constants.AppConstants;
import com.akshit.ontime.managers.UserManager;
import com.akshit.ontime.util.AppContext;
import com.akshit.ontime.util.FirebaseUtil;
import com.akshit.ontime.util.SharedPreferenceManager;

public class CollegeApplication extends Application {
    private static final String TAG = "CollegeApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseUtil.initFirebase();
        SharedPreferenceManager.getInstance(this);

//        if (SharedPreferenceManager.getServerKey() == null) {
//            FirebaseUtil.getServerKey();
//        }
        //To use it in non activity classes
        AppContext.setContext(this);

        //fetch user details
        UserManager.getInstance().fetchUserDetails();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel(AppConstants.CHANNEL_ID,
                    "College Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    //TODO s
    //1. Change university name
}
