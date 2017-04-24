package com.eques.doorbell.a9048a3c38de2d7a.tools.preference;

import android.content.Context;


import com.eques.doorbell.a9048a3c38de2d7a.tools.logger.Logger;
import com.eques.doorbell.a9048a3c38de2d7a.tools.logger.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/22.
 */
public class PreferenceManager {
    public static final String PREFERENCE_GLOBAL = "global";
    public static final String PREFERENCE_CACHE = "cache";
    private static PreferenceManager instance = new PreferenceManager();
    private Map<String, Preference> preferenceMap = new HashMap<String, Preference>();
    private Logger logger = LoggerFactory.getLogger(PreferenceManager.class);
    private Context context;

    private PreferenceManager() {
        logger.debug("PreferenceManager created");
    }

    public static PreferenceManager getInstance() {
        return instance;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void checkPreference(String preferenceID) {
        if (!preferenceMap.containsKey(preferenceID)) {
            preferenceMap.put(preferenceID, new PreferenceProperties(context, preferenceID));
        }
    }

    public Preference getGlobalPreference() {
        checkPreference(PREFERENCE_GLOBAL);
        return preferenceMap.get(PREFERENCE_GLOBAL);
    }

    public void setGlobalPreference(Preference preference) {
        preferenceMap.put(PREFERENCE_GLOBAL, preference);
    }

    public Preference getCachePreference() {
        checkPreference(PREFERENCE_CACHE);
        return preferenceMap.get(PREFERENCE_CACHE);
    }

    public Preference setCachePreference(Preference preference) {
        return preferenceMap.put(PREFERENCE_CACHE, preference);
    }

    public Preference getCustomPreference(String preferenceID) {
        checkPreference(preferenceID);
        return preferenceMap.get(preferenceID);
    }

    public Preference setCustomPreference(String preferenceID, Preference customPreference) {
        return preferenceMap.put(preferenceID, customPreference);
    }
}
