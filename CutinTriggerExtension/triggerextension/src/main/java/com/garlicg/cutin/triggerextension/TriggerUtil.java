package com.garlicg.cutin.triggerextension;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Utility for CUT-IN Trigger.
 *
 * @author Hattsun
 * @author garlicG
 */
public class TriggerUtil {

    /**
     * Returns true, if the CUT-IN Manager is installed in this Device.
     *
     * @param context Context
     * @return true if the CUT-IN Manager is installed
     */
    public static boolean isManagerInstalled(@NonNull Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.getLaunchIntentForPackage(TriggerConst.MANAGER_PACKAGE) != null;
    }

    /**
     * Get Launcher intent of CUT-IN Manager app.
     *
     * @param context Context
     * @return Intent for launch CUT-IN Manager app. Returns null if CUT-IN Manager does not exist.
     */
    public static Intent getManagerLaunchIntent(Context context){
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(TriggerConst.MANAGER_PACKAGE);
        if(intent != null) intent.putExtra(TriggerConst.EXTRA_PACKAGE_NAME , context.getPackageName());
        return intent;
    }

    /**
     * If CUT-IN Manager does not exit , it is good to guide to market.
     *
     * @return Intent for view the CUT-IN Manager page on market.
     */
    public static Intent getManagerMarketIntent(){
        return new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + TriggerConst.MANAGER_PACKAGE));
    }
}
