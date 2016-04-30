package com.xiao.nanshi_check.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xiao.nanshi_check.fragment.FragmentFactory;

/**
 * Created by ZzZz on 2016/4/30/0030.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    private String[] tab_Names;

    public FragmentAdapter(FragmentManager fm, String[] tab_names) {
        super(fm);
        tab_Names = tab_names;
    }

    @Override
    public Fragment getItem(int position) {

        return FragmentFactory.createFragment(position);
    }

    @Override
    public int getCount() {
        return tab_Names.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab_Names[position];
    }
}
