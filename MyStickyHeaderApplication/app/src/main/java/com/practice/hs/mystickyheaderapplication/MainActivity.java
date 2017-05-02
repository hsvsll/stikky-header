package com.practice.hs.mystickyheaderapplication;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements StickyContentFragment.OnFragmentInteractionListener {
    @BindView(R.id.tlTitle)
    TabLayout mTabLayoutTitle;
    @BindView(R.id.vpContent)
    ViewPager mViewPage;
    @BindView(R.id.svMain)
    StickyScrollView mScrollView;

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;
    private StickyContentFragment mStickyContentLeftFragment;
    private StickyContentFragment mStickyContentMiddleFragment;
    private StickyContentFragment mStickyContentRightFragment;
    private StickyViewPageAdapter mStickyViewPageAdapter;
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
        mViewPage.setAdapter(mStickyViewPageAdapter);
        mTabLayoutTitle.setupWithViewPager(mViewPage);
//        mScrollView.smoothScrollTo(0,0);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
