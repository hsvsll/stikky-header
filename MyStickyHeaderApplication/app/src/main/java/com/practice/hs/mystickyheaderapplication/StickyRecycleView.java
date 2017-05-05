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
    private int mLastY = 0;
    private boolean isTop = false;
    private int mHeaderHeight;
    public StickyRecycleView(Context context) {
        super(context);
    }

    public StickyRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setHeaderHeight(int height){
        this.mHeaderHeight = height;
        this.isTop = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int y = (int) ev.getRawY();
        if(ev.getAction() == MotionEvent.ACTION_MOVE) {
            int deltaY = y - mLastY;
            if( deltaY > 0 && isTop && mHeaderHeight <= 0){
                isTop = false;
                Log.i("TAG","StickyRecycleView  onTouchEvent  return false");
                return false;
            }
        }
        mLastY = y;
        return super.onTouchEvent(ev);
    }
}
