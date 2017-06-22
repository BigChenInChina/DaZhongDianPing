package com.liuchen.dazhongdianping.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.liuchen.dazhongdianping.App.MyApp;
import com.liuchen.dazhongdianping.Entity.CityNameEntity;
import com.liuchen.dazhongdianping.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

public class SearchActivity extends Activity {
    @BindView(R.id.lv_search_listview)
    ListView listView;
    List<String> cities;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initialListView();
    }

    @OnTextChanged(R.id.et_search)
    public void search(Editable editable){
        if(editable.length()==0){
            cities.clear();
            adapter.notifyDataSetChanged();
        }else{
            searchCities(editable.toString().toUpperCase());
        }
    }

    private void searchCities(String s){
        List<String> temps = new ArrayList<String>();
        if (s.matches("[\u4e00-\u9fff]+")){
            for (CityNameEntity entity : MyApp.cityNameEntityList){
                if (entity.getCityName().contains(s)){
                    temps.add(entity.getCityName());
                }
            }
        }else {
            for (CityNameEntity c :MyApp.cityNameEntityList){
                if (c.getPyName().contains(s)){
                    temps.add(c.getCityName());
                }
            }
        }
        if (temps.size() > 0){
            cities.clear();
            cities.addAll(temps);
            adapter.notifyDataSetChanged();
        }
    }

    private void initialListView() {
        cities = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cities);
        listView.setAdapter(adapter);
    }

    @OnItemClick(R.id.lv_search_listview)
    public void selectCity(AdapterView<?> adapterView, View view,int i ,long l){
        Intent data = new Intent();
        String city = adapter.getItem(i);
        data.putExtra("city",city);
        Log.i("TAG", "R.id.lv_search_listView "+city);
        setResult(RESULT_OK,data);
        finish();
    }
    @OnClick(R.id.search_city_back)
    public void backCityActivity(View view){
        Intent intent = new Intent(SearchActivity.this,CityActivity.class);
        startActivityForResult(intent,101);
        finish();
    }
}
