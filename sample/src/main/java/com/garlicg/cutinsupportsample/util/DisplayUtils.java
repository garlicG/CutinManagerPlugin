package com.garlicg.cutinsupportsample.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.WindowManager;

public class DisplayUtils {

    public static int dpToPx(Resources res, int dp) {
        return (int) (res.getDisplayMetrics().density * dp + 0.5f);
    }

    @SuppressWarnings("deprecation")
    public static Point getWindowSize(Context context){
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2){
            int width = manager.getDefaultDisplay().getWidth();
            int height = manager.getDefaultDisplay().getHeight();
            return new Point(width , height);
        }
        else{
            Point outSize = new Point();
            manager.getDefaultDisplay().getSize(outSize);
            return outSize;
        }
    }

}
