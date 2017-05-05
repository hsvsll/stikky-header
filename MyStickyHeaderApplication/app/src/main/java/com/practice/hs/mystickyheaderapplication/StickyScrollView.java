package com.practice.hs.mystickyheaderapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by qiyue on 17/4/30.
 *
 */
public class StickyScrollView extends ScrollView {
    //记录上次拦击坐标
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;
    private int mCurDiffX, mCurDiffY;

    //方便测试先固定。
    private int maxHeight = 464;
    private int mCurrentHeight = 464;
    private RecyclerView mRecyclerView;

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

    private MyStickyLayout.OnGiveUpTouchEventListener mGiveUpTouchEventListener;

    public void setOnGiveUpTouchEventListener(MyStickyLayout.OnGiveUpTouchEventListener l) {
        mGiveUpTouchEventListener = l;
    }

    public StickyScrollView(Context context) {
        super(context);
        mScroller = new Scroller(getContext());
    }

    public StickyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(getContext());
    }

    public StickyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(getContext());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StickyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


//    @Override
//    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
//        return super.onStartNestedScroll(child, target, nestedScrollAxes);
//    }
//
//    @Override
//    public void onNestedScrollAccepted(View child, View target, int axes) {
//        super.onNestedScrollAccepted(child, target, axes);
//    }
//
//    @Override
//    public void onStopNestedScroll(View target) {
//        super.onStopNestedScroll(target);
//    }
//
//    @Override
//    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
//    }
//
//    @Override
//    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
//        return consumed;
//    }
//
//    //返回true代表父view消耗滑动速度，子View将不会滑动
//    @Override
//    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
//        if (null == mRecyclerView) mRecyclerView = (RecyclerView) target;
//        if (mRecyclerView.computeVerticalScrollOffset() != 0) {
//            return false;
//        }
//        this.fling((int) velocityY);
//        return true;
//    }
//
//    //对应子view 的dispatchNestedPreScroll方法， 最后一个数组代表消耗的滚动量，下标0代表x轴，下标1代表y轴
//    @Override
//    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//        //判断是否滚动到最大值
//        if ( dy >= 0 && this.getScrollY() < maxHeight) {
//            if (null == mRecyclerView) mRecyclerView = (RecyclerView) target;
//            //计算RecyclerView的偏移量， 等于0的时候说明recyclerView没有滑动，否则应该交给recyclerView自己处理
//            if (mRecyclerView.computeVerticalScrollOffset() != 0) return;
//            this.smoothScrollBy(dx, dy);
//            consumed[1] = dy; //consumed[1]赋值为 dy ，代表父类已经消耗了改滚动。
//        }
//    }

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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int height = y - mLastY;
                smoothScrollTo(height);
                break;
            case MotionEvent.ACTION_UP:
                //the header show or hide
                int dy = y - mLastY;
                smoothScrollTo(dy);
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollTo(int dy){
        int scrollY = getScrollY();
        if( mHeaderHeight <= mOriginalHeight * 0.5){
            status = STATUS_COLLAPSED;
        }else{
            status = STATUS_EXPANDED;
        }

//        int deltaY = scrollY + dy;
        mScroller.startScroll(0,scrollY, 0, dy, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
