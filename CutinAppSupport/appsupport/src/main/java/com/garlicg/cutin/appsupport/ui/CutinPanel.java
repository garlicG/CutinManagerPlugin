package com.garlicg.cutin.appsupport.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.garlicg.cutin.appsupport.CutinItem;
import com.garlicg.cutin.appsupport.ManagerUtils;
import com.garlicg.cutin.appsupport.Demo;
import com.garlicg.cutin.appsupport.R;

/**
 * Base Panel Activity
 * This activity will be transparent with dark or light theme depends on state from intent,
 */
public abstract class CutinPanel extends FragmentActivity {

    private boolean mCalledFromManager;
    private int mTriggerId;

    private Demo mDemo;
    protected Demo getDemo(){
        return mDemo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set theme of cutin manager
        Intent intent = getIntent();
        int cmTheme = intent.getIntExtra(ManagerUtils.EXTRA_THEME_UI , ManagerUtils.THEME_UI_LIGHT);
        setTheme(resolveDialogThemeResource(cmTheme));

        // trigger id from cutin manager
        mTriggerId = getIntent().getIntExtra(ManagerUtils.EXTRA_TRIGGER_TYPE, ManagerUtils.TRIGGER_TYPE_DEMO);

        // flag of called from manager?
        mCalledFromManager = ManagerUtils.isCalledFromManager(intent);

        mDemo = new Demo(this);

    }

    protected void resizeWindow(){
        int orientation = getResources().getConfiguration().orientation;
        int a = orientation == Configuration.ORIENTATION_LANDSCAPE ? 70:90;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        lp.width = metrics.widthPixels * a / 100;
        getWindow().setAttributes(lp);
    }

    protected int resolveDialogThemeResource(int cmTheme){
        if(cmTheme == ManagerUtils.THEME_UI_DARK){
            return R.style.CutinPanel_Dark;
        }else {
            return R.style.CutinPanel_Light;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDemo.forceStop();
    }

    /////////////////
    // methods

    public boolean isPickable(){
        return mCalledFromManager;
    }

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
            new LaunchManagerDialog().showSingly(fm);
        }
        // show go to Market dialog
        else {
            new DownloadManagerDialog().showSingly(fm);
        }
    }

    public int getTriggerId(){
        return mTriggerId;
    }


    //////////////////
    // dialogs


    public static class DownloadManagerDialog extends SimpleAlert implements DialogInterface.OnClickListener{
        @Override
        protected String getDialogTag() {
            return "DownloadManagerDialog";
        }

        @Override
        protected String getMessage() {
            return getString(R.string.cp_message_need_manager);
        }

        @Override
        protected String getButton() {
            return getString(R.string.cp_download);
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent = ManagerUtils.buildMarketIntent();
            startActivity(intent);
            getActivity().finish();
        }
    }


    public static class LaunchManagerDialog extends SimpleAlert implements DialogInterface.OnClickListener{

        @Override
        protected String getDialogTag() {
            return "LaunchManagerDialog";
        }

        @Override
        protected String getMessage() {
            return getString(R.string.cp_message_launch_manager);
        }

        @Override
        protected String getButton() {
            return getString(R.string.cp_launch);
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent startManager = ManagerUtils.buildLauncherIntent(getActivity());
            startActivity(startManager);
            getActivity().finish();
        }
    }



    public static abstract class SimpleAlert extends DialogFragment implements DialogInterface.OnClickListener{

        public void showSingly(FragmentManager manager) {
            final String tag = getDialogTag();
            if(manager.findFragmentByTag(tag) != null)return;
            super.show(manager, tag);
        }

        protected abstract String getDialogTag();
        protected abstract String getMessage();
        protected abstract String getButton();

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getMessage());
            builder.setPositiveButton(getButton(), this);
            return builder.create();
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {}
    }



    /**
     * Use handleOKClick instead of this.
     */
    @Deprecated
    public void onOKClick(@Nullable CutinItem item) {}
}
