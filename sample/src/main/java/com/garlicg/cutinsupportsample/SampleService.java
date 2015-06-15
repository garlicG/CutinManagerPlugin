package com.garlicg.cutinsupportsample;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;

import com.garlicg.cutinsupport.CutinEngine;
import com.garlicg.cutinsupport.CutinService;
import com.garlicg.cutinsupportsample.engine.BannerEngine;
import com.garlicg.cutinsupportsample.engine.SampleEngine1;
import com.garlicg.cutinsupportsample.engine.SampleEngine2;
import com.garlicg.cutinsupportsample.engine.SampleEngine3;

public class SampleService extends CutinService{

    public static final long ORDER_SAMPLE_1 = 0;
    public static final long ORDER_SAMPLE_2 = 1;
    public static final long ORDER_SAMPLE_3 = 2;
    public static final long ORDER_BANNER = 3;
    public static final long ORDER_SURFACE = 4;
    public static final long ORDER_GL_SURFACE = 5;

    @Override
    @Nullable
    protected CutinEngine onCreateEngine(@NonNull Intent intent, long orderId) {
        if(orderId == ORDER_SAMPLE_1){
            return new SampleEngine1(this);
        }
        else if(orderId == ORDER_SAMPLE_2){
            return new SampleEngine2(this);
        }
        else if(orderId == ORDER_SAMPLE_3){
            return new SampleEngine3(this);
        }
        else if(orderId == ORDER_BANNER){
            BannerEngine.Option option = new BannerEngine.Option();
            option.text = "BANNER!";
            option.textSizeDp = 32;
            option.textVerticalGravity = Gravity.BOTTOM;
            option.mainImageResource = R.drawable.sample_180_120;
            option.accentColor = 0xFFF0F0F0;
            option.baseColor = 0xff669900;
            option.direction = 1;
            option.gravity = Gravity.CENTER_VERTICAL;
            return new BannerEngine(this , option);
        }
        else if(orderId == ORDER_SURFACE){
            return null;
        }
        else if(orderId == ORDER_GL_SURFACE){
            return null;
        }
        else {
            return null;
        }
    }
}
