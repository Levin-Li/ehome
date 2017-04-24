package com.eques.doorbell.a9048a3c38de2d7a.tools.preference;

public interface Preference {

    public String getString(String key, String defaultValue);

    public void putString(String key, String value);

    public int getInt(String key, int defaultValue);

    public void putInt(String key, int value);

    public void putLong(String key, long value);

    public long getLong(String key, long defaultValue);

    public void putFloat(String key, float value);

    public float getFloat(String key, float defaultValue);

    public boolean getBoolean(String key, boolean defaultValue);

    public void putBoolean(String key, boolean value);

    public void clearAllCustomGwData();

    public boolean containsKey(String key);
}