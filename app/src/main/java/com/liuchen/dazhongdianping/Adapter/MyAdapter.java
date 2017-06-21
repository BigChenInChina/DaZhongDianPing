package com.liuchen.dazhongdianping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public abstract class MyAdapter<T> extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<T> datas;

    public MyAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void add(T t){
        datas.add(t);
        notifyDataSetChanged();
    }

    /**
     * 追加或更新数据
     * @param list
     * @param isClear
     */
    public void addAll(List<T> list,boolean isClear){
        if (isClear){
            datas.clear();
        }
        datas.addAll(list);
        notifyDataSetChanged();
    }

    public void removeAll(){
        datas.clear();
        notifyDataSetChanged();
    }
}
