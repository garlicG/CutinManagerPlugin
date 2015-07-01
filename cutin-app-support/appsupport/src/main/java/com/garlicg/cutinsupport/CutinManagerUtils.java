package com.garlicg.cutinsupport;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

/**
 * Information of Cut-In Manager
 */
public class CutinManagerUtils {

    ////////////////////////
    // Constants


    /**
     * PackageName of CutinManager
     */
    public static final String PACKAGE_NAME_CUTIN_MANAGER = "com.garlicg.cutin";

    /**
     * Pickable intent action for every cutin apps.
     */
    public final static String ACTION_PICK_CUTIN ="com.garlicg.cutin.action.PICK";

    /**
     * Pickable intent category for every cutin apps.
     */
    public final static String CATEGORY_CUTIN = "com.garlicg.cutin.category.RESOURCE";

    /**
     * Set intent for specific cutin
     */
    public final static String ACTION_SET_CUTIN ="com.garlicg.cutin.action.SET";

    /**
     * CutinManager save cutin action and will call cutin app using this when trigger fired.
     * Panel -> Manager
     */
    public final static String EXTRA_CUTIN_ACTION ="action_name";

    /**
     * CutinManager save cutin title and show cutin title to user.
     * Panel -> Manager
     */
    public final static String EXTRA_CUTIN_TITLE ="cutin_name";

    /**
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
     * Trigger list of CutinManager
     * Manager -> CutinService
     */
    public final static String EXTRA_TRIGGER_ID ="trigger_id";
    public final static int TRIGGER_ID_DEMO = -1;
    public final static int TRIGGER_ID_SCREEN_ON = 0;
    public static final int TRIGGER_ID_SCREEN_UNLOCK = 6;

    public final static int TRIGGER_ID_CHARGE_ON = 1;
    public final static int TRIGGER_ID_HEAD_SET_ON = 2;

    public final static int TRIGGER_ID_DATE_CHANGED = 3;
    public final static int TRIGGER_ID_LOW_BATTERY = 4;

    public static final int TRIGGER_ID_MOBILE_NETWORK_CONNECTED = 7;
    public static final int TRIGGER_ID_WIFI_CONNECTED = 8;

    public static final int TRIGGER_ID_BLUETOOTH_ON = 9;
    public static final int TRIGGER_ID_GPS_ON = 10;
    public static final int TRIGGER_ID_RINGER_MODE_VIBRATE_ON = 11;
    public static final int TRIGGER_ID_RINGER_MODE_SILENT_ON = 12;
    public static final int TRIGGER_ID_AIRPLANE_MODE_ON = 13;

    public static final int TRIGGER_ID_PACKAGE_ADDED = 14;
    public static final int TRIGGER_ID_PACKAGE_REMOVED = 15;
    public final static int TRIGGER_ID_NOTIFICATION = 5;
    public final static String EXTRA_NOTIFICATION_TICKER ="notification_ticker";
    public final static String EXTRA_NOTIFICATION_PACKAGE_NAME="notification_package_name";


    /**
     * CutinManager's theme
     */
    public final static String EXTRA_THEME_UI ="ui_theme";
    public final static int THEME_UI_LIGHT = 0;
    public final static int THEME_UI_DARK = 1;



    public static Intent buildResultIntent(CutinItem item){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CUTIN_ACTION, item.serviceClass.getName());
        intent.putExtra(EXTRA_CUTIN_TITLE, item.cutinName);
        intent.putExtra(EXTRA_ORDER_ID, item.orderId);
        return intent;
    }

    public static boolean isEnableNotificationTrigger(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }


    public static boolean isCalledFromCutinManager(Intent intent){
        if(intent == null) return false;
        String action = intent.getAction();
        if(action == null) return false;

        if(action.equals(ACTION_PICK_CUTIN)){
            return true;
        }
        else {
            return false;
        }
    }


    public static Intent buildLauncherIntent(Context context){
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(PACKAGE_NAME_CUTIN_MANAGER);
        if(intent != null) intent.putExtra(EXTRA_PACKAGE , context.getPackageName());
        return intent;
    }

    public static boolean existManager(Context context){
        return buildLauncherIntent(context) != null;
    }

    public static Intent buildMarketIntent(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.garlicg.cutin"));
        return intent;
    }

    public static Intent buildSetCutinIntent(CutinItem item){
        Intent intent = new Intent(ACTION_SET_CUTIN);
        intent.setPackage(PACKAGE_NAME_CUTIN_MANAGER);
        intent.putExtra(EXTRA_CUTIN_ACTION, item.serviceClass.getName());
        intent.putExtra(EXTRA_CUTIN_TITLE, item.cutinName);
        intent.putExtra(EXTRA_ORDER_ID, item.orderId);
        return intent;
    }

    public static boolean startSetCutinActivity(Context context , CutinItem item){
        try{
            context.startActivity(buildSetCutinIntent(item));
        }catch(ActivityNotFoundException e){
            return false;
        }
        return true;
    }

}
