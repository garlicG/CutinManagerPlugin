package com.garlicg.cutin.appsupport;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

/**
 * Utility with CUT-IN Manager
 */
public class ManagerUtils {

    ////////////////////////
    // Constants

    /**
     * PackageName of CUT-IN Manager
     */
    public static final String PACKAGE_NAME_CUTIN_MANAGER = "com.garlicg.cutin";

    /**
     * Pickable intent action for every cutin apps.
     */
    public final static String ACTION_PICK_CUTIN ="com.garlicg.cutin.action.PICK";

    /**
     * Set intent for specific cutin
     */
    public final static String ACTION_SET_CUTIN ="com.garlicg.cutin.action.SET";

    /**
     * Pickable intent category for every cutin apps.
     */
    public final static String CATEGORY_RESOURCE_CUTIN = "com.garlicg.cutin.category.RESOURCE";


    /**
     * Identifier of the CutinService.
     */
    public final static String EXTRA_CUTIN_ACTION ="action_name";

    /**
     * The CUT-IN Manager will use CUTIN_TITLE to display settings.
     */
    public final static String EXTRA_CUTIN_TITLE ="cutin_name";

    /**
     * Package name of CUT-IN App.
     */
    public final static String EXTRA_PACKAGE ="com.garlicg.cutin.extra.PACKAGE";

    /**
     * CutinManager save CUTIN-ID and send it to CUTIN App when trigger fire.
     * Panel -> Manager
     * Manager -> CutinService
     */
    public final static String EXTRA_ORDER_ID ="cutin_id";

    @Deprecated
    public final static String EXTRA_CUTIN_ID ="cutin_id";


    /**
     * TRIGGER_TYPE_DEMO : Demo on CUT-IN App or Unknown trigger
     * TRIGGER_TYPE_EXTENSION : Trigger from Extension Trigger Plugin.
     * TRIGGER_TYPE_OTHER : Basic Trigger of CUT-IN Manager.
     */
    public final static String EXTRA_TRIGGER_TYPE ="trigger_id";

    public final static int TRIGGER_TYPE_EXTENSION = -2;
    public final static int TRIGGER_TYPE_DEMO = -1;
    public final static int TRIGGER_TYPE_SCREEN_ON = 0;
    public static final int TRIGGER_TYPE_SCREEN_UNLOCK = 6;
    public final static int TRIGGER_TYPE_CHARGE_ON = 1;
    public final static int TRIGGER_TYPE_HEAD_SET_ON = 2;
    public final static int TRIGGER_TYPE_DATE_CHANGED = 3;
    public final static int TRIGGER_TYPE_LOW_BATTERY = 4;
    public static final int TRIGGER_TYPE_MOBILE_NETWORK_CONNECTED = 7;
    public static final int TRIGGER_TYPE_WIFI_CONNECTED = 8;
    public static final int TRIGGER_TYPE_BLUETOOTH_ON = 9;
    public static final int TRIGGER_TYPE_GPS_ON = 10;
    public static final int TRIGGER_TYPE_RINGER_MODE_VIBRATE_ON = 11;
    public static final int TRIGGER_TYPE_RINGER_MODE_SILENT_ON = 12;
    public static final int TRIGGER_TYPE_AIRPLANE_MODE_ON = 13;
    public static final int TRIGGER_TYPE_PACKAGE_ADDED = 14;
    public static final int TRIGGER_TYPE_PACKAGE_REMOVED = 15;
    public final static int TRIGGER_TYPE_NOTIFICATION = 5;

    /**
     * TEXT of useful content for CUT-IN appearance from Trigger. it may be English.
     */
    public static final String EXTRA_CONTENT_TITLE ="com.garlicg.cutin.extra.CONTENT_TITLE";

    /**
     * TEXT of useful content for CUT-IN appearance from Trigger. it may be English.
     */
    public static final String EXTRA_CONTENT_MESSAGE ="com.garlicg.cutin.extra.CONTENT_MESSAGE";

    /**
     * Ui Theme of the CUT-IN Manager.
     */
    public final static String EXTRA_THEME_UI ="ui_theme";
    public final static int THEME_UI_LIGHT = 0;
    public final static int THEME_UI_DARK = 1;

    /**
     * Send result to CUT-IN Manager.
     * @param item The CUT-IN should be registered.
     * @return intent is used to Activity#setResult.
     */
    public static Intent buildResultIntent(CutinItem item){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CUTIN_ACTION, item.serviceClass.getName());
        intent.putExtra(EXTRA_CUTIN_TITLE, item.cutinName);
        intent.putExtra(EXTRA_ORDER_ID, item.orderId);
        return intent;
    }

    /**
     * Since JB-MR2, Notification Trigger enable.
     *
     * @return enable or not.
     */
    public static boolean isEnableNotificationTrigger(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * Confirm the Activity called from CUT-IN Manager or not.
     *
     * @param intent Use Activity#getIntent
     * @return Called from CUT-IN Manager or not.
     */
    public static boolean isCalledFromManager(Intent intent){
        if(intent == null) return false;
        String action = intent.getAction();
        return action != null && action.equals(ACTION_PICK_CUTIN);
    }

    /**
     * Confirm if the CUT-IN Manager installed.
     *
     * @return exist or not.
     */
    public static boolean existManager(Context context){
        return buildLauncherIntent(context) != null;
    }

    /**
     * Get Launcher intent of CUT-IN Manager app.
     *
     * @return Intent for launch CUT-IN Manager app. Returns null if CUT-IN Manager does not exist.
     */
    public static Intent buildLauncherIntent(Context context){
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(PACKAGE_NAME_CUTIN_MANAGER);
        if(intent != null) intent.putExtra(EXTRA_PACKAGE , context.getPackageName());
        return intent;
    }

    /**
     * If CUT-IN Manager does not exit , it is good to guide to market.
     *
     * @return Intent for view the CUT-IN Manager page on market.
     */
    public static Intent buildMarketIntent(){
        return new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PACKAGE_NAME_CUTIN_MANAGER));
    }

    /**
     * Send CUT-IN for register to anonymous trigger on CUT-IN Manager.
     *
     * @return Intent to register the CUT-IN.
     */
    @Deprecated
    public static Intent buildDirectSetIntent(CutinItem item){
        Intent intent = new Intent(ACTION_SET_CUTIN);
        intent.setPackage(PACKAGE_NAME_CUTIN_MANAGER);
        intent.putExtra(EXTRA_CUTIN_ACTION, item.serviceClass.getName());
        intent.putExtra(EXTRA_CUTIN_TITLE, item.cutinName);
        intent.putExtra(EXTRA_ORDER_ID, item.orderId);
        return intent;
    }

}
