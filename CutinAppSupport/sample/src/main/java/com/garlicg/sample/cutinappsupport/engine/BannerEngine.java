package com.garlicg.sample.cutinappsupport.engine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.garlicg.cutin.appsupport.CutinEngine;
import com.garlicg.cutin.appsupport.CutinService;
import com.garlicg.sample.cutinappsupport.R;
import com.garlicg.sample.cutinappsupport.util.AnimationListenerAdapter;
import com.garlicg.sample.cutinappsupport.util.DisplayUtils;
import com.garlicg.sample.cutinappsupport.util.GravityUtils;
import com.garlicg.sample.cutinappsupport.util.ViewFinder;

public class BannerEngine extends CutinEngine{

    public static class Option {
        public int accentColor;
        public int baseColor;
        public int mainImageResource;
        public String text;
        public int textVerticalGravity = Gravity.CENTER_VERTICAL;
        public float textSizeDp = 60;
        public int gravity = Gravity.CENTER_VERTICAL;
        public int direction = 1; // 1: main image to Right  , -1: main image to Left
    }

    private Option mOption;


    public BannerEngine(CutinService service , Option option){
        super(service);
        mOption = option;
    }

    private FrameLayout mFrame;
    private View mLineTopBackView;
    private View mLineTopFrontView;
    private View mLineBottomBackView;
    private View mLineBottomFrontView;
    private View mBackgroundView;
    private ImageView mShadowImage;
    private ImageView mMainImage;
    private TextView mLogoText;


    @SuppressLint("RtlHardcoded")
    @Override
    public View onCreateLayout(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.engine_banner, null);
        mFrame = ViewFinder.byId(root, R.id.frame);
        mLineTopBackView = ViewFinder.byId(root, R.id.line_top_back);
        mLineTopFrontView = ViewFinder.byId(root,R.id.line_top_front);
        mLineBottomBackView = ViewFinder.byId(root,R.id.line_bottom_back);
        mLineBottomFrontView = ViewFinder.byId(root,R.id.line_bottom_front);
        mBackgroundView = ViewFinder.byId(root,R.id.background);
        mShadowImage = ViewFinder.byId(root,R.id.image_shadow);
        mMainImage = ViewFinder.byId(root,R.id.image);
        mLogoText = ViewFinder.byId(root,R.id.logo);

