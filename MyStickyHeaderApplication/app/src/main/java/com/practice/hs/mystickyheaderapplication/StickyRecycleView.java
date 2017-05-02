package com.practice.hs.mystickyheaderapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by huha on 2017/5/2.
 */

public class StickyRecycleView extends RecyclerView {
    public StickyRecycleView(Context context) {
        super(context);
    }

    public StickyRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        Log.i("TAG","StickyRecycleView :   onInterceptTouchEvent ");
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("TAG","StickyRecycleView :   dispatchTouchEvent ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        Log.i("TAG","StickyRecycleView :   onTouchEvent ");
        return super.onTouchEvent(e);
    }
}
