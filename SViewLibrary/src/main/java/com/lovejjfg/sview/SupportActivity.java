package com.lovejjfg.sview;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.lovejjfg.sview.utils.FragmentsUtil;
import com.lovejjfg.sview.utils.KeyBoardUtil;
import java.util.List;

/**
 * Created by Joe on 2016/11/13.
 * Email lovejjfg@gmail.com
 */

public abstract class SupportActivity extends AppCompatActivity implements ISupportFragment {

    public FragmentsUtil fragmentsUtil;

    @Override
    public void addToParent(int containerViewId, @NonNull SupportFragment parent, int pos,
        SupportFragment... children) {
        fragmentsUtil.addToParent(containerViewId, parent, pos, children);
    }

    @Override
    public void replaceToParent(int containerViewId, @NonNull SupportFragment parent, SupportFragment... children) {
        fragmentsUtil.replaceToParent(containerViewId, parent, children);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        fragmentsUtil = new FragmentsUtil(getSupportFragmentManager());
        super.onCreate(savedInstanceState);
    }

    public FragmentsUtil getFragmentsUtil() {
        return fragmentsUtil;
    }

    @Override
    public void initFragments(Bundle savedInstanceState, SupportFragment fragment) {
        fragmentsUtil.initFragments(savedInstanceState, fragment);
    }

    @Nullable
    @Override
    public List<Fragment> getTopFragment() {
        return fragmentsUtil.getTopFragments();
    }

    @Nullable
    @Override
    public SupportFragment findFragment(String className) {
        return fragmentsUtil.findFragment(className);
    }

    @Override
    public void loadRoot(int containerViewId, SupportFragment... root) {
        fragmentsUtil.loadRoot(containerViewId, 0, root);
    }

    @Override
    public void addToShow(SupportFragment from, SupportFragment to) {
        fragmentsUtil.addToShow(from, to);
    }

    @Override
    public boolean popTo(Class<? extends SupportFragment> target, boolean includeSelf) {
        return fragmentsUtil.popTo(target, includeSelf);
    }

    public boolean popSelf() {
        return fragmentsUtil.popSelf();
    }

    @Override
    public void replaceToShow(SupportFragment from, SupportFragment to) {
        fragmentsUtil.replaceToShow(from, to);
    }

    @Override
    public void onBackPressed() {
        if (!finishSelf()) {
            super.onBackPressed();
        }
    }

    @Override
    public void showToast(String toast) {

    }

    @Override
    public void showToast(int StringId) {

    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void closeLoadingDialog() {

    }

    @Override
    public void openKeyBoard() {
        KeyBoardUtil.openKeyBoard(this);
    }

    @Override
    public void openKeyBoard(View focusView) {
        KeyBoardUtil.openKeyBoard(this, focusView);
    }

    @Override
    public void closeKeyBoard() {
        KeyBoardUtil.closeKeyBoard(this);
    }

    @Override
    public boolean finishSelf() {
        List<Fragment> topFragments = getTopFragment();

        if (topFragments != null && !topFragments.isEmpty()) {
            for (Fragment fragment : topFragments) {
                if (fragment instanceof SupportFragment) {
                    ((SupportFragment) fragment).finishSelf();
                    // TODO: 2017/2/8 如果多个Fragment可见的时候相关处理
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }
            return true;
        }
        return true;
    }

//    @Override
//    public void saveToSharedPrefs(String key, Object value) {
//
//    }

    @Override
    public void saveViewData(Bundle bundle) {

    }

    @Override
    public void saveViewData(String key, Bundle bundle) {

    }
}
