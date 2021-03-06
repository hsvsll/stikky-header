package com.practice.hs.mystickyheaderapplication;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by huha on 2017/5/2.
 */

public class CustomViewPage extends ViewPager{
    private int mLastY = 0;

    private int mHeaderHeight;

    public CustomViewPage(Context context) {
        super(context);
    }

    public CustomViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHeaderHeight(int height){
        this.mHeaderHeight = height;
    }

    public interface OnGiveUpTouchEventListener {
        boolean giveUpTouchEvent(MotionEvent event);
    }

    private OnGiveUpTouchEventListener mGiveUpTouchEventListener;

    public void setOnGiveUpTouchEventListener(OnGiveUpTouchEventListener l) {
        mGiveUpTouchEventListener = l;
    }

//    private void init() {
//        mScroller = new Scroller(getContext());
//        this.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
//
//        this.addOnPageChangeListener(new OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int arg0) {
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//                CustomViewPage.this.getParent().requestDisallowInterceptTouchEvent(true);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//            }
//        });
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        View maxHeightView = getChildAt(0);
//
//        int size = getChildCount();
//        for (int i = 0; i < size; i++) {
//            View child = getChildAt(i);
//            if (child != null && child.getVisibility() != GONE) {
//                child.measure(widthMeasureSpec, heightMeasureSpec);
//                if (child.getHeight() > maxHeightView.getHeight()) {
//                    maxHeightView = child;
//                }
//            }
//        }
//
//        if (maxHeightView != null) {
//            setMeasuredDimension(getMeasuredWidth(), measureHeight(heightMeasureSpec, maxHeightView));
//        }
//    }
//
//
//    /**
//     * Determines the height of this view
//     *
//     * @param measureSpec A measureSpec packed into an int
//     * @param view the base view with already measured height
//     *
//     * @return The height of the view, honoring constraints from measureSpec
//     */
//    private int measureHeight(int measureSpec, View view) {
//        int result = 0;
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//
//        if (specMode == MeasureSpec.EXACTLY) {
//            result = specSize;
//        } else {
//            // set the height from the base view if available
//            if (view != null) {
//                result = view.getMeasuredHeight();
//                if (result > mMaxHeightSize) {
//                    mMaxHeightSize = result;
//                } else {
//                    result = mMaxHeightSize;
//                }
//            }
//            if (specMode == MeasureSpec.AT_MOST) {
//                result = Math.min(result, specSize);
//            }
//        }
//        return result;
//    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        int curY = (int) ev.getRawY();
//        int curX = (int) ev.getRawX();
//        boolean resume = false;
//        int action = ev.getAction();
//        switch (action){
//            case MotionEvent.ACTION_DOWN:
////                mLastMotionY = curY;
//                resume = true;
//                break;
//            case MotionEvent.ACTION_MOVE:
////                // deltaY > 0 是向下运动,< 0是向上运动
//                float deltaY = curY - mLastY;
//                float deltaX = curX - mLastInterceptX;
//                if(Math.abs(deltaX) < Math.abs(deltaY)){
//                }
//                resume = true;
//                break;
//            case MotionEvent.ACTION_UP:
//                resume = false;
//                break;
//            default:
//                resume = false;
//                break;
//        }
//        mLastInterceptX = curX;
//        mLastY = curY;
//        return resume;
//    }
//
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("TAG","CustomViewPage  onTouchEvent  return false");
        return false;
    }

}
