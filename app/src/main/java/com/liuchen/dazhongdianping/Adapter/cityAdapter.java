package com.liuchen.dazhongdianping.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.liuchen.dazhongdianping.Entity.CityNameEntity;
import com.liuchen.dazhongdianping.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> implements SectionIndexer{

    Context context;
    List<CityNameEntity> datas;
    LayoutInflater layoutInflater;
    OnItemClickListener listener;
    View headerView;
    private static final int HEADER = 0;
    private static final int ITEM = 1;

    public CityAdapter(Context context, List<CityNameEntity> datas){
        this.datas = datas;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void addHeaderView(View v){
        if (this.headerView == null){
            this.headerView = v;
            notifyItemChanged(0);
        }else {
            throw new RuntimeException("不允许添加多个头部");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (this.headerView != null){
            if (position == 0){
                return HEADER;
            }else {
                return ITEM;
            }
        }else {
            return ITEM;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER){
            MyViewHolder myViewHolder = new MyViewHolder(headerView);
            return myViewHolder;
        }
        View view = layoutInflater.inflate(R.layout.city_recycler_view_item_layout,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (headerView != null &&position == 0){
            return;
        }
        final int dataIndex = getDataIndex(position);
        CityNameEntity cityNameEntity = datas.get(dataIndex);
        holder.tvCityName.setText(cityNameEntity.getCityName());
        holder.tvLetter.setText(cityNameEntity.getLetter()+"");

        if (dataIndex == getPositionForSection(getSectionForPosition(dataIndex))){
            holder.tvLetter.setVisibility(View.VISIBLE);
        }else {
            holder.tvLetter.setVisibility(View.GONE);
        }
        View itemView = holder.itemView;
        if(this.listener!=null){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(view,dataIndex);
                }
            });
        }
    }

    private int getDataIndex(int position) {

        return headerView == null?position:position-1;
    }


    @Override
    public int getItemCount() {

        return datas.size();
    }

    public void addAll(List<CityNameEntity> list,boolean isClear){

        if (isClear){
            datas.clear();
        }
        datas.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int section) {
        for(int i = 0; i < datas.size(); i++){


            if (datas.get(i).getLetter() == section){
                return i;

            }
        }
        return datas.size()+1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return datas.get(position).getLetter();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @Nullable
        @BindView(R.id.city_item_Letter)
        TextView tvLetter;
        @Nullable
        @BindView(R.id.city_item_CityName)
        TextView tvCityName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface OnItemClickListener{
        void OnItemClick(View itemView,int position);
    }
}
