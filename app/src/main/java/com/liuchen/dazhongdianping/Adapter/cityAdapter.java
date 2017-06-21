package com.liuchen.dazhongdianping.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liuchen.dazhongdianping.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {

    Context context;
    List<String> datas;
    LayoutInflater layoutInflater;

    public CityAdapter(Context context, List<String> datas){
        this.datas = datas;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.city_recycler_view_item_layout,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String cityName = datas.get(position);
        holder.tvCityName.setText(cityName);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addAll(List<String> list,boolean isClear){
        if (isClear){
            datas.clear();
        }
        datas.addAll(list);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.city_item_Letter)
        TextView tvLetter;
        @BindView(R.id.city_item_CityName)
        TextView tvCityName;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
