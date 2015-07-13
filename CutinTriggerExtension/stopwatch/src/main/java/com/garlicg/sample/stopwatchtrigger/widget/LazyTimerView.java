/*
 * LazyTimerView
 * Copyright (C) 2015 Takahiro GOTO
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.garlicg.sample.stopwatchtrigger.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.garlicg.sample.stopwatchtrigger.util.DisplayUtils;

public class LazyTimerView extends View{

    private static final char[] NUM_SET = new char[]{'0','1','2','3','4','5','6','7','8','9'};
    private final char[] mTimeText = new char[]{'0','0',':','0','0',':','0','0','0'};


    public LazyTimerView(Context context) {
        super(context);
        init();
    }

    public LazyTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LazyTimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    ///////////////
    // Attrs

    private void init(){
        setTextSizeDp(DEFAULT_TEXT_SIZE_DP);
        setTextColor(DEFAULT_TEXT_COLOR);
        mTextPaint.setAntiAlias(true);
    }


    private final static int DEFAULT_TEXT_COLOR = 0xDD000000;
    private final static int DEFAULT_TEXT_SIZE_DP = 40;
    private Paint mTextPaint = new Paint();

    public void setTextSizeDp(int size){
        setTextSize(DisplayUtils.dpToPx(getResources(), size));
    }

    public void setTextSize(int size){
        mTextPaint.setTextSize(size);
    }

    public void setTextColor(int color){
        mTextPaint.setColor(color);
    }


    /////////////////
    // Lifecycle

    private int mTextX;
    private int mTextY;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // lazy point
        // Fix showing text in center of this view
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(mTimeText, 0, mTimeText.length, bounds);
        mTextX = w / 2 - bounds.width() / 2;
        mTextY = h / 2 - bounds.height() /2;
    }


    ///////////////////
    // control

    private boolean mRunning = false;

    public boolean isRunning(){
        return mRunning;
    }

    public void start(){
        mStartTime = System.currentTimeMillis() - mAdditionalTime;
        mRunning = true;
        invalidate();
    }

    public void pause(){
        mAdditionalTime = System.currentTimeMillis() - mStartTime;
        mRunning = false;
        updateText(mAdditionalTime);
        invalidate();
    }

    public void reset(){
        mStartTime = 0;
        mAdditionalTime = 0;
        mRunning = false;
        updateText(0);
        invalidate();
    }

    private long mStartTime;
    private long mAdditionalTime;


    ////////////////////
    // draw

    private void updateText(long currentTime){
        long ms = currentTime % 1000;
        mTimeText[8] = NUM_SET[(int)ms % 10];
        mTimeText[7] = NUM_SET[(int)ms / 10 % 10];
        mTimeText[6] = NUM_SET[(int)ms / 100];
        long s = (currentTime / 1000) % 60;
        mTimeText[4] = NUM_SET[(int)s % 10];
        mTimeText[3] = NUM_SET[(int)s / 10];
        long m = (currentTime / (1000 * 60)) % 60;
        mTimeText[1] = NUM_SET[(int)m % 10];
        mTimeText[0] = NUM_SET[(int)m / 10];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mRunning){
            if(mStartTime > 0){
                long currentTime = System.currentTimeMillis() - mStartTime;
                checkTimePublish(currentTime);
                updateText(currentTime);
            }
            invalidate();
        }
        canvas.drawText(mTimeText, 0, mTimeText.length, mTextX, mTextY, mTextPaint);
    }


    /////////////////////
    // time callback

    public interface OnTimePassListener{
        void onTimePassed(long time);
    }

    private OnTimePassListener mTimePassListener;
    private long[] mTimePassSet;
    private long mLastTime = 0;

    public void setOnTimePassListener(OnTimePassListener listener , long[] time){
        if(listener != null && (time == null || time.length == 0)){
            throw new IllegalStateException("Missing time pass.");
        }
        mTimePassListener = listener;
        mTimePassSet = time;
    }

    private void checkTimePublish(long currentTime){
        if(mTimePassListener == null || currentTime == 0)return;

        for(long time : mTimePassSet){
            if(time <= currentTime && time > mLastTime ){
                mTimePassListener.onTimePassed(time);
            }
        }
        mLastTime = currentTime;
    }
}
