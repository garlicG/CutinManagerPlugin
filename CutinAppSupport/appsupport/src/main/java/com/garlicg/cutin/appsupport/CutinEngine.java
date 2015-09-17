package com.garlicg.cutin.appsupport;

import android.content.Context;
import android.view.View;

public abstract class CutinEngine {

    public abstract View onCreateLayout(Context context);
    public abstract void onStart();
    public void onDestroy(){}

    protected CutinService mCutinService;

    public CutinEngine(CutinService cutinService){
        mCutinService = cutinService;
    }

    public Context getContext(){
        return mCutinService;
    }

    public void finishCutin(){
        if(mCutinService.isDestroyed())return;
        mCutinService.stopSelf();
    }

    public boolean isDestroyed(){
        return mCutinService.isDestroyed();
    }
}
