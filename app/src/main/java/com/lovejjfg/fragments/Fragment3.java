package com.lovejjfg.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lovejjfg.fragments.widget.CurveLayout;
import com.lovejjfg.fragments.widget.CurveView;
import com.lovejjfg.fragments.widget.ScrollAbleViewPager;
import com.lovejjfg.sview.SupportFragment;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com
 */
public class Fragment3 extends SupportFragment implements View.OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ViewPager.PageTransformer pageTransformer;
    private String[] names;
    private ArrayList<Fragment> fragments;
    private ViewPagerAdapter mAdapter;

    public Fragment3() {
    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment3 newInstance(int sectionNumber) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.view_pager)
    ScrollAbleViewPager mViewPager;

    @BindView(R.id.ts)
    CurveView mCurveView;
    @BindView(R.id.bottom_sheet)
    CurveLayout mBoottom;
    private int mCurveViewHeight;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_3, container, false);
        rootView.findViewById(R.id.tv_add).setOnClickListener(this);
        rootView.findViewById(R.id.tv_delete).setOnClickListener(this);
        rootView.findViewById(R.id.tv_clear).setOnClickListener(this);
        ButterKnife.bind(this, rootView);

        mViewPager.setPageTransformer(false, pageTransformer);
        names = getResources().getStringArray(R.array.names);
        fragments = initFragment();
        mAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setScrollable(mBoottom.isExpanded());
        mTab.setupWithViewPager(mViewPager);
        ListFragment.setCurveLayout(mBoottom);
        mBoottom.registerCallback(new CurveLayout.Callbacks() {
            private float currentTop;
            private int dy;

            @Override
            public void onSheetExpanded() {
                Log.e(TAG, "onSheetExpanded: ");
                mCurveView.onDispatchUp();
                mCurveView.setTranslationY(0);
                mCurveView.setVisibility(View.GONE);
                mTab.setTranslationY(-mCurveView.getHeight());
                mTab.setVisibility(View.VISIBLE);
                mCurveView.setScaleX(1.f);
                mCurveView.setScaleY(1.f);
                mViewPager.setScrollable(true);
                dy = 0;
            }

            @Override
            public void onSheetNarrowed() {
                Log.e(TAG, "onSheetNarrowed: ");
                mCurveView.onDispatchUp();
                mCurveView.setTranslationY(0);
                mCurveView.setScaleX(1.f);
                mCurveView.setScaleY(1.f);
                mTab.setVisibility(View.GONE);
                mViewPager.setScrollable(false);
                mCurveView.setVisibility(View.VISIBLE);
                dy = 0;

            }

            @Override
            public void onSheetPositionChanged(int sheetTop, float currentX, int ddy, boolean reverse) {

                if (mCurveViewHeight == 0) {
                    mCurveViewHeight = mCurveView.getHeight();
                    mBoottom.setDismissOffset(mCurveViewHeight);
                }
                this.dy += ddy;
                float fraction = 1 - sheetTop * 1.0f / mCurveViewHeight;
                if (!reverse) {
                    if (fraction >= 0 && !mBoottom.isExpanded()) {//向上拉
                        mTab.setVisibility(View.VISIBLE);
                        mBoottom.setTabOffset(mTab.getHeight());
                        mCurveView.setTranslationY(dy * 0.2f);
                        mTab.setTranslationY(-fraction * (mCurveView.getHeight() + mTab.getHeight()));
                    } else if (fraction < 0 && !mBoottom.isExpanded()) {//向下拉
                        mTab.setVisibility(View.GONE);
                        mCurveView.onDispatch(currentX, dy);
                        mCurveView.setScaleX(1 - fraction * 0.5f);
                        mCurveView.setScaleY(1 - fraction * 0.5f);
                    }
                }
            }
        });
        mCurveView.setOnClickListener(this);
        return rootView;


    }

    @NonNull
    private ArrayList<Fragment> initFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>(8);
        fragments.add(ListFragment.newInstance(Constants.TYPE_NORMAL));
        fragments.add(ListFragment.newInstance(Constants.TYPE_BIG_IMG));
        fragments.add(ListFragment.newInstance(Constants.TYPE_NORMAL));
        fragments.add(ListFragment.newInstance(Constants.TYPE_BIG_IMG));
        fragments.add(ListFragment.newInstance(Constants.TYPE_NORMAL));
        fragments.add(ListFragment.newInstance(Constants.TYPE_BIG_IMG));
        fragments.add(ListFragment.newInstance(Constants.TYPE_NORMAL));
        fragments.add(ListFragment.newInstance(Constants.TYPE_BIG_IMG));
        return fragments;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        Log.e(TAG, "onClick: " + i);
        if (i == R.id.tv_clear) {
            fragments.clear();
        } else if (i == R.id.tv_add) {
            ListFragment fragment1 = ListFragment.newInstance(Constants.TYPE_NORMAL);
            fragment1.setUpdate(true);
            fragments.add(0, fragment1);
        } else if (i == R.id.tv_delete) {
            ListFragment remove = (ListFragment) fragments.remove(0);
            remove.setUpdate(true);
        }
        mAdapter.setFragments(fragments);
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() == fragments.size() ? fragments.size() - 1 : mViewPager.getCurrentItem());
        mTab.setupWithViewPager(mViewPager);
    }
}
