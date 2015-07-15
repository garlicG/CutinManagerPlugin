package com.garlicg.sample.stopwatchtrigger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

/**
 *
 * @author garlicG
 */
public class MyPreferences {
    private static final String KEY_START = "start";
    private static final String KEY_3S = "3s";
    private static final String KEY_10S = "10s";

    private static HashMap<Long , String> sTriggerKeyMap;
    static {
        sTriggerKeyMap = new HashMap<>(3);
        sTriggerKeyMap.put(TriggerObject.TRIGGER_ID_START ,KEY_START );
        sTriggerKeyMap.put(TriggerObject.TRIGGER_ID_3S ,KEY_3S );
        sTriggerKeyMap.put(TriggerObject.TRIGGER_ID_10S ,KEY_10S );
    }

    private SharedPreferences mPrefs;

    public MyPreferences(Context context){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isEnableTrigger(long triggerId){
        return mPrefs.getBoolean(sTriggerKeyMap.get(triggerId) , false);
    }

    public void setEnableTrigger(long triggerId ,boolean enable){
        mPrefs.edit().putBoolean(sTriggerKeyMap.get(triggerId) , enable).apply();
    }

    public void clearEnableTriggers(){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(KEY_START , false);
        editor.putBoolean(KEY_3S , false);
        editor.putBoolean(KEY_10S , false);
        editor.apply();
    }

}
