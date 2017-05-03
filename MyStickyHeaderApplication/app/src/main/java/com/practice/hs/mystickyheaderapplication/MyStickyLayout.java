package com.practice.hs.mystickyheaderapplication;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by huha on 2017/5/3.
 */

public class MyStickyLayout extends LinearLayoutManager {
    public MyStickyLayout(Context context) {
        super(context);
    }

    public MyStickyLayout(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyStickyLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
