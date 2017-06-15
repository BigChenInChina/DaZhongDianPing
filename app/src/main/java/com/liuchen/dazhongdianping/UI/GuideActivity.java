package com.liuchen.dazhongdianping.UI;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.liuchen.dazhongdianping.Adapter.MyPagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;
import com.liuchen.dazhongdianping.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends FragmentActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    MyPagerAdapter adapter;

    @BindView(R.id.indicator)
    CirclePageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(10*density);
        indicator.setPageColor(0xFFFFFFFF);
        indicator.setFillColor(0xFFFF6633);
        indicator.setStrokeColor(0xFFFF6633);
        indicator.setStrokeWidth(2*density);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (position == 3){
                    indicator.setVisibility(View.INVISIBLE);
                }else {
                    indicator.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
