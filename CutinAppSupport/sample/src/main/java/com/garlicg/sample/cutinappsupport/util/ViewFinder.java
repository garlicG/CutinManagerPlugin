package com.garlicg.sample.cutinappsupport.util;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

public class ViewFinder {

    public static <T extends View> T byId(View view, int id) {
        return (T) view.findViewById(id);
    }

    public static <T extends View> T byId(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

    public static <T extends View> T byId(Dialog dialog, int id) {
        return (T) dialog.findViewById(id);
    }
}
