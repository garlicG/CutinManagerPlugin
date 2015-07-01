package com.garlicg.cutin.triggerextension;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

/**
 * Utility for CUT-IN Trigger.
 *
 * @author Hattsun
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
}
