package com.akshit.ontime.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.akshit.ontime.constants.AppConstants;
import com.akshit.ontime.constants.SharedPrefConstants;
import com.akshit.ontime.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SharedPreferenceManager {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static Gson gson;

    private SharedPreferenceManager() {
    }

    public static synchronized void getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("College", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            gson = new GsonBuilder().setPrettyPrinting().create();
        }
    }

    public static boolean isWelcomeScreenShown() {
        return sharedPreferences.getBoolean(AppConstants.IS_WELCOME_SCREEN_SHOWN, false);
    }

    public static void setWelcomeScreenShown(boolean isShown) {
        editor.putBoolean(AppConstants.IS_WELCOME_SCREEN_SHOWN, isShown);
        editor.apply();
    }

    public static void setLoggedInEmail(String id) {
        editor.putString(AppConstants.USER_ID, id);
        editor.apply();
    }

    public static String getLoggedEmail() {
        return sharedPreferences.getString(AppConstants.USER_ID, null);

    }

    public static String getUniversityName() {
        return sharedPreferences.getString(AppConstants.UNIVERSITY_NAME, null);

    }

//    public static String getDepartment() {
////        return sharedPreferences.getString(DbConstants.DEPARTMENT, null);
////    }

//    public static void setDepartment(String department) {
//        editor.putString(DbConstants.DEPARTMENT, department);
//        editor.apply();
//    }
//
    public static void clearAll() {
        setLoggedInEmail(null);
        setUniversityName(null);
//        setDepartment(null);
//        setUserToken(null);
    }
//
//    public static String getUserToken() {
//        return sharedPreferences.getString(SharedPrefConstants.TOKEN, null);
//    }
//
//    public static void setUserToken(String token) {
//        editor.putString(SharedPrefConstants.TOKEN, token);
//        editor.apply();
//    }
//
//    public static String getServerKey() {
//        return sharedPreferences.getString(DbConstants.SERVER_KEY, null);
//    }
//
//    public static void setServerKey(String key) {
//        editor.putString(DbConstants.SERVER_KEY, key);
//        editor.apply();
//    }
//
//    public static void setUpdateRequired(boolean b) {
//        editor.putBoolean(AppConstants.UPDATE_REQUIRED, b);
//        editor.apply();
//    }
//
    public static User getUser() {
        return AppUtils.fromJSON(sharedPreferences.getString(SharedPrefConstants.LOGGED_USER, null), User.class);
    }

    public static void setUser(final User user) {
        editor.putString(SharedPrefConstants.LOGGED_USER, AppUtils.toJSON(user));
        editor.apply();
    }

    public static void setUniversityName(final String universityName) {
        editor.putString(AppConstants.UNIVERSITY_NAME, universityName);
        editor.apply();
    }

    public static void setLastTimeSemesterSynced(final long currentTimeMillis) {
        editor.putLong(AppConstants.LAST_TIME_SEMESTERS_SYNCED, currentTimeMillis);
        editor.apply();
    }

    public static long getLastTimeSynced() {
        return sharedPreferences.getLong(AppConstants.LAST_TIME_SEMESTERS_SYNCED, 0);
    }
}
