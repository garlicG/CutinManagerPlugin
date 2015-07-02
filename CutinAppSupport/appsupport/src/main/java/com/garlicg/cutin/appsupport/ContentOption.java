package com.garlicg.cutin.appsupport;

import android.content.Intent;

public class ContentOption {

    /**
     * Trigger type
     *
     * @see com.garlicg.cutin.appsupport.ManagerUtils#EXTRA_TRIGGER_TYPE
     */
    public int triggerType = ManagerUtils.TRIGGER_TYPE_DEMO;

    /**
     * TITLE of useful content for CUT-IN appearance from Trigger
     */
    public String contentTitle = null;

    /**
     * MESSAGE of useful content for CUT-IN appearance from Trigger
     */
    public String contentMessage = null;

    private ContentOption(int triggerType, String contentTitle, String contentMessage){
        this.triggerType = triggerType;
        this.contentTitle = contentTitle;
        this.contentMessage = contentMessage;
    }

    public static ContentOption from(Intent intent){
        return new ContentOption(
                intent.getIntExtra(ManagerUtils.EXTRA_TRIGGER_TYPE , ManagerUtils.TRIGGER_TYPE_DEMO),
                intent.getStringExtra(ManagerUtils.EXTRA_CONTENT_TITLE) ,
                intent.getStringExtra(ManagerUtils.EXTRA_CONTENT_MESSAGE));
    }

    public static ContentOption emulate(String contentTitle , String contentMessage , int type){
        return new ContentOption(
                type,
                contentTitle,
                contentMessage);
    }

    public static ContentOption emulate(String contentTitle , String contentMessage){
        return emulate(contentTitle , contentMessage , ManagerUtils.TRIGGER_TYPE_DEMO);
    }

    public static ContentOption emulate(int type){
        return emulate(null , null , type);
    }

}
