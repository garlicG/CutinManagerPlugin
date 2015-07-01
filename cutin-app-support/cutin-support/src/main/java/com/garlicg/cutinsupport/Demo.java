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
     */
    public ComponentName play(Intent intent) {
        forceStop();
        mCutinIntent = intent;
        return mContext.startService(intent);
    }

    public ComponentName play(CutinItem item) {
        return play(getPlayIntent(item));
    }

    public ComponentName play(Class<? extends Service> serviceClass ,int triggerId){
        return play(serviceClass , triggerId);
    }

    public ComponentName play(Class<? extends Service> serviceClass , long orderId , int triggerId) {
        return play(getPlayIntent(serviceClass , orderId , triggerId));
    }

    public ComponentName play(Class<? extends Service> serviceClass , long orderId ,String notifyPackageName ,String ticker){
        Intent intent = getPlayIntent(serviceClass , orderId ,CutinManagerUtils.TRIGGER_ID_NOTIFICATION);
        intent.putExtra(CutinManagerUtils.EXTRA_NOTIFICATION_PACKAGE_NAME,notifyPackageName);
        intent.putExtra(CutinManagerUtils.EXTRA_NOTIFICATION_TICKER,ticker);
        return play(intent);
    }

    public Intent getPlayIntent(Class<? extends Service> cutinService , long orderId){
        return getPlayIntent(cutinService , orderId , CutinManagerUtils.TRIGGER_ID_DEMO);
    }

    public Intent getPlayIntent(CutinItem item){
        return getPlayIntent(item.serviceClass , item.orderId, item.triggerId);
    }

    public Intent getPlayIntent(Class<? extends Service> cutinService , long orderId , int triggerId){
        Intent intent = new Intent(mContext ,cutinService );
        intent.putExtra(CutinManagerUtils.EXTRA_ORDER_ID, orderId);
        intent.putExtra(CutinManagerUtils.EXTRA_TRIGGER_ID , triggerId);
        return intent;
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


    @Deprecated
    public ComponentName play(Class<? extends Service> serviceClass) {
        Intent intent = new Intent(mContext , serviceClass);
        return play(intent);
    }
}