package com.garlicg.sample.cutinappsupport.engine;

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

import com.garlicg.cutin.appsupport.CutinEngine;
import com.garlicg.cutin.appsupport.CutinService;
import com.garlicg.sample.cutinappsupport.R;
import com.garlicg.sample.cutinappsupport.util.AnimationListenerAdapter;
import com.garlicg.sample.cutinappsupport.util.GravityUtils;

public class GarlinEngine3 extends CutinEngine{

    public GarlinEngine3(CutinService cutinService) {
        super(cutinService);
    }

    private ImageView mImage;
    private View mLayout;

    @Override
    public View onCreateLayout(Context context) {
        mLayout = LayoutInflater.from(getContext()).inflate(R.layout.engine_garlin, null);
        mImage = (ImageView) mLayout.findViewById(R.id.image);
        GravityUtils.addFrameLayoutGravity(mImage , Gravity.BOTTOM | Gravity.LEFT);
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

        anim.setAnimationListener(new AnimationListenerAdapter(){
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
