package com.practice.hs.mystickyheaderapplication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener ,StickyLayout.OnGiveUpTouchEventListener {
    @BindView(R.id.tlTitle)
    TabLayout mTabLayoutTitle;
    @BindView(R.id.sticky_content)
    ViewPager mViewPage;
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
        mStickyContentMiddleFragment = new StickyContentFragment();
        mStickyContentRightFragment = new StickyContentFragment();
        mTitleList.add("Left");
        mTitleList.add("Middle");
        mTitleList.add("Right");
        mFragmentList.add(mStickyContentLeftFragment);
        mFragmentList.add(mStickyContentMiddleFragment);
        mFragmentList.add(mStickyContentRightFragment);
        mStickyViewPageAdapter = new StickyViewPageAdapter(getSupportFragmentManager(),mFragmentList,mTitleList);
        mViewPage.addOnPageChangeListener(this);
        mViewPage.setAdapter(mStickyViewPageAdapter);
        mTabLayoutTitle.setupWithViewPager(mViewPage);
//        mLayout.setOnGiveUpTouchEventListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
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
                if(diff > 0 && mHeaderTextView.getLayoutParams().height <= 0 && currentFragment.isScrollerToTop()){
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

}
