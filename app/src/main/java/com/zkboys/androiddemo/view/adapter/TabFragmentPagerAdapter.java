package com.zkboys.androiddemo.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;


/**
 * 这里使用多个fragment进行切换，好处是每个fragment 都有自己独立的代码
 * <p>
 * Created by cg on 2015/10/21.
 */

public class TabFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private List<Fragment> mFragments; // fragment列表
    private List<String> mTitles; // tab名的列表

    public TabFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments, List<String> titles) {
        super(fragmentManager);
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    /**
     * 此方法是给tablayout中的tab赋值的，就是显示名称
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position % mTitles.size());
    }
}