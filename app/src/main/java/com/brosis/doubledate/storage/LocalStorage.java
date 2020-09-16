package com.brosis.doubledate.storage;

import android.content.Context;
import android.preference.PreferenceManager;

public class LocalStorage {

    public static void setIsFragmentOpen(Context context, String key) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("setIsFragmentOpen", key);
        prefsEditor.commit();
    }

    public static String getIsFragmentOpen(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("setIsFragmentOpen", "false");
    }
}
