package com.liuchen.dazhongdianping.UI;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.liuchen.dazhongdianping.Adapter.CityAdapter;
import com.liuchen.dazhongdianping.Entity.CityEntity;
import com.liuchen.dazhongdianping.R;
import com.liuchen.dazhongdianping.Util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityActivity extends Activity {

    @BindView(R.id.city_recyclerView)
    RecyclerView cityRecyclerView;

    List<String> datas;

    CityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        initialRecyclerView();
    }

    private void initialRecyclerView() {
        cityRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        datas = new ArrayList<String>();
        adapter = new CityAdapter(this,datas);
        cityRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        HttpUtil.getCitiesByRetrofit(new Callback<CityEntity>() {
            @Override
            public void onResponse(Call<CityEntity> call, Response<CityEntity> response) {
                CityEntity cityEntity = response.body();
                List<String> list = cityEntity.getCities();
                adapter.addAll(list,true);
            }

            @Override
            public void onFailure(Call<CityEntity> call, Throwable throwable) {
            }
        });
    }
}
