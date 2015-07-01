package com.garlicg.sample.cutinappsupport.engine;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.garlicg.cutin.appsupport.CutinEngine;
import com.garlicg.cutin.appsupport.CutinService;
import com.garlicg.sample.cutinappsupport.R;
import com.garlicg.sample.cutinappsupport.util.AnimationListenerAdapter;

public class SampleEngine1 extends CutinEngine{

    public SampleEngine1(CutinService cutinService) {
        super(cutinService);
    }

    private ImageView mImage;
    private View mLayout;

    @Override
    public View onCreateLayout(Context context) {
        mLayout = LayoutInflater.from(getContext()).inflate(R.layout.engine_sample, null);
        mImage = (ImageView) mLayout.findViewById(R.id.image);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mImage.getLayoutParams();
        params.gravity = Gravity.CENTER;
        return mLayout;
    }

    @Override
    public void onStart() {

        final int centerX = mImage.getWidth() / 2;
        final int centerY = mImage.getHeight() / 2;

        AnimationSet tornado = new AnimationSet(false);
        // in
        RotateAnimation rotateIn = new RotateAnimation(0, 1170, centerX, centerY);
        rotateIn.setDuration(600);
        tornado.addAnimation(rotateIn);

        // scale up
        ScaleAnimation scale = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, centerX, centerY);
        scale.setDuration(600);
        scale.setStartOffset(600);
        tornado.addAnimation(scale);
        RotateAnimation rotateMain = new RotateAnimation(90f, -90f, centerX, centerY);
        rotateMain.setDuration(600);
        rotateMain.setStartOffset(600);
        tornado.addAnimation(rotateMain);

        // out
        RotateAnimation rotateOut = new RotateAnimation(0, -1080, mImage.getWidth() / 2, mImage.getHeight() / 2);
        rotateOut.setDuration(600);
        rotateOut.setStartOffset(1200);
        tornado.addAnimation(rotateOut);

        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(600);
        fadeOut.setStartOffset(1200);
        tornado.addAnimation(fadeOut);

        tornado.setAnimationListener(new AnimationListenerAdapter(){
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
