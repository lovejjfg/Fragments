package com.lovejjfg.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lovejjfg.sview.SupportFragment;

/**
 * Created by Joe on 2016-06-09
 * Email: lovejjfg@gmail.com
 */
public class Fragment6 extends SupportFragment implements View.OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public Fragment6() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment6 newInstance(int sectionNumber) {
        Fragment6 fragment = new Fragment6();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_6, container, false);
        rootView.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick:fragment6 " + v.getId());
        try {
            getActivity().startActivity(new Intent(getContext(), Main2Activity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
