package com.garlicg.cutin.appsupport;

import android.content.Intent;
import android.text.TextUtils;

public class TriggerInfo {

    /**
     * TRIGGER_TYPE_DEMO : Demo on CUT-IN App or Unknown trigger
     * TRIGGER_TYPE_EXTENSION : Trigger from Extension Trigger Plugin.
     * TRIGGER_TYPE_OTHER : Basic Trigger of CUT-IN Manager.
     */
    public final static String EXTRA_TRIGGER_TYPE ="trigger_id";

    public final static int TRIGGER_TYPE_EXTENSION = -2;
    public final static int TRIGGER_TYPE_DEMO = -1;
    public final static int TRIGGER_TYPE_SCREEN_ON = 0;
    public static final int TRIGGER_TYPE_SCREEN_UNLOCK = 6;
    public final static int TRIGGER_TYPE_CHARGE_ON = 1;
    public final static int TRIGGER_TYPE_HEAD_SET_ON = 2;
    public final static int TRIGGER_TYPE_DATE_CHANGED = 3;
    public final static int TRIGGER_TYPE_LOW_BATTERY = 4;
    public static final int TRIGGER_TYPE_MOBILE_NETWORK_CONNECTED = 7;
    public static final int TRIGGER_TYPE_WIFI_CONNECTED = 8;
    public static final int TRIGGER_TYPE_BLUETOOTH_ON = 9;
    public static final int TRIGGER_TYPE_GPS_ON = 10;
    public static final int TRIGGER_TYPE_RINGER_MODE_VIBRATE_ON = 11;
    public static final int TRIGGER_TYPE_RINGER_MODE_SILENT_ON = 12;
    public static final int TRIGGER_TYPE_AIRPLANE_MODE_ON = 13;
    public static final int TRIGGER_TYPE_PACKAGE_ADDED = 14;
    public static final int TRIGGER_TYPE_PACKAGE_REMOVED = 15;
    public final static int TRIGGER_TYPE_NOTIFICATION = 5;

    public static final int DEFAULT_TRIGGER_TYPE = TRIGGER_TYPE_DEMO;

    /**
     * Trigger type
     */
    public int type = DEFAULT_TRIGGER_TYPE;


    /**
     * TEXT of useful content for CUT-IN appearance from Trigger. it may be English.
     */
    public static final String EXTRA_CONTENT_TITLE ="com.garlicg.cutin.extra.CONTENT_TITLE";

    /**
     * TITLE of useful content for CUT-IN appearance from Trigger
     */
    public String contentTitle = null;


    /**
     * TEXT of useful content for CUT-IN appearance from Trigger. it may be English.
     */
    public static final String EXTRA_CONTENT_MESSAGE ="com.garlicg.cutin.extra.CONTENT_MESSAGE";

    /**
     * MESSAGE of useful content for CUT-IN appearance from Trigger
     */
    public String contentMessage = null;


    private TriggerInfo(int type, String contentTitle, String contentMessage){
        this.type = type;
        this.contentTitle = contentTitle;
        this.contentMessage = contentMessage;
    }

    public static TriggerInfo from(Intent intent , String defaultTitle ,String defaultMessage){
        TriggerInfo info = new TriggerInfo(
                intent.getIntExtra(EXTRA_TRIGGER_TYPE , TRIGGER_TYPE_DEMO),
                intent.getStringExtra(EXTRA_CONTENT_TITLE) ,
                intent.getStringExtra(EXTRA_CONTENT_MESSAGE));
        if(TextUtils.isEmpty(info.contentTitle))info.contentTitle = defaultTitle;
        if(TextUtils.isEmpty(info.contentMessage))info.contentMessage = defaultMessage;
        return info;
    }

    public static TriggerInfo from(Intent intent){
        return from(intent , "TITLE" ,"Message");
    }

    public static TriggerInfo emulate(String contentTitle , String contentMessage , int type){
        return new TriggerInfo(
                type,
                contentTitle,
                contentMessage);
    }

    public static TriggerInfo emulate(String contentTitle , String contentMessage){
        return emulate(contentTitle , contentMessage , TRIGGER_TYPE_DEMO);
    }

}
