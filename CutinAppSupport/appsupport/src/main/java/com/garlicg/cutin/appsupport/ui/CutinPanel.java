package com.garlicg.cutin.appsupport.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.garlicg.cutin.appsupport.CutinItem;
import com.garlicg.cutin.appsupport.Demo;
import com.garlicg.cutin.appsupport.ManagerUtils;
import com.garlicg.cutin.appsupport.R;

/**
 * Base Panel Activity
 * This activity will be transparent with dark or light theme depends on state from intent,
 */
public abstract class CutinPanel extends FragmentActivity {

    private Demo mDemo;
    protected Demo getDemo(){
        return mDemo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectExtras(getIntent());
        mDemo = new Demo(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDemo.forceStop();
    }

    //////////////
    // extras

    private boolean mCalledFromManager;
    private int mCutinManagerTheme;

    private void injectExtras(Intent intent){

        // flag of called from manager?
        mCalledFromManager = ManagerUtils.isCalledFromManager(intent);

        mCutinManagerTheme = intent.getIntExtra(ManagerUtils.EXTRA_THEME_UI , ManagerUtils.THEME_UI_LIGHT);
    }


    protected int getCmTheme(){
        return mCutinManagerTheme;
    }

    public boolean isPickable(){
        return mCalledFromManager;
    }

    protected int resolvePanelThemeResource(int cmTheme){
        return cmTheme == ManagerUtils.THEME_UI_DARK ? R.style.CutinPanel_Dark : R.style.CutinPanel_Light;
    }

    //////////////
    // methods

    public void handleOKClick(@Nullable CutinItem item){
        // called from manager
        if(isPickable()){
            finishWithCutinResult(item);
        }
        // called from other (launcher etc...)
        else{
            showCutinManager();
        }
    }

    /**
     * Return cutin information to CutinManager.
     */
    public void finishWithCutinResult(CutinItem item){
        if(item == null)return;
        Intent intent = ManagerUtils.buildResultIntent(item);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    /**
     * Show CutinManager or go to market page of CutinManager.
     */
    public void showCutinManager(){
        FragmentManager fm = getSupportFragmentManager();
        // existManager
        if(ManagerUtils.existManager(this)){
            new SimpleCutinDialogs.LaunchAlert().showSingly(fm);
        }
        // show go to Market dialog
        else {
            new SimpleCutinDialogs.DownloadAlert().showSingly(fm);
        }
    }


    /**
     * Use handleOKClick instead of this.
     */
    @Deprecated
    public void onOKClick(@Nullable CutinItem item) {}
}
