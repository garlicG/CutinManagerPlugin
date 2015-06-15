package com.garlicg.cutinsupportsample;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.garlicg.cutinsupport.CutinEngine;
import com.garlicg.cutinsupport.CutinService;
import com.garlicg.cutinsupportsample.engine.SampleEngine1;
import com.garlicg.cutinsupportsample.engine.SampleEngine2;
import com.garlicg.cutinsupportsample.engine.SampleEngine3;

public class SampleService extends CutinService{

    public static final long ORDER_SAMPLE_1 = 0;
    public static final long ORDER_SAMPLE_2 = 1;
    public static final long ORDER_SAMPLE_3 = 2;


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
        else {
            return null;
        }
    }
}
