package com.practice.hs.mystickyheaderapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link StickyContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StickyContentFragment extends Fragment{
    @BindView(R.id.rvStickyContentList)
    RecyclerView mRecycleView;

    private FullyLinearLayoutManager mFullyLinearLayoutManager;
    private StickyRecycleViewAdapter mStickyRecycleViewAdapter;
    private List<String> mData;
    private LinearLayoutManager mLinearLayoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int totalChange;

    public StickyContentFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StickyContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StickyContentFragment newInstance(String param1, String param2) {
        StickyContentFragment fragment = new StickyContentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initData() {
        mData = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            mData.add("AAA"+i);
        }
    }

    private void initView() {
        mStickyRecycleViewAdapter = new StickyRecycleViewAdapter(mData);
//        mLinearLayoutManager = new FullyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        mLinearLayoutManager.setScrollEnabled(false);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(mLinearLayoutManager);

        mRecycleView.setAdapter(mStickyRecycleViewAdapter);
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sticky_conten, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public boolean isScrollerToTop(){
        return mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0;
    }

}
