package com.akshit.ontime;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private Context context;
    private SharedPreferences sharedPreferences;

    PreferencesManager(Context context)
    {
        this.context=context;
        getSharedPreferences();
    }


    private void getSharedPreferences() {
        sharedPreferences=context.getSharedPreferences("Slides",Context.MODE_PRIVATE);
    }
    public void writePreference()
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Yes","null");
        editor.commit();
    }

    boolean checkPreference()
    {
        boolean status=false;
        status = (sharedPreferences.getString("Yes", "")).equals("null");
        return status;
    }
}
