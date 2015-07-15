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

    /**
     *
     * @param triggerId Unique value in this app.
     * @param triggerName  Trigger Name.
     * @param contentTitleHint CUT-IN App can handle contentTitleHint as a content ingredient at the time of demo playback. When contentTitleHint is not defined , application label is substituted for it.
     * @param contentMessageHint CUT-IN App can handle contentMessageHint as a content ingredient at the time of demo playback. When contentMessageHint is not defined , TriggerName substituted for it.
     */
    public TriggerObject(long triggerId, String triggerName, @Nullable String contentTitleHint, @Nullable String contentMessageHint) {
        mTriggerId = triggerId;
        mLabel = triggerName;
        mContentTitle = contentTitleHint;
        mContentMessage = contentMessageHint;
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
