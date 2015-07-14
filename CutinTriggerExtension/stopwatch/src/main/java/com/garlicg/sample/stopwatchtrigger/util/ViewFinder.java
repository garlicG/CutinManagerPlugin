package com.garlicg.sample.stopwatchtrigger.util;

import android.app.Activity;
import android.view.View;

/**
 * @author garlicG
 */
public class ViewFinder {

    public static <T extends View> T id(View view , int id){
        return (T)view.findViewById(id);
    }

    public static <T extends View> T id(Activity activity, int id){
        return (T)activity.findViewById(id);
    }
}
