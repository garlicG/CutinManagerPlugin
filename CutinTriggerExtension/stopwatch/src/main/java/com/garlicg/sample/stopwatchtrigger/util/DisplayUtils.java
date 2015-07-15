package com.garlicg.sample.stopwatchtrigger.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.WindowManager;

/**
 * @author garlicG
 */
public class DisplayUtils {
	
	public static Point getWindowSize(Context context){
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Point outSize = new Point();
		if(VERSION.SDK_INT < VERSION_CODES.HONEYCOMB_MR2){
			//noinspection deprecation
			outSize.x = wm.getDefaultDisplay().getWidth();
			//noinspection deprecation
			outSize.y = wm.getDefaultDisplay().getHeight();
			return outSize;
		}
		else{
			wm.getDefaultDisplay().getSize(outSize);
			return outSize;
		}
	}

    public static int dpToPx(Resources res, int dp) {
        return (int) (res.getDisplayMetrics().density * dp + 0.5f);
    }

}
