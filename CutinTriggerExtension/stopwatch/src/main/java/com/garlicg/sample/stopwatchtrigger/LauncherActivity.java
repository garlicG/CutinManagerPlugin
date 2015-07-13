package com.garlicg.sample.stopwatchtrigger;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.garlicg.sample.stopwatchtrigger.util.ViewFinder;
import com.garlicg.sample.stopwatchtrigger.widget.LazyTimerView;

public class LauncherActivity extends Activity implements LazyTimerView.OnTimePassListener{

    private LazyTimerView mTimerView;
    private TextView mStartStopView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);

        mTimerView = ViewFinder.id(this ,R.id.Timer);
        mTimerView.setOnTimePassListener(this , new long[]{3000,10000});
        mStartStopView = ViewFinder.id(this , R.id.StartStop);
    }


    //////////////////////
    // Control StopWatch


    /**
     * Click Start or Stop Button
     */
    public void onStartStopClick(View view){
        if(mTimerView.isRunning()){
            mTimerView.pause();
            mStartStopView.setText("Start");
        }
        else {
            mTimerView.start();
            mStartStopView.setText("Stop");
        }
    }

    /**
     * Click Reset Button
     */
    public void onResetClick(View view){
        mTimerView.reset();
        mStartStopView.setText("Start");
    }


    /**
     * Specific time passed
     */
    @Override
    public void onTimePassed(long time) {
    }


    //////////////////////
    // Go to other


    /**
     * onClick by xml
     */
    public void onManagerClick(View view){
        // go to google play
    }

    /**
     * onClick by xml
     */
    public void onLibraryClick(View view){
        // go to github
    }
}
