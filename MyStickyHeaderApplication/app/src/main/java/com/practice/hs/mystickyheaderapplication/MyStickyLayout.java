package com.practice.hs.mystickyheaderapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

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
    private int status = STATUS_EXPANDED;
    private static final int STATUS_EXPANDED = 1;
    private static final int STATUS_COLLAPSED = 2;

    private Scroller mScroller;

    public interface OnGiveUpTouchEventListener {
        boolean giveUpTouchEvent(MotionEvent event);
    }

    private OnGiveUpTouchEventListener mGiveUpTouchEventListener;

    public MyStickyLayout(Context context) {
        super(context);
        init(context,null);
    }

    public MyStickyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public MyStickyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private void init(Context context, AttributeSet attributeSet) {
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if( hasWindowFocus && (mContentView == null || mContentView == null )){
            initData();
        }
    }

    public void initData(){
        mScroller = new Scroller(getContext());
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

    public void setOnGiveUpTouchEventListener(OnGiveUpTouchEventListener l) {
        mGiveUpTouchEventListener = l;
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
                if(status == STATUS_EXPANDED && diffY < -mTouchSlop){
                    intercept = true;
                }else if( mGiveUpTouchEventListener != null && mGiveUpTouchEventListener.giveUpTouchEvent(ev) && diffY > mTouchSlop){
                    intercept = true;
                }else if(Math.abs(diffX) >= Math.abs(diffY)){
                    intercept = false;
                }
//                else if( y <= getHeaderHeight()){
//                    intercept = false;
//                }

                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                mLastInterceptX = 0;
                mLastInterceptY = 0;
                break;
            default:
                break;
        }
        Log.d("TAG", "intercept=" + intercept );
        return intercept;
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        final int action = ev.getActionMasked();
//        final boolean shouldRedirectDownTouch = action == MotionEvent.ACTION_MOVE
////                && (!isIntercepted && isPrevIntercepted)
//                && mHeaderHeight == 0;
//
//        Log.i("TAG", "shouldRedirectDownTouch : "+shouldRedirectDownTouch);
//        if (shouldRedirectDownTouch) {
//            mMotionEventHook.hook(ev, MotionEvent.ACTION_DOWN);
//        }
//        super.dispatchTouchEvent(ev);
//        return true;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("TAG","StickyRecycleView  onTouchEvent  return false");
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                 int height = y - mLastY;
                mHeaderHeight += height;
                if(mHeaderHeight <= 0 &&  mGiveUpTouchEventListener != null && mGiveUpTouchEventListener.giveUpTouchEvent(event)){
                    event.setAction(MotionEvent.ACTION_DOWN);
                    dispatchTouchEvent(event);
                }else {
                    setHeaderHeight(mHeaderHeight);
                }
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

    private void smoothScrollTo(){
        int scrollY = getScrollY();
        if( mHeaderHeight <= mOriginalHeight * 0.5){
            status = STATUS_COLLAPSED;
        }else{
            status = STATUS_EXPANDED;
        }
        int deltaY = scrollY + mHeaderHeight;
        mScroller.startScroll(0,scrollY, 0, deltaY, 500);
        invalidate();
    }

    private void smoothShowOrHideHeader() {
        int height = mHeaderHeight;
        if( height <= mOriginalHeight * 0.5){
            height = 0;
            status = STATUS_COLLAPSED;
        }else{
            height = mOriginalHeight;
            status = STATUS_EXPANDED;
        }
        final int resultHeight = height;
        ValueAnimator valueAnimatorCompat = ValueAnimator.ofFloat(0,1);
        valueAnimatorCompat.setDuration(500);
        valueAnimatorCompat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int result = (int) ((resultHeight - mHeaderHeight) * animation.getAnimatedFraction());
                mHeaderHeight = mHeaderHeight + result;
                mHeaderView.getLayoutParams().height = mHeaderHeight;
                mHeaderView.requestLayout();
            }
        });
        valueAnimatorCompat.start();

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

        if (height == 0) {
            status = STATUS_COLLAPSED;
        } else {
            status = STATUS_EXPANDED;
        }

        if(mHeaderView != null && mHeaderView.getLayoutParams() != null){
            mHeaderView.getLayoutParams().height = height;
            mHeaderView.requestLayout();
            mHeaderHeight = height;
        }
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
