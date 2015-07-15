package com.garlicg.sample.stopwatchtrigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.garlicg.cutin.triggerextension.TriggerConst;

/**
 * Receiver to inactive trigger settings when CUT-IN Manager removed.
 *
 * @author garlicG
 */
public class ManagerRemovedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // ignore case
        if (intent == null || intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) {
            return;
        }

        if(TriggerConst.MANAGER_PACKAGE.equals(getPackageName(intent))){

            // Disable all trigger settings.
            MyPreferences prefs = new MyPreferences(context);
            prefs.clearEnableTriggers();
        }
    }

    private String getPackageName(Intent intent){
        Uri uri = intent.getData();
        return uri == null ? null : uri.getSchemeSpecificPart();
    }
}
