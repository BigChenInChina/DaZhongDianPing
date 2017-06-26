package com.liuchen.dazhongdianping.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuchen.dazhongdianping.Entity.BusinessEntity;
import com.liuchen.dazhongdianping.R;
import com.liuchen.dazhongdianping.Util.HttpUtil;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class BusinessAdapter extends MyAdapter<BusinessEntity.Business> {
    public BusinessAdapter(Context context, List<BusinessEntity.Business> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BusinessAdapter.ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.business_list_view_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        BusinessEntity.Business item = getItem(i);
        HttpUtil.loadImage(item.getPhoto_url(), holder.ivPic);
        String name = item.getName().substring(0, item.getName().indexOf("("));
        if (!TextUtils.isEmpty(item.getBranch_name())) {
            name = name + "(" + item.getBranch_name() + ")";
        }
        holder.tvName.setText(name);

        int[] stars = new int[]{R.drawable.movie_star10,
                R.drawable.movie_star20,
                R.drawable.movie_star30,
                R.drawable.movie_star35,
                R.drawable.movie_star40,
                R.drawable.movie_star45,
                R.drawable.movie_star50};
        Random rand = new Random();
        int idx = rand.nextInt(7);
        holder.ivRating.setImageResource(stars[idx]);
        int price = rand.nextInt(200) + 90;

        holder.tvPrice.setText("￥" + price + "/人");

        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < item.getRegions().size(); j++) {

            if (j == 0) {
                sb.append(item.getRegions().get(j));
            } else {
                sb.append("/").append(item.getRegions().get(j));
            }


        }

        sb.append(" ");

        for (int j = 0; j < item.getCategories().size(); j++) {
            if (j == 0) {
                sb.append(item.getCategories().get(j));
            } else {
                sb.append("/").append(item.getCategories().get(j));
            }
        }

        holder.tvInfo.setText(sb.toString());
        return view;
    }

    public class ViewHolder {

        @BindView(R.id.business_list_view_item_image)
        ImageView ivPic;
        @BindView(R.id.business_shop)
        TextView tvName;
        @BindView(R.id.business_rating)
        ImageView ivRating;
        @BindView(R.id.business_price)
        TextView tvPrice;
        @BindView(R.id.business_info)
        TextView tvInfo;
        @BindView(R.id.business_distance)
        TextView tvDistance;


        public ViewHolder(View view) {

            ButterKnife.bind(this, view);
        }

    }
}
