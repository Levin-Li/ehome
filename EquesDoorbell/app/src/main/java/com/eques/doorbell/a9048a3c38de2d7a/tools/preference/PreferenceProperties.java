package com.eques.doorbell.a9048a3c38de2d7a.tools.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 系统参数
 */
public class PreferenceProperties implements Preference {
    private final SharedPreferences preference;
    private final Editor editor;

    public PreferenceProperties(Context context, String name) {
        preference = context.getSharedPreferences(
                name, Context.MODE_PRIVATE);
        editor = preference.edit();
    }

    public String getString(String key, String defaule) {
        return preference.getString(key, defaule);
    }

    public void putString(String key, String value) {
        editor.putString(key, value).commit();
    }

    public int getInt(String key, int defaule) {
        return preference.getInt(key, defaule);
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value).commit();
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value).commit();
    }

    public long getLong(String key, long defaule) {
        return preference.getLong(key, defaule);
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value).commit();
    }

    public float getFloat(String key, float defaule) {
        return preference.getFloat(key, defaule);
    }

    public boolean getBoolean(String key, boolean defaule) {
        return preference.getBoolean(key, defaule);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value).commit();
    }

    public void clearAllCustomGwData() {
        editor.clear().commit();
    }

    public boolean containsKey(String key) {
        return preference.contains(key);
    }
}