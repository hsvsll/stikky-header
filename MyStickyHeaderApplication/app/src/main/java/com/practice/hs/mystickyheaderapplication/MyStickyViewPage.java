package com.practice.hs.mystickyheaderapplication;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by huha on 2017/5/4.
 * 内部拦截
 */

public class MyStickyViewPage extends ViewPager{
    private int mLastX = 0, mLastY = 0, mLastInterceptX = 0, mLastInterceptY = 0;
    private MyStickyLayout mStickyLayout;
    public MyStickyViewPage(Context context) {
        super(context);
    }

    public MyStickyViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int diffX = x - mLastInterceptX;
                int diffY = y - mLastInterceptY;
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }
}
