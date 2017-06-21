package com.liuchen.dazhongdianping.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuchen.dazhongdianping.Entity.TuanEntity;
import com.liuchen.dazhongdianping.R;
import com.liuchen.dazhongdianping.Util.HttpUtil;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class DealAdapter extends MyAdapter<TuanEntity.Deal> {

    public DealAdapter(Context context, List<TuanEntity.Deal> datas){
        super(context,datas);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            view = inflater.inflate(R.layout.main_listview_item_layout,viewGroup,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }
        TuanEntity.Deal deal = getItem(i);
        HttpUtil.displayImage(deal.getS_image_url(),holder.imageViewPic);
        holder.tvTitle.setText(deal.getTitle());
        holder.tvDetail.setText(deal.getDescription());
        holder.tvPrice.setText(deal.getCurrent_price()+"");
        Random random = new Random();
        int count = random.nextInt(2000)+500;
        holder.tvCount.setText("已售"+count);

        return view;
    }
    public class ViewHolder{
        @BindView(R.id.listview_item_imageview)
        ImageView imageViewPic;
        @BindView(R.id.tradeName)
        TextView tvTitle;
        @BindView(R.id.discount)
        TextView tvDetail;
        @BindView(R.id.Facade)
        TextView tvDistance;
        @BindView(R.id.FoodPrice)
        TextView tvPrice;
        @BindView(R.id.Sales)
        TextView tvCount;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
