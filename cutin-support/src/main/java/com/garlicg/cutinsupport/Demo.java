package com.garlicg.cutinsupport;


import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Call cutinservice.
 */
public class Demo {
    private Intent mCutinIntent;
    private Context mContext;
    public Demo(Context context){
        mContext = context;
    }

    /**
     * Play cutin service. If there are playing cutin , stop it and new cutin service start.
     * @param intent
     * @return
     */
    public ComponentName play(Intent intent) {
        forceStop();
        mCutinIntent = intent;
        return mContext.startService(intent);
    }

    public ComponentName play(Class<? extends Service> serviceClass) {
        Intent intent = new Intent(mContext , serviceClass);
        return play(intent);
    }

    public ComponentName play(CutinItem item) {
        return play(item.serviceClass, item.cutinId , item.triggerId);
    }

    public ComponentName play(Class<? extends Service> serviceClass , long cutinId , int triggerId) {
        Intent intent = new Intent(mContext,serviceClass);
        intent.putExtra(CutinManagerUtils.EXTRA_CUTIN_ID, cutinId);
        intent.putExtra(CutinManagerUtils.EXTRA_TRIGGER_ID , triggerId);
        return play(intent);
    }

    public ComponentName play(Class<? extends Service> serviceClass ,int triggerId){
        Intent intent = new Intent(mContext,serviceClass);
        intent.putExtra(CutinManagerUtils.EXTRA_TRIGGER_ID, triggerId);
        return play(intent);
    }

    public ComponentName play(Class<? extends Service> serviceClass , String notifyPackageName ,String ticker){
        Intent intent = new Intent(mContext,serviceClass);
        intent.putExtra(CutinManagerUtils.EXTRA_TRIGGER_ID, CutinManagerUtils.TRIGGER_ID_NOTIFICATION);
        intent.putExtra(CutinManagerUtils.EXTRA_NOTIFICATION_PACKAGE_NAME,notifyPackageName);
        intent.putExtra(CutinManagerUtils.EXTRA_NOTIFICATION_TICKER,ticker);
        return play(intent);
    }

    /**
     * Stop cut-in playing,
     */
    public boolean forceStop(){
        boolean isStop = false;
        if(mCutinIntent != null){
            try{
                isStop = mContext.stopService(mCutinIntent);
            }catch(SecurityException e){
            }
        }
        mCutinIntent = null;
        return isStop;
    }
}