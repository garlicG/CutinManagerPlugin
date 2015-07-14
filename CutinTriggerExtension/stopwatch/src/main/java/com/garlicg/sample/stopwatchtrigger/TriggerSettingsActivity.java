package com.garlicg.sample.stopwatchtrigger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.garlicg.cutin.triggerextension.ResultBundleBuilder;
import com.garlicg.cutin.triggerextension.TriggerSetting;

import java.util.ArrayList;

/**
 * This Activity be called from CUT-IN Manager.
 *
 * Declare belows as Trigger Settings
 *
 * 1. Create chooser of all triggers.
 * 2. Return enable triggers.
 *
 * @author garlicG
 */
public class TriggerSettingsActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private MyPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreferences = new MyPreferences(this);

        // Create all trigger list this app has.
        ArrayList<TriggerSetting> list = new ArrayList<>();
        String contentMessageHint = "Hint message from Stop Watch.";
        list.add(new TriggerObject(TriggerObject.TRIGGER_ID_START, "Time Start" , "START!" , contentMessageHint));
        list.add(new TriggerObject(TriggerObject.TRIGGER_ID_3S, "3 Second", "3Sec!", contentMessageHint));
        list.add(new TriggerObject(TriggerObject.TRIGGER_ID_10S, "10 Second" , "10Sec!" , contentMessageHint));

        // Create listView
        mListView = new ListView(this);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mListView.setOnItemClickListener(this);
        setContentView(mListView);
        ArrayAdapter<TriggerSetting> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, list);
        mListView.setAdapter(adapter);

        // Set current enable state
        mListView.setItemChecked(0, mPreferences.isEnableTrigger(TriggerObject.TRIGGER_ID_START));
        mListView.setItemChecked(1, mPreferences.isEnableTrigger(TriggerObject.TRIGGER_ID_3S));
        mListView.setItemChecked(2, mPreferences.isEnableTrigger(TriggerObject.TRIGGER_ID_10S));

    }


    /**
     * Update preference When check state changed ,
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TriggerObject to = (TriggerObject)adapterView.getItemAtPosition(i);
        mPreferences.setEnableTrigger(to.getId(), mListView.isItemChecked(i));
    }


    /**
     * Return enable triggers.
     */
    @Override
    public void onBackPressed() {
        ArrayList<TriggerSetting> enableTriggers = getEnableTriggers();
        Intent resultIntent = newResultIntent(enableTriggers);
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    /**
     * Get enable triggers to return CUT-IN Manager.
     */
    private ArrayList<TriggerSetting> getEnableTriggers() {
        ArrayList<TriggerSetting> triggerList = new ArrayList<>();
        SparseBooleanArray items = mListView.getCheckedItemPositions();
        for (int position = 0; position < items.size(); position++) {
            if (items.get(position)) {
                triggerList.add((TriggerObject) mListView.getItemAtPosition(position));
            }
        }
        return triggerList;
    }

    /**
     * Create intent to return CUT-IN Manager.
     */
    private Intent newResultIntent(ArrayList<TriggerSetting> enableTriggers) {
        ResultBundleBuilder builder = new ResultBundleBuilder(this);
        builder.addAll(enableTriggers);

        Intent intent = new Intent();
        intent.putExtras(builder.build());

        return intent;
    }


}








