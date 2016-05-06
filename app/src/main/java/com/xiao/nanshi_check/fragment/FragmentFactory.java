package com.xiao.nanshi_check.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZzZz on 2016/4/30/0030.
 */
public class FragmentFactory {
    private static Map<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();

    public static Fragment createFragment(int position) {
        Fragment fragment = null;
        fragment = mFragments.get(position);  //在集合中取出来Fragment
        if (fragment == null) {  //如果再集合中没有取出来 需要重新创建
            if (position == 0) {
                fragment = new ExaminationFragment();
            } else if (position == 1) {
                fragment = new EquipmentFragment();
            } else if (position == 2) {
                fragment = new ResultsFragment();
            }

        }
        if (fragment != null) {
            mFragments.put(position, fragment);// 把创建好的Fragment存放到集合中缓存起来
        }

//        Bundle bundle = new Bundle();
//        bundle.putString("content", "http://blog.csdn.net/feiduclear_up \n CSDN 废墟的树999");
//        fragment.setArguments(bundle);
        return fragment;

    }
}
