package com.garlicg.sample.stopwatchtrigger;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.garlicg.cutin.triggerextension.FireIntentBuilder;
import com.garlicg.cutin.triggerextension.TriggerConst;
import com.garlicg.cutin.triggerextension.TriggerUtil;
import com.garlicg.sample.stopwatchtrigger.util.ViewFinder;
import com.garlicg.sample.stopwatchtrigger.widget.LazyTimerView;

/**
 * Launcher Activity
 *
 * Main feature is Stop Watch. It is possible to send registered trigger to the CUT-IN Manager.
 *
 * @author garlicG
 */
public class LauncherActivity extends Activity implements LazyTimerView.OnTimePassListener{
    private static final String TAG = LauncherActivity.class.getName();

    private MyPreferences mPreferences;

    private LazyTimerView mTimerView;
    private TextView mStartStopView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreferences = new MyPreferences(this);

        setContentView(R.layout.activity_launcher);
        mTimerView = ViewFinder.id(this ,R.id.Timer);
        mStartStopView = ViewFinder.id(this , R.id.StartStop);
        mTimerView.setOnTimePassListener(this , new long[]{3000L, 10000L});
    }



    //////////////////////
    // With stopWatch


    public void onStartStopClick(View view){
        // From running state to pause state.
        if(mTimerView.isRunning()){
            mTimerView.pause();
            mStartStopView.setText("Start");
        }
        // To running state.
        else {
            boolean isInitialStart = mTimerView.start();
            mStartStopView.setText("Stop");

            if(isInitialStart){
                // Send starting trigger to the CUT-IN Manager
                sendTriggerIfEnable(TriggerObject.TRIGGER_ID_START, "START!");
            }
        }
    }

    public void onResetClick(View view){
        mTimerView.reset();
        mStartStopView.setText("Start");
    }


    @Override
    public void onTimePassed(long time) {
        if(time == 3000){
            // Send 3ms trigger to the CUT-IN Manager
            sendTriggerIfEnable(TriggerObject.TRIGGER_ID_3S, "3Sec!");
        }
        else if(time == 10000){
            // Send 10ms trigger to the CUT-IN Manager
            sendTriggerIfEnable(TriggerObject.TRIGGER_ID_10S, "10Sec!");
        }
    }


    /**
     * Send specific trigger to the CUT-IN Manager if enable.
     */
    private void sendTriggerIfEnable(long triggerId, String contentTitle){
        // ignore case
        if(!mPreferences.isEnableTrigger(triggerId)) {
            Log.i(TAG , "trigger:" + Long.toString(triggerId) + " is disable");
            return;
        }

        FireIntentBuilder builder = new FireIntentBuilder(this, triggerId);
        builder.setContentTitle(contentTitle);
        builder.setContentMessage("Message from Stopwatch");
        builder.sendBroadcast(this);

        Log.i(TAG, "send trigger:" + Long.toString(triggerId));
    }



    //////////////////////
    // Go to other

    /**
     * Launch CUT-IN Manager or Go to Market page.
     */
    public void onManagerClick(View view){

        Intent intent;
        if(TriggerUtil.isManagerInstalled(this)){
            intent = TriggerUtil.getManagerLaunchIntent(this);
        }
        else{
            intent = TriggerUtil.getManagerMarketIntent();
        }
        startActivity(intent);
    }

    /**
     * Go to github page.
     */
    public void onLibraryClick(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("https://github.com/garlicG/CutinManagerPlugin/tree/master/CutinTriggerExtension"));
        startActivity(intent);
    }


}
