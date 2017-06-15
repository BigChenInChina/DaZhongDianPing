package com.liuchen.dazhongdianping.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.liuchen.dazhongdianping.Fragment.Fragment1;
import com.liuchen.dazhongdianping.Fragment.Fragment2;
import com.liuchen.dazhongdianping.Fragment.Fragment3;
import com.liuchen.dazhongdianping.Fragment.Fragment4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList;
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new Fragment1());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment3());
        fragmentList.add(new Fragment4());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
