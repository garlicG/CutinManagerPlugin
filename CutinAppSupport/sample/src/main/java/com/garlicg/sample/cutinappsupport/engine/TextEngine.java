package com.garlicg.sample.cutinappsupport.engine;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.garlicg.cutin.appsupport.CutinEngine;
import com.garlicg.cutin.appsupport.CutinService;
import com.garlicg.sample.cutinappsupport.R;
import com.garlicg.sample.cutinappsupport.util.AnimationListenerAdapter;
import com.garlicg.sample.cutinappsupport.util.DisplayUtils;
import com.garlicg.sample.cutinappsupport.util.ViewFinder;

public class TextEngine extends CutinEngine{

    private String mTitle;
    private String mMessage;

    public TextEngine(CutinService cutinService , String title , String message) {
        super(cutinService);
        mTitle = title;
        mMessage = message;
    }


    private View mBackground;
    private TextView mTitleView;
    private TextView mMessageView;

    @Override
    public View onCreateLayout(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.engine_text, null);
        mBackground = ViewFinder.byId(root, R.id.background);

        mTitleView = ViewFinder.byId(root, R.id.title);
        mTitleView.setText(mTitle);

        mMessageView = ViewFinder.byId(root, R.id.message);
        mMessageView.setText(mMessage);
        return root;
    }

    private Handler mHandler = new Handler();

    @Override
    public void onStart() {
        backgroundIn(200);

        mMessageView.setVisibility(View.VISIBLE);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                titleIn(300);
            }
        }, 100);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                titleOut(300);
            }
        }, 1100);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                backgroundOut(200);
            }
        }, 1200);
    }

    void backgroundIn(long duration){
        mBackground.setVisibility(View.VISIBLE);
        AlphaAnimation alpha = new AlphaAnimation(0f , 1f);
        alpha.setDuration(duration);
        mBackground.startAnimation(alpha);
    }

    void backgroundOut(long duration){
        AlphaAnimation alpha = new AlphaAnimation(1f , 0f);
        alpha.setDuration(duration);
        alpha.setAnimationListener(new AnimationListenerAdapter(){
            @Override
            public void onAnimationEnd(Animation animation) {
                if(!isDestroyed()) mBackground.setVisibility(View.INVISIBLE);

                finishCutin();
            }
        });
        mBackground.startAnimation(alpha);
    }

    void titleIn(long duration){
        mTitleView.setVisibility(View.VISIBLE);

        AlphaAnimation alpha = new AlphaAnimation(0f , 1f);
        alpha.setDuration(duration);
        float dp40 = DisplayUtils.dpToPx(getContext().getResources(), 40);
        TranslateAnimation translate = new TranslateAnimation(-dp40 , 0 , 0 , 0);
        translate.setDuration(duration);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(alpha);
        set.addAnimation(translate);
        mTitleView.startAnimation(set);
    }

    void titleOut(long duration){
        mTitleView.setVisibility(View.VISIBLE);

        AlphaAnimation alpha = new AlphaAnimation(1f , 0f);
        alpha.setDuration(duration);
        float dp40 = DisplayUtils.dpToPx(getContext().getResources() , 40);
        TranslateAnimation translate = new TranslateAnimation(0 , dp40 , 0 , 0);
        translate.setDuration(duration);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(alpha);
        set.addAnimation(translate);
        set.setFillAfter(true);
        mTitleView.startAnimation(set);
    }
}
