package com.lovejjfg.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lovejjfg.fragments.model.ModelBean;
import com.lovejjfg.fragments.pagetransformer.ScaleInTransformer;
import com.lovejjfg.fragments.pagetransformer.ScalePageTransformer;
import com.lovejjfg.sview.SupportFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com
 */
public class Fragment1 extends SupportFragment implements View.OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ViewPager.PageTransformer pageTransformer;

    public Fragment1() {
    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment1 newInstance() {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, "Fragment1");
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    public void onAttach(Context context) {
        Gson gson = new Gson();
        ModelBean modelBean = gson.fromJson(getString(R.string.gson), ModelBean.class);
        Log.e(TAG, "Fragment1: " + modelBean.toString());
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_1, container, false);
        ButterKnife.bind(this, rootView);
        mViewPager.setAdapter(new ImagePagerAdapter());
//        pageTransformer = new ScalePageTransformer(mViewPager);
        ScaleInTransformer scaleInTransformer = new ScaleInTransformer();
        mViewPager.setPageMargin(20);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setPageTransformer(false, scaleInTransformer);

        mViewPager.setCurrentItem(5);
//        mViewPager.setPageTransformer(false, pageTransformer);

//        if (savedInstanceState == null) {
//
//            mViewPager.post(new Runnable() {
//                @Override
//                public void run() {
//                    mViewPager.setCurrentItem(50, true);
//                }
//            });
//            mViewPager.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mViewPager.setVisibility(View.VISIBLE);
//                }
//            }, 200);
//        } else {
//            mViewPager.setVisibility(View.VISIBLE);
//        }

        return rootView;
    }


    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick: " + v.getId());
    }

    @Override
    public boolean finishSelf() {
        return false;
    }
}
