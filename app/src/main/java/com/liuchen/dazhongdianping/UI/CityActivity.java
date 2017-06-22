package com.liuchen.dazhongdianping.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.liuchen.dazhongdianping.Adapter.CityAdapter;
import com.liuchen.dazhongdianping.App.MyApp;
import com.liuchen.dazhongdianping.Entity.CityEntity;
import com.liuchen.dazhongdianping.Entity.CityNameEntity;
import com.liuchen.dazhongdianping.R;
import com.liuchen.dazhongdianping.Util.DBUtil;
import com.liuchen.dazhongdianping.Util.HttpUtil;
import com.liuchen.dazhongdianping.Util.PinYinUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityActivity extends Activity {

    @BindView(R.id.city_recyclerView)
    RecyclerView cityRecyclerView;

    List<CityNameEntity> datas;

    CityAdapter adapter;

    DBUtil dbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        dbUtil = new DBUtil(this);
        ButterKnife.bind(this);
        initialRecyclerView();
    }

    private void initialRecyclerView() {
        cityRecyclerView.setLayoutManager(new LinearLayoutManager
                (this,LinearLayoutManager.VERTICAL,false));
        datas = new ArrayList<CityNameEntity>();
        adapter = new CityAdapter(this,datas);
        cityRecyclerView.setAdapter(adapter);
        View headerView = LayoutInflater.from(this).inflate
                (R.layout.city_name_item_head,cityRecyclerView,false);
        adapter.addHeaderView(headerView);
        adapter.setOnItemClickListener(new CityAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View itemView, int position) {
                CityNameEntity cityNameEntity = datas.get(position);
                //Toast.makeText(CityActivity.this, cityNameEntity.getCityName(), Toast.LENGTH_SHORT).show();
                String city = cityNameEntity.getCityName();

                Intent data = new Intent();
                data.putExtra("city",city);
                setResult(RESULT_OK,data);

                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
    @OnClick(R.id.city_back)
    public void backMainActivity(View view){
        Intent intent = new Intent(CityActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.city_head_tv_search)
    public void jumpToCity(View view) {
        Intent intent = new Intent(CityActivity.this, SearchActivity.class);
        startActivityForResult(intent,101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && requestCode == 101){
//            Intent data2 = new Intent();
//            String city = data.getStringExtra("city");
//            data2.putExtra("city",city);
            setResult(RESULT_OK,data);
            finish();
        }
    }

    private void refresh() {
        if (MyApp.cityNameEntityList != null && MyApp.cityNameEntityList.size() > 0){
            adapter.addAll(MyApp.cityNameEntityList,true);
            Log.d("TAG", "数据从缓存中读取 ");
            return;
        }


        List<CityNameEntity> list = dbUtil.query();
        if (list != null &&list.size() > 0){
            adapter.addAll(list,true);

            MyApp.cityNameEntityList = list;
            Log.d("TAG", "城市数据从数据库中加载 ");
            return;
        }

        HttpUtil.getCitiesByRetrofit(new Callback<CityEntity>() {
            @Override
            public void onResponse(Call<CityEntity> call, Response<CityEntity> response) {
                CityEntity cityEntity = response.body();
                List<String> list = cityEntity.getCities();
                final List<CityNameEntity> cityNameEntities = new ArrayList<CityNameEntity>();
                for (String name : list){
                    if (!name.equals("全国")&&!name.equals("其它城市")&&!name.equals("点评实验室")){
                        CityNameEntity cityNameEntity = new CityNameEntity();
                        cityNameEntity.setCityName(name);
                        cityNameEntity.setPyName(PinYinUtil.getPinYin(name));
                        cityNameEntity.setLetter(PinYinUtil.getLetter(name));
                        cityNameEntities.add(cityNameEntity);
                    }
                }
                Collections.sort(cityNameEntities, new Comparator<CityNameEntity>() {
                    @Override
                    public int compare(CityNameEntity t1, CityNameEntity t2) {
                        return t1.getPyName().compareTo(t2.getPyName());
                    }
                });

                adapter.addAll(cityNameEntities,true);
                Log.i("TAG", "城市名称数据从网络中加载");
                MyApp.cityNameEntityList = cityNameEntities;

                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        long start = System.currentTimeMillis();
                        dbUtil.insertAll(cityNameEntities);
                        Log.d("TAG", "写入数据库完毕，耗时："+(System.currentTimeMillis()-start));
                    }
                }.start();
            }

            @Override
            public void onFailure(Call<CityEntity> call, Throwable throwable) {
            }
        });
    }
}
