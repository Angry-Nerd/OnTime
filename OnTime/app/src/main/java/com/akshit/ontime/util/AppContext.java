package com.akshit.ontime.util;

import android.content.Context;

/**
 * Class to hold app's context.
 */
public class AppContext {

    private static Context mContext = null;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(final Context context) {
        mContext = context;
    }
}
