package com.practice.hs.mystickyheaderapplication;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * Created by huha on 2017/5/3.
 */

public class MyStickyLayout extends LinearLayout {
    private int mLastX = 0, mLastY = 0, mLastInterceptX = 0, mLastInterceptY = 0;
    private int mTouchSlop;
    private View mHeaderView;
    private View mContentView;
    private int mHeaderHeight;
    private int mOriginalHeight;
    private boolean mInitDataSucceed = false;
    public MyStickyLayout(Context context) {
        super(context);
    }

    public MyStickyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyStickyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyStickyLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if( hasWindowFocus && (mContentView == null || mContentView == null )){
            initData();
        }
    }

    public void initData(){
        int headerId = getResources().getIdentifier("sticky_header","id",getContext().getPackageName());
        int contentId = getResources().getIdentifier("sticky_content","id",getContext().getPackageName());
        if(headerId != 0 && contentId != 0){
            mHeaderView = findViewById(headerId);
            mContentView = findViewById(contentId);
            mOriginalHeight = mHeaderView.getMeasuredHeight();
            mHeaderHeight = mOriginalHeight;
            mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
            if(mHeaderHeight > 0){
                mInitDataSucceed = true;
            }
        }else {
            new Throwable ("the view don't have id sticky_header and sticky_content ");
        }
    }

    public int getHeaderHeight(){
        return mHeaderHeight;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                mLastInterceptX = x;
                mLastX = x;
                mLastInterceptY = y;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int diffX = x - mLastInterceptX;
                int diffY = y - mLastInterceptY;
                if(mHeaderHeight == mOriginalHeight && diffY < -mTouchSlop){
                    intercept = true;
                }else if( mHeaderHeight == 0 && diffY > mTouchSlop){
                    intercept = true;
                }else {
                    intercept = false;
                }

                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                mLastInterceptX = 0;
                mLastInterceptY = 0;
                break;
            default:
                break;
        }

        Log.d("TAG", "mHeaderHeight=" + mHeaderHeight +" mOriginalHeight = " +mOriginalHeight);

        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int height = 0;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                 height = y - mLastY;
                mHeaderHeight += height;
                setHeaderHeight(mHeaderHeight);
                break;
            case MotionEvent.ACTION_UP:
                //the header show or hide
                smoothShowOrHideHeader();
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothShowOrHideHeader() {
        int height = mHeaderHeight;
        if( height < mOriginalHeight * 0.5){
            height = 0;
        }else{
            height = mOriginalHeight;
        }
        final int resultHeight = height;
        ValueAnimator valueAnimatorCompat = ValueAnimator.ofFloat(0,1);
        valueAnimatorCompat.setDuration(500);
        valueAnimatorCompat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int result = (int) ((resultHeight - mHeaderHeight) * animation.getAnimatedFraction());
                mHeaderView.getLayoutParams().height = mHeaderHeight + result;
            }
        });

    }

    private void setHeaderHeight(int height) {
        if (!mInitDataSucceed) {
            initData();
        }
        if( height < 0){
            height = 0;
        }else if( height > mOriginalHeight){
            height = mOriginalHeight;
        }

        if(mHeaderView != null && mHeaderView.getLayoutParams() != null){
            mHeaderView.getLayoutParams().height = height;
            mHeaderView.requestLayout();
            mHeaderHeight = height;
        }
    }

}
