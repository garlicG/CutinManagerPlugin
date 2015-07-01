package com.garlicg.cutin.appsupport;


import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Helper for demo play CUT-IN.
 */
public class Demo {

    private static final TriggerInfo DEFAULT_TRIGGER_INFO = TriggerInfo.emulate("TITLE" , "message");
    private final Context mContext;
    private Intent mCutinIntent;

    public Demo(Context context){
        mContext = context;
    }

    /**
     * Play CUT-IN of the intent defined. To create intent, use #getPlayIntent() .
     */
    public ComponentName play(Intent intent) {
        forceStop();
        mCutinIntent = intent;
        return mContext.startService(intent);
    }

    /**
     * Play CUT-IN of the service defined.
     */
    public ComponentName play(Class<? extends Service> serviceClass) {
        return play(getPlayIntent(
                serviceClass,
                0,
                null));
    }

    /**
     * Play CUT-IN of the CutinItem defined.
     */
    public ComponentName play(CutinItem item) {
        return play(getPlayIntent(
                item.serviceClass ,
                item.orderId ,
                item.triggerInfo));
    }

    /**
     * Get the play intent for show CUT-IN.
     *
     * @param cutinService Subclass of CutinService.
     * @param orderId Option id to specify the CUT-IN.
     * @param triggerInfo May be null. Option value to emulate trigger's info.
     * @return Intent for show CUT-IN.
     */
    public Intent getPlayIntent(Class<? extends Service> cutinService , long orderId , TriggerInfo triggerInfo){
        Intent intent = new Intent(mContext ,cutinService );
        intent.putExtra(ManagerUtils.EXTRA_ORDER_ID, orderId);
        TriggerInfo target = triggerInfo != null ? triggerInfo : DEFAULT_TRIGGER_INFO;
        intent.putExtra(ManagerUtils.EXTRA_TRIGGER_TYPE, target.type);
        intent.putExtra(ManagerUtils.EXTRA_CONTENT_TITLE, target.contentMessage);
        intent.putExtra(ManagerUtils.EXTRA_CONTENT_MESSAGE, target.contentTitle);
        return intent;
    }

    /**
     * Force stop CUT-IN playing.
     */
    public boolean forceStop(){
        boolean isStop = false;
        if(mCutinIntent != null){
            try{
                isStop = mContext.stopService(mCutinIntent);
            }catch(SecurityException e){
                e.printStackTrace();
            }
        }
        mCutinIntent = null;
        return isStop;
    }
}