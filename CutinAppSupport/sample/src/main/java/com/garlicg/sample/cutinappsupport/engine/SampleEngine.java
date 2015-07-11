package com.garlicg.sample.cutinappsupport.engine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.garlicg.cutin.appsupport.CutinEngine;
import com.garlicg.cutin.appsupport.CutinService;
import com.garlicg.sample.cutinappsupport.R;
import com.garlicg.sample.cutinappsupport.util.AnimationListenerAdapter;

public class SampleEngine extends CutinEngine{

    public SampleEngine(CutinService cutinService) {
        super(cutinService);
    }

    private ImageView mImageView;

    @Override
    public View onCreateLayout(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.engine_sample , null);
        mImageView = (ImageView)root.findViewById(R.id.image);
        return root;
    }

    @Override
    public void onStart() {
        final int centerX = mImageView.getWidth() / 2;
        final int centerY = mImageView.getHeight() / 2;

        // Rotate a second
        RotateAnimation rotate = new RotateAnimation(0, 1170, centerX, centerY);
        rotate.setDuration(1000);
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // must finish cutin when end of anim.
                finishCutin();
            }
        });

        mImageView.startAnimation(rotate);
    }
}
