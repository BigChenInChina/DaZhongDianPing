package com.liuchen.dazhongdianping.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.liuchen.dazhongdianping.Entity.CityNameEntity;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper{

    public static DBHelper INSTANCE;
    public static DBHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (DBHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new DBHelper(context);
                }
            }
        }
     return INSTANCE;
    }

    public DBHelper(Context context) {
        super(context, "city.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, CityNameEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource,CityNameEntity.class,true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