        // prepare from option
        final Option option = mOption;
        GravityUtils.addFrameLayoutGravity(mFrame, option.gravity);
        mLineTopFrontView.setBackgroundColor(option.accentColor);
        mLineBottomFrontView.setBackgroundColor(option.accentColor);
        mLineTopBackView.setBackgroundColor(option.baseColor);
        mLineBottomBackView.setBackgroundColor(option.baseColor);
        mBackgroundView.setBackgroundColor(option.baseColor);
        mShadowImage.setImageResource(option.mainImageResource);
        mShadowImage.setColorFilter(option.baseColor);
        GravityUtils.addFrameLayoutGravity(mShadowImage, mOption.direction > 0 ? Gravity.RIGHT : Gravity.LEFT);
        mMainImage.setImageResource(option.mainImageResource);
        GravityUtils.addFrameLayoutGravity(mMainImage, mOption.direction > 0 ?  Gravity.RIGHT : Gravity.LEFT);
        mLogoText.setText(option.text);
        mLogoText.setTextColor(option.accentColor);
        mLogoText.setShadowLayer(3f, 3f, 3f, option.baseColor);
        if(option.textSizeDp > -1)mLogoText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, option.textSizeDp);
        GravityUtils.addFrameLayoutGravity(mLogoText, option.direction > 0 ? Gravity.LEFT | option.textVerticalGravity : Gravity.RIGHT | option.textVerticalGravity);
        return root;
    }

    private final static int ENTER_PHASE = 400;
    private final static int MOVE_PHASE = 1200;
    private final static int OUT_PHASE = 300;
    private int mImageWidth;
    private int mLineWidth;

    @Override
    public void onStart() {
        enterPhase();
    }

    private void enterPhase(){
        //-- line enter
        mLineWidth = mLineBottomFrontView.getWidth();
        mLineTopFrontView.setVisibility(View.VISIBLE);
        mLineTopBackView.setVisibility(View.VISIBLE);
        mLineBottomBackView.setVisibility(View.VISIBLE);
        mLineBottomFrontView.setVisibility(View.VISIBLE);
        TranslateAnimation lineFrontEnter = new TranslateAnimation(mLineWidth* mOption.direction, 0, 0, 0);
        lineFrontEnter.setDuration(1200);
        lineFrontEnter.setInterpolator(new LinearInterpolator());
        mLineBottomFrontView.setAnimation(lineFrontEnter);
        mLineTopFrontView.setAnimation(lineFrontEnter);
        TranslateAnimation lineBackEnter = new TranslateAnimation(mLineWidth* -mOption.direction,0,0,0);
        lineBackEnter.setDuration(1200);
        lineBackEnter.setInterpolator(new LinearInterpolator());
        mLineTopBackView.setAnimation(lineBackEnter);
        mLineBottomBackView.setAnimation(lineBackEnter);
        lineFrontEnter.start();
        lineBackEnter.start();

        //-- logo enter
        final float logoWidth = mLogoText.getWidth();
        mLogoText.setVisibility(View.VISIBLE);
        int dp16 = DisplayUtils.dpToPx(getContext().getResources(), 16);
        TranslateAnimation logoEnter =  new TranslateAnimation((logoWidth+dp16)*-mOption.direction, 0, 0, 0);
        logoEnter.setDuration(ENTER_PHASE);
        logoEnter.setInterpolator(new AccelerateInterpolator());
        mLogoText.startAnimation(logoEnter);

        // -- backEnter
        mBackgroundView.setVisibility(View.VISIBLE);
        AlphaAnimation alpha = new AlphaAnimation(0.f, 0.3f);
        alpha.setDuration(ENTER_PHASE);
        alpha.setFillAfter(true);
        mBackgroundView.startAnimation(alpha);

        //-- main enter
        mImageWidth = mMainImage.getWidth();

        // shadow
        mShadowImage.setVisibility(View.VISIBLE);
        TranslateAnimation shadowEnter =  new TranslateAnimation((mLineWidth+mImageWidth)*mOption.direction, 0, 0, 0);
        shadowEnter.setDuration(ENTER_PHASE);
        shadowEnter.setInterpolator(new DecelerateInterpolator());
        mShadowImage.startAnimation(shadowEnter);

        // mainImage
        mMainImage.setVisibility(View.VISIBLE);
        TranslateAnimation imageEnter =  new TranslateAnimation(mLineWidth* -mOption.direction, (mImageWidth/8)*-mOption.direction, 0, 0);
        imageEnter.setDuration(ENTER_PHASE);
        imageEnter.setInterpolator(new DecelerateInterpolator());
        imageEnter.setAnimationListener(new AnimationListenerAdapter(){
            @Override
            public void onAnimationEnd(Animation animation) {
                movePhase();
            }
        });
        mMainImage.startAnimation(imageEnter);
    }

    private void movePhase(){
        if(isDestroyed())return;

        //-- move phase

        //background
        AlphaAnimation alpha = new AlphaAnimation(0.3f, 0.8f);
        alpha.setDuration(MOVE_PHASE);
        alpha.setFillAfter(true);
        mBackgroundView.startAnimation(alpha);

        // shadow
        TranslateAnimation shadowMove =  new TranslateAnimation(0,(mImageWidth/8)*-mOption.direction, 0, 0);
        shadowMove.setDuration(MOVE_PHASE);
        shadowMove.setInterpolator(new LinearInterpolator());
        mShadowImage.startAnimation(shadowMove);

        // image
        TranslateAnimation imageMove =  new TranslateAnimation((mImageWidth/8)*-mOption.direction, 0, 0, 0);
        imageMove.setDuration(MOVE_PHASE);
        imageMove.setInterpolator(new LinearInterpolator());
        imageMove.setAnimationListener(new AnimationListenerAdapter(){
            @Override
            public void onAnimationEnd(Animation animation) {
                outPhase();
            }
        });
        mMainImage.startAnimation(imageMove);
    }


    private void outPhase(){
        if(isDestroyed())return;

        // line
        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0f);
        alpha.setDuration(OUT_PHASE);
        mLineBottomBackView.startAnimation(alpha);
        mLineTopBackView.startAnimation(alpha);
        mLineBottomFrontView.startAnimation(alpha);
        mLineTopFrontView.startAnimation(alpha);

        // logo
        mLogoText.startAnimation(alpha);

        // backGround
        AlphaAnimation bgEndAlpha = new AlphaAnimation(0.8f, 0f);
        bgEndAlpha.setDuration(OUT_PHASE);
        mBackgroundView.startAnimation(bgEndAlpha);

        // shadow
        TranslateAnimation shadowOut =  new TranslateAnimation((mImageWidth/8)* -mOption.direction, mLineWidth*-mOption.direction, 0, 0);
        shadowOut.setDuration(OUT_PHASE);
        shadowOut.setInterpolator(new AccelerateInterpolator());
        mShadowImage.startAnimation(shadowOut);

        // image
        TranslateAnimation imageOut =  new TranslateAnimation(0, (mLineWidth+mImageWidth)*mOption.direction, 0, 0);
        imageOut.setDuration(OUT_PHASE);
        imageOut.setInterpolator(new AccelerateInterpolator());
        imageOut.setAnimationListener(new AnimationListenerAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                mFrame.setVisibility(View.INVISIBLE);
                finishCutin();
            }
        });
        mMainImage.startAnimation(imageOut);
    }

}
