package com.garlicg.cutin.triggerextension;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * The Builder to build the Broadcast Intent of fired Trigger.
 * <p/>
 * <h1>Security</h1>
 * <p/>
 * In pre-ICS, {@link Intent#setPackage(String)} doesn't work in Broadcast.
 * So, can't use explicit Broadcast on those devices.
 * <p/>
 * The Trigger's Intent can contain any text. Third-party application can receive the text on pre-ICS.<br>
 * Therefore you should consider that it is the <strong>minSdkVersion</strong> value to upper <strong>14</strong>.
 *
 * @author Hattsun
 */
public class FireIntentBuilder {

    private TriggerInfo mInfo;

    private long id;

    private String contentTitle;

    private String contentMessage;

    /**
     * @param context {@link Context}
     * @param id      The trigger's ID
     */
    public FireIntentBuilder(Context context, long id) {
        if (context == null) {
            throw new NullPointerException("context must be not null.");
        }

        mInfo = new TriggerInfo(context);
        this.id = id;
    }

    /**
     * Set the content title of this trigger. (Optional)
     *
     * @param contentTitle contentTitle
     * @return this
     */
    public FireIntentBuilder setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
        return this;
    }

    /**
     * Set the content message of this trigger. (Optional)
     *
     * @param contentMessage contentMessage
     * @return this
     */
    public FireIntentBuilder setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
        return this;
    }

    /**
     * Build new {@link Intent}
     *
     * @return Intent
     */
    public Intent intent() {
        Intent intent = new Intent(TriggerConst.ACTION_FIRE);
        intent.addCategory(TriggerConst.CATEGORY_TRIGGER);
        intent.setPackage(TriggerConst.MANAGER_PACKAGE);

        intent.putExtras(mInfo.build());

        intent.putExtra(TriggerConst.EXTRA_TRIGGER_ID, id);
        intent.putExtra(TriggerConst.EXTRA_TRIGGER_CONTENT_TITLE, contentTitle);
        intent.putExtra(TriggerConst.EXTRA_TRIGGER_CONTENT_MESSAGE, contentMessage);

        return intent;
    }

    /**
     * Send Broadcast to the CUT-IN Manager application.
     * if CUT-IN Manager is not installed, it doesn't send a Broadcast.
     *
     * @param context {@link Context}
     * @see #intent()
     */
    public void sendBroadcast(@NonNull Context context) {
        if (TriggerUtil.isManagerInstalled(context)) {
            context.sendBroadcast(intent());
        }
    }
}
