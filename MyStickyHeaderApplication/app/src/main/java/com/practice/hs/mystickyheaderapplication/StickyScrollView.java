package com.practice.hs.mystickyheaderapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by qiyue on 17/4/30.
 *
 */
public class StickyScrollView extends ScrollView {
    //记录上次滑动坐标
    private int mLastX = 0;
    private int mLastY = 0;
    //记录上次拦击坐标
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;
    private int mCurDiffX, mCurDiffY;

    //方便测试先固定。
    private int maxHeight = 464;
    private int mCurrentHeight = 464;
    private RecyclerView mRecyclerView;

    public StickyScrollView(Context context) {
        super(context);
    }

    public StickyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                mCurDiffX = x - mLastXIntercept;
                mCurDiffY = y - mLastYIntercept;
//                if(Math.abs(mCurDiffX) < Math.abs(mCurDiffY)){
//                    Log.i("TAG"," mCurDiffY " +mCurDiffY);
//                    intercepted = true;
//                    mCurrentHeight += mCurDiffY;
//                    if(mCurrentHeight <= 0){
//                        intercepted = false;
//                    }else if(mCurrentHeight >= maxHeight){
//                        intercepted = false;
//                    }else {
//                        intercepted = true;
//                    }
//                }
//                else {
//                    intercepted = false;
//                }
                intercepted = false;
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if(ev.getAction() == MotionEvent.ACTION_MOVE){
//            scrollTo(0, y - mLastY);
//            mLastY = y;
//            Log.i("TAG","StickyScrollView  onTouchEvent  ACTION_MOVE");
        }else if(ev.getAction() == MotionEvent.ACTION_DOWN){
//            Log.i("TAG","StickyScrollView  onTouchEvent  ACTION_DOWN");
        }else if(ev.getAction() == MotionEvent.ACTION_UP){
//            Log.i("TAG","StickyScrollView  onTouchEvent  ACTION_UP");
        }
        return super.onTouchEvent(ev);
    }
}
