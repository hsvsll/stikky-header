package com.practice.hs.mystickyheaderapplication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MyStickyLayout.OnGiveUpTouchEventListener
,StickyContentFragment.OnRecycleMoveToTopEventListener {
    @BindView(R.id.tlTitle)
    TabLayout mTabLayoutTitle;
    @BindView(R.id.sticky_content)
    CustomViewPage mViewPage;
    @BindView(R.id.sticky_layout)
    MyStickyLayout mLayout;
    @BindView(R.id.sticky_header)
    TextView mHeaderTextView;

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;
    private StickyContentFragment mStickyContentLeftFragment;
    private StickyContentFragment mStickyContentMiddleFragment;
    private StickyContentFragment mStickyContentRightFragment;
    private StickyViewPageAdapter mStickyViewPageAdapter;
    private StickyContentFragment currentFragment;
    private int mLastY = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitleList = new ArrayList<>();
        mFragmentList = new ArrayList<>();
        mStickyContentLeftFragment = new StickyContentFragment();
        mStickyContentLeftFragment.setOnRecycleMoveToTopEventListener(this);

        mStickyContentMiddleFragment = new StickyContentFragment();
        mStickyContentMiddleFragment.setOnRecycleMoveToTopEventListener(this);

        mStickyContentRightFragment = new StickyContentFragment();
        mStickyContentRightFragment.setOnRecycleMoveToTopEventListener(this);
        mTitleList.add("Left");
        mTitleList.add("Middle");
        mTitleList.add("Right");
        mFragmentList.add(mStickyContentLeftFragment);
        mFragmentList.add(mStickyContentMiddleFragment);
        mFragmentList.add(mStickyContentRightFragment);
        mStickyViewPageAdapter = new StickyViewPageAdapter(getSupportFragmentManager(),mFragmentList,mTitleList);
        mViewPage.setAdapter(mStickyViewPageAdapter);
        mTabLayoutTitle.setupWithViewPager(mViewPage);
        mLayout.setOnGiveUpTouchEventListener(this);
    }

    @Override
    public boolean giveUpTouchEvent(MotionEvent event) {
        currentFragment = (StickyContentFragment) mStickyViewPageAdapter.getItem(mViewPage.getCurrentItem());
        int y = (int) event.getY();
        int x = (int) event.getX();
        boolean giveUp = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                int diff = y - mLastY;
                if( mHeaderTextView.getLayoutParams().height <= 0 && currentFragment.isScrollerToTop()){
                    giveUp = true;
                }else {
                    giveUp = false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_DOWN:
                giveUp = false;
                break;
        }
        mLastY = y;
        return giveUp;
    }

    @Override
    public void moveToTopEvent() {
        currentFragment = (StickyContentFragment) mStickyViewPageAdapter.getItem(mViewPage.getCurrentItem());
        currentFragment.setHeaderHeight(mHeaderTextView.getLayoutParams().height);
    }
}
