package com.garlicg.sample.cutinappsupport.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class GravityUtils {

    public static void addFrameLayoutGravity(View frameChild , int gravity){
        ViewGroup.LayoutParams p = frameChild.getLayoutParams();
        if(p instanceof FrameLayout.LayoutParams){
            FrameLayout.LayoutParams fp = (FrameLayout.LayoutParams)frameChild.getLayoutParams();
            fp.gravity = gravity;
        }
    }
}