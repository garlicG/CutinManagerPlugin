package com.garlicg.cutinsupportsample;

import android.content.Intent;

import com.garlicg.cutinsupport.CutinEngine;
import com.garlicg.cutinsupport.CutinService;

public class SampleCutinService extends CutinService{

    @Override
    protected CutinEngine onCreateEngine(Intent intent, long orderId) {
        if(orderId == 1){
            return new SampleEngine1(this);
        }
        else if(orderId == 2){
            return new SampleEngine2(this);
        }
        else if(orderId == 3){
            return new SampleEngine3(this);
        }
        else {
            return new SampleEngine1(this);
        }
    }
}
