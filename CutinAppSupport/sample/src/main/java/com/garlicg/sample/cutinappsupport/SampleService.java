package com.garlicg.sample.cutinappsupport;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;

import com.garlicg.cutin.appsupport.CutinEngine;
import com.garlicg.cutin.appsupport.CutinService;
import com.garlicg.cutin.appsupport.ContentOption;
import com.garlicg.sample.cutinappsupport.engine.BannerEngine;
import com.garlicg.sample.cutinappsupport.engine.GLSurfaceViewEngine;
import com.garlicg.sample.cutinappsupport.engine.GarlinEngine1;
import com.garlicg.sample.cutinappsupport.engine.GarlinEngine2;
import com.garlicg.sample.cutinappsupport.engine.GarlinEngine3;
import com.garlicg.sample.cutinappsupport.engine.SampleEngine;
import com.garlicg.sample.cutinappsupport.engine.SurfaceViewEngine;

public class SampleService extends CutinService{

    public static final long ORDER_SAMPLE = 0;
    public static final long ORDER_GARLIN_1 = 1;
    public static final long ORDER_GARLIN_2 = 2;
    public static final long ORDER_GARLIN_3 = 3;
    public static final long ORDER_BANNER = 4;
    public static final long ORDER_SURFACE = 5;
    public static final long ORDER_GL_SURFACE = 6;

    @Override
    @Nullable
    protected CutinEngine onCreateEngine(@NonNull Intent intent, long orderId) {
        // tutorial sample
        if(orderId == ORDER_SAMPLE){
            return new SampleEngine(this);
        }
        // simple samples
        else if(orderId == ORDER_GARLIN_1){
            return new GarlinEngine1(this);
        }
        else if(orderId == ORDER_GARLIN_2){
            return new GarlinEngine2(this);
        }
        else if(orderId == ORDER_GARLIN_3){
            return new GarlinEngine3(this);
        }
        // engine samples
        else if(orderId == ORDER_BANNER){
            BannerEngine.Option option = new BannerEngine.Option();
            option.text = "BANNER!";
            option.textSizeDp = 32;
            option.textVerticalGravity = Gravity.BOTTOM;
            option.mainImageResource = R.drawable.garlin_180_120;
            option.accentColor = 0xFFF0F0F0;
            option.baseColor = 0xff669900;
            option.direction = 1;
            option.gravity = Gravity.CENTER_VERTICAL;
            return new BannerEngine(this , option);
        }
        else if(orderId == ORDER_SURFACE){
            return new SurfaceViewEngine(this);
        }
        else if(orderId == ORDER_GL_SURFACE){
            return new GLSurfaceViewEngine(this);
        }
        // can define other engine if need
        else {
            // none
        }

        // stop service immediately and show nothing.
        return null;
    }
}
