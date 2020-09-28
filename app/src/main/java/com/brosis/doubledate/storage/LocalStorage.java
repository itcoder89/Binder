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
    public static void setPersonalCode(Context context, String key) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("setPersonalCode", key);
        prefsEditor.commit();
    }

    public static String getPersonalCode(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("setPersonalCode", "");
    }
    public static void setPartnerCode(Context context, String key) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("setPartnerCode", key);
        prefsEditor.commit();
    }

    public static String getPartnerCode(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("setPartnerCode", "");
    }

    public static void setLaunchActivity(Context context, String key) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("setLaunchActivity", key);
        prefsEditor.commit();
    }

    public static String getLaunchActivity(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("setLaunchActivity", "false");
    }
}
