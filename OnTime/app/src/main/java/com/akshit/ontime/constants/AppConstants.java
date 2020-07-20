package com.akshit.ontime.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Class level constants.
 */
public class AppConstants {

    public static final String APP_PREFIX = "College";

    /**
     * Is welcome screen shown.
     */
    public static final String IS_WELCOME_SCREEN_SHOWN = "IS_WELCOME_SCREEN_SHOWN";

    /**
     * Variable to store the user id.
     */
    public static final String USER_ID = "USER_ID";


    /**
     * Time for the splash screen.
     */
    public static final long SPLASH_TIME = 1000;

    /**
     * Name of the university with which user logged in.
     */
    public static final String UNIVERSITY_NAME = "UNIVERSITY_NAME";

    /**
     * End point for the fcm.
     */
    public static final String FCM_END_POINT = "https://fcm.googleapis.com/fcm/";

    /**
     * Key for sending a chat message type.
     */
    public static final String CHAT_MESSAGE = "CHAT_MESSAGE";

    /**
     * Is update required for the app.
     */
    public static final String UPDATE_REQUIRED = "update_required";

    /**
     * Channel for the notification.
     */
    public static final String CHANNEL_ID = "CHANNEL";

    /**
     * Date time format for the app.
     */
    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";

    /**
     * Receiver when connectivity change.
     */
    public static final String CONNECTIVITY_CHANGE_LISTENER = "android.net.conn.CONNECTIVITY_CHANGE";

    /**
     * Receiver when wifi connectivity change.
     */
    public static final String WIFI_STATE_CHANGE_LISTENER = "android.net.wifi.WIFI_STATE_CHANGED";

    /**
     * Type of the notification.
     */
    public static final String NOTIFICATION_TYPE = "notification_type";

    /**
     * Type of the notifications.
     */
    public static final Map<String, Integer> NOTIFICATION_TYPES = new HashMap<>();


    public static final int GOOGLE_SIGN_IN_REQUEST_CODE = 0x2020;

    /**
     * Time stamp when semesters data last time synced.
     */
    public static final String LAST_TIME_SEMESTERS_SYNCED = "LAST_TIME_SEMESTERS_SYNCED";
    public static final long ONE_WEEK = 1000 * 60 * 60 * 24 * 7;
    public static final String USER_APPLICATION_STATUS = "USER_APPLICATION_STATUS";

    static {
//        NOTIFICATION_TYPES.put(NotificationConstants.ANNOUNCEMENT, 1001);
//        NOTIFICATION_TYPES.put(NotificationConstants.CLUB_EVENT, 1002);
    }

    /**
     * Private Constructor.
     */
    private AppConstants() {
    }


    /**
     * States of the TT.
     */
    public static enum STATES {
        ASSIGNED,
        RESEARCHING,
        WORK_IN_PROGRESS,
        RESOLVED
    }
}
