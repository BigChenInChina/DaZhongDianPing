package com.liuchen.dazhongdianping.App;

import android.app.Application;

import com.liuchen.dazhongdianping.Entity.CityNameEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class MyApp extends Application{

    public static MyApp CONTEXT;
    public static List<CityNameEntity> cityNameEntityList;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;
    }
}
