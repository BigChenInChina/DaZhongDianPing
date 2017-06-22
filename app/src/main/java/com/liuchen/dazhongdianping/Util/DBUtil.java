package com.liuchen.dazhongdianping.Util;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.liuchen.dazhongdianping.Entity.CityNameEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class DBUtil {
    Dao<CityNameEntity,String> dao;
    DBHelper dbHelper;

    public DBUtil(Context context){
        dbHelper = DBHelper.getInstance(context);
        try {
            dao = dbHelper.getDao(CityNameEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insert(CityNameEntity cityNameEntity){
        try {
            dao.createIfNotExists(cityNameEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertAll(List<CityNameEntity> list){
        for (CityNameEntity bean :list){
            insert(bean);
        }
    }

    public List<CityNameEntity> query(){
        try {
            List<CityNameEntity> cityNameEntityList = dao.queryForAll();
            return cityNameEntityList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询数据库时出现异常");
        }
    }
}
