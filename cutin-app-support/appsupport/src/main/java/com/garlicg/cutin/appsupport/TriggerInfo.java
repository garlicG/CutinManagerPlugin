package com.garlicg.cutin.appsupport;

import android.content.Intent;

public class TriggerInfo {

    /**
     * Trigger type
     *
     * @see com.garlicg.cutin.appsupport.ManagerUtils#EXTRA_TRIGGER_TYPE
     */
    public final int type;

    /**
     * TITLE of useful content for CUT-IN appearance from Trigger
     */
    public final String contentTitle;

    /**
     * MESSAGE of useful content for CUT-IN appearance from Trigger
     */
    public final String contentMessage;

    private TriggerInfo(int triggerType , String contentTitle, String contentMessage){
        this.type = triggerType;
        this.contentTitle = contentTitle;
        this.contentMessage = contentMessage;
    }

    public static TriggerInfo from(Intent intent){
        return new TriggerInfo(
                intent.getIntExtra(ManagerUtils.EXTRA_TRIGGER_TYPE , ManagerUtils.TRIGGER_TYPE_DEMO),
                intent.getStringExtra(ManagerUtils.EXTRA_CONTENT_TITLE) ,
                intent.getStringExtra(ManagerUtils.EXTRA_CONTENT_MESSAGE));
    }

    public static TriggerInfo emulate(String contentTitle , String contentMessage , int type){
        return new TriggerInfo(
                type,
                contentTitle,
                contentMessage);
    }

    public static TriggerInfo emulate(String contentTitle , String contentMessage){
        return emulate(contentTitle , contentMessage , ManagerUtils.TRIGGER_TYPE_DEMO);
    }

    public static TriggerInfo emulate(int type){
        return emulate("" , "" , type);
    }

}
