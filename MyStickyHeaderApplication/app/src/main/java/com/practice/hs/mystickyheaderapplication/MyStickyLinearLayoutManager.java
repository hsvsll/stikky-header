package com.practice.hs.mystickyheaderapplication;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by qiyue on 17/4/30.
 * Custom RecycleView LayoutManage
 */
public class MyStickyLinearLayoutManager extends LinearLayoutManager{
    public MyStickyLinearLayoutManager(Context context) {
        super(context);
    }

    public MyStickyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyStickyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


}
