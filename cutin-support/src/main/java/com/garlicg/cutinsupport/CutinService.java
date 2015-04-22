package com.garlicg.cutinsupport;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;

public abstract class CutinService extends Service {



    private View mLayout;
    private WindowManager mWindowManager;
    private boolean mStarted = false;
    private boolean mDestroy = false;
    private CutinEngine mEngine;

    /**
     * Create root view which is inflated to full screen window.
     */
    protected abstract CutinEngine onCreateEngine(Intent intent,long cutinId);

    @Override
    final public IBinder onBind(Intent arg0) {
        return null;
    }


    @Override
    final public int onStartCommand(final Intent intent, final int flags, final int startId) {
        if (!mStarted) {
            mStarted = true;
            mEngine = onCreateEngine(intent ,intent.getLongExtra(CutinManagerUtils.EXTRA_CUTIN_ID, -1));
            if(mEngine == null)return START_NOT_STICKY;
            createLayout();
            // must to be possible to get view size on start method.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(mDestroy)return;
                    mEngine.onStart();
                }
            },10);
        }
        return START_NOT_STICKY;
    }

    private void createLayout(){
        mLayout = mEngine.onCreateLayout(this);

        if (mLayout == null) {
            throw new NullPointerException("You must to define CutinEngine#createLayout and need to return view.");
        }

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN,
                0,
                PixelFormat.TRANSLUCENT);

        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.addView(mLayout, params);
    }


    protected void finishCutin() {
        stopSelf();
    }

    public boolean isDestroyed(){
        return mDestroy;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDestroy = true;
        if(mEngine !=null){
            mEngine.onDestroy();
            mEngine = null;
        }
        if (mWindowManager != null && mLayout != null) {
            mWindowManager.removeView(mLayout);
            mWindowManager = null;
        }
    }

}
