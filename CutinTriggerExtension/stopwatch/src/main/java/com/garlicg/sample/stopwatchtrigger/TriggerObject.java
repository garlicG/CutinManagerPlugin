package com.garlicg.sample.stopwatchtrigger;

import android.support.annotation.Nullable;

import com.garlicg.cutin.triggerextension.TriggerSetting;

/**
 *
 * @author garlicG
 */
public class TriggerObject implements TriggerSetting{

    public static final long TRIGGER_ID_START = 0;
    public static final long TRIGGER_ID_3S = 1;
    public static final long TRIGGER_ID_10S = 2;

    private final long mTriggerId;
    private final String mLabel;
    private final String mContentTitle;
    private final String mContentMessage;

    public TriggerObject(long triggerId, String label, String contentTitle, String contentMessage) {
        mTriggerId = triggerId;
        mLabel = label;
        mContentTitle = contentTitle;
        mContentMessage = contentMessage;
    }

    @Override
    public long getId() {
        return mTriggerId;
    }

    @Override
    public String getTriggerName() {
        return mLabel;
    }

    @Nullable
    @Override
    public String getContentTitleHint() {
        return mContentTitle;
    }

    @Nullable
    @Override
    public String getContentMessageHint() {
        return mContentMessage;
    }


    @Override
    public String toString() {
        return mLabel;
    }
}
