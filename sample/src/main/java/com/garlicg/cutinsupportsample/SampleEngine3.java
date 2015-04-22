package com.garlicg.cutinsupportsample;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.garlicg.cutinsupport.CutinEngine;
import com.garlicg.cutinsupport.CutinService;

public class SampleEngine3 extends CutinEngine{

    private ImageView mImage;
    private View mLayout;

    public SampleEngine3(CutinService cutinService) {
        super(cutinService);
    }

    @Override
    public View onCreateLayout(Context context) {
        mLayout = LayoutInflater.from(getContext()).inflate(R.layout.sample_engine, null);
        mImage = (ImageView) mLayout.findViewById(R.id.cutinImage);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mImage.getLayoutParams();
        params.gravity = Gravity.BOTTOM| Gravity.LEFT;
        return mLayout;
    }

    @Override
    public void onStart() {
        final int layoutHeight = mLayout.getHeight();
        final int imageHeight = mImage.getWidth();
        final int xPos = (int)(
                (mLayout.getWidth() - mImage.getWidth())
                        * Math.random()
        );

        AnimationSet anim = new AnimationSet(false);

        // translate
        TranslateAnimation translate= new TranslateAnimation(xPos, xPos , imageHeight , -layoutHeight);
        translate.setDuration(1000);
        translate.setInterpolator(new AccelerateInterpolator());
        anim.addAnimation(translate);

        anim.setAnimationListener(new Animation.AnimationListener() {

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
        mImage.startAnimation(anim);
    }


    @Override
    public void onDestroy() {
        // none

    }
}
