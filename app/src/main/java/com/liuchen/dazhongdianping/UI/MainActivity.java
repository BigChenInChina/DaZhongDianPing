package com.liuchen.dazhongdianping.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.liuchen.dazhongdianping.Adapter.DealAdapter;
import com.liuchen.dazhongdianping.Entity.TuanEntity;
import com.liuchen.dazhongdianping.R;
import com.liuchen.dazhongdianping.Util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends Activity {

    //头部
    @BindView(R.id.ll_header_left_container)
    LinearLayout cityContainer;
    @BindView(R.id.tv_header_main_city)
    TextView tvCity;
    @BindView(R.id.main_head_iv_search)
    ImageView ivAdd;
    @BindView(R.id.menu_layout)
    View menuLayout;

    //中段
    @BindView(R.id.PTRlv_main_list)
    PullToRefreshListView PTRlv_main_list;

    ListView listView;
    List<TuanEntity.Deal> datas;
    DealAdapter adapter;

    //脚部
    @BindView(R.id.rg_main_footer)
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialListView();
    }

    @OnClick(R.id.ll_header_left_container)
    public void jumpToCity(View view) {
        Intent intent = new Intent(this, CityActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.main_head_add)
    public void toggleMenu(View view) {
        if (menuLayout.getVisibility() == View.VISIBLE) {
            menuLayout.setVisibility(View.INVISIBLE);
        } else {
            menuLayout.setVisibility(View.VISIBLE);
        }
    }

    private void initialListView() {
        listView = PTRlv_main_list.getRefreshableView();
        datas = new ArrayList<TuanEntity.Deal>();
        adapter = new DealAdapter(this, datas);

        //为ListView添加若干个头部
        LayoutInflater inflater = LayoutInflater.from(this);

        View listHeaderIcons = inflater.inflate(R.layout.main_head_app, listView, false);
        View listHeaderSquares = inflater.inflate(R.layout.header_list_square, listView, false);
        View listHeaderAds = inflater.inflate(R.layout.header_list_ads, listView, false);
        View listHeaderCategories = inflater.inflate(R.layout.header_list_categories, listView, false);
        View listHeaderRecommend = inflater.inflate(R.layout.header_list_recommend, listView, false);

        listView.addHeaderView(listHeaderIcons);
        listView.addHeaderView(listHeaderSquares);
        listView.addHeaderView(listHeaderAds);
        listView.addHeaderView(listHeaderCategories);
        listView.addHeaderView(listHeaderRecommend);

        listView.setAdapter(adapter);

        initialListHeaderIcons(listHeaderIcons);
        //添加下拉松手后的刷新
        PTRlv_main_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        datas.add(0,"新增内容");
                        adapter.notifyDataSetChanged();
                        PTRlv_main_list.onRefreshComplete();
                    }
                },3000);*/
                refresh();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int i) {
            }

            @Override
            public void onScroll(AbsListView view, int i, int i1, int i2) {
                if (i == 0) {
                    cityContainer.setVisibility(View.VISIBLE);
                    ivAdd.setVisibility(View.VISIBLE);
                } else {
                    cityContainer.setVisibility(View.GONE);
                    ivAdd.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initialListHeaderIcons(View listHeaderIcons) {
        final ViewPager viewPager = (ViewPager) listHeaderIcons.findViewById(R.id.main_head_app_viewPager);

        PagerAdapter adapter = new PagerAdapter() {
            int[] resIDs = new int[]{
                    R.layout.app1,
                    R.layout.app2,
                    R.layout.app3,
            };

            @Override
            public int getCount() {
                return 3000;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                int layoutId = resIDs[position % 3];
                View view = LayoutInflater.from(MainActivity.this).inflate(layoutId, viewPager, false);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(15000);

        final ImageView iv1 = (ImageView) listHeaderIcons.findViewById(R.id.iv_header_list_icons_indicator1);
        final ImageView iv2 = (ImageView) listHeaderIcons.findViewById(R.id.iv_header_list_icons_indicator2);
        final ImageView iv3 = (ImageView) listHeaderIcons.findViewById(R.id.iv_header_list_icons_indicator3);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                iv1.setImageResource(R.drawable.banner_dot);
                iv2.setImageResource(R.drawable.banner_dot);
                iv3.setImageResource(R.drawable.banner_dot);

                switch (position % 3) {
                    case 0:
                        iv1.setImageResource(R.drawable.banner_dot_pressed);
                        break;
                    case 1:
                        iv2.setImageResource(R.drawable.banner_dot_pressed);
                        break;
                    case 2:
                        iv3.setImageResource(R.drawable.banner_dot_pressed);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {

        //1)发起一个请求，服务器响应
        //以GET的方式发起请求
        //请求格式：http://xxx.xxxx.com/xxx？key=14xxxxxxx&city=%e8%f8%c6%xx%xx%xx
        //利用HttpClient(apache)
        //HttpURLConnection

        //Volley
        /*HttpUtil.getDailyDealsByVolley(tvCity.getText().toString(), new Response.Listener<TuanEntity>() {
            @Override
            public void onResponse(TuanEntity s) {
                if (s != null){
                    List<TuanEntity.Deal> deals = s.getDeals();
                    adapter.addAll(deals,true);
                }else {
                    //今日无新增团购内容
                    Toast.makeText(MainActivity.this, "今日无新增团购内容", Toast.LENGTH_SHORT).show();
                }
                PTRlv_main_list.onRefreshComplete();
            }
        });*/
        //Retrofit+OKHttp
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HttpUtil.getDailyDealsByRetrofit(tvCity.getText().toString(), new Callback<TuanEntity>() {
                    @Override
                    public void onResponse(Call<TuanEntity> call, retrofit2.Response<TuanEntity> response) {
                        if (response != null) {
                            TuanEntity tuanEntity = response.body();
                            List<TuanEntity.Deal> deals = tuanEntity.getDeals();
                            adapter.addAll(deals, true);
                        } else {
                            Toast.makeText(MainActivity.this, "今日无新增团购内容", Toast.LENGTH_SHORT).show();
                        }
                        PTRlv_main_list.onRefreshComplete();
                    }

                    @Override
                    public void onFailure(Call<TuanEntity> call, Throwable throwable) {
                        Log.d("TAG", "onFailure: " + throwable.getMessage());
                        PTRlv_main_list.onRefreshComplete();
                    }
                });
            }
        }, 2000);
        //2)根据服务器响应的内容进行解析
        // JSON字符串 / XML文档
        // 解析JSON字符串：
        // JSONLib(JsonObject)
        // GSON
        // fastJson
        // jackson
        // 解析XML文档
        // XMLPull

        // SAX

        //3)将解析结果放到View中显示
        //放到ListView中显示需要适配器、条目布局

        //HttpUtil.testHttpURLConnection();
        //HttpUtil.testVolley();
        //HttpUtil.testRetrofit();

    }
}
