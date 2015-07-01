package com.garlicg.cutinsupport;

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

    protected Context getContext(){
        return mCutinService;
    }

    protected void finishCutin(){
        if(mCutinService.isDestroyed())return;
        mCutinService.stopSelf();
    }

    protected boolean isDestroyed(){
        return mCutinService.isDestroyed();
    }
}
