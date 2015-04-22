package com.garlicg.cutinsupportsample;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.garlicg.cutinsupport.CutinEngine;
import com.garlicg.cutinsupport.CutinService;

public class SampleEngine2 extends CutinEngine{

    private ImageView mImage;
    private View mLayout;

    public SampleEngine2(CutinService cutinService) {
        super(cutinService);
    }

    @Override
    public View onCreateLayout(Context context) {
        mLayout = LayoutInflater.from(getContext()).inflate(R.layout.sample_engine, null);
        mImage = (ImageView) mLayout.findViewById(R.id.cutinImage);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mImage.getLayoutParams();
        params.gravity = Gravity.CENTER;
        return mLayout;
    }

    @Override
    public void onStart() {
        final int layoutWidth = mLayout.getWidth();
        final int imageWidth = mImage.getWidth();
        final int centerX = mImage.getWidth() / 2;
        final int centerY = mImage.getHeight() / 2;
        final int vector = Math.random() >= 0.9 ? -1 : 1;

        AnimationSet tornado = new AnimationSet(false);

        // rotate
        RotateAnimation rotate = new RotateAnimation(0, 360 * 4 * vector, centerX, centerY);
        rotate.setDuration(1000);
        tornado.addAnimation(rotate);

        // translate
        TranslateAnimation translate;
        if(vector > 0){
            translate = new TranslateAnimation(- layoutWidth/2, layoutWidth/2 + imageWidth , 0 , 0);
        }
        else{
            translate = new TranslateAnimation(layoutWidth/2, - layoutWidth/2 - imageWidth , 0 , 0);
        }
        translate.setDuration(1000);
        translate.setInterpolator(new LinearInterpolator());
        tornado.addAnimation(translate);

        tornado.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finishCutin();
            }
        });
        mImage.startAnimation(tornado);

    }


    @Override
    public void onDestroy() {
        // none

    }
}
