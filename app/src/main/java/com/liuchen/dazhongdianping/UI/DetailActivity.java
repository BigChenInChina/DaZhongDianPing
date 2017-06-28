package com.liuchen.dazhongdianping.UI;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liuchen.dazhongdianping.Entity.BusinessEntity;
import com.liuchen.dazhongdianping.Entity.Comment;
import com.liuchen.dazhongdianping.R;
import com.liuchen.dazhongdianping.Util.HttpUtil;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends Activity {

    BusinessEntity.Business business;
    @BindView(R.id.detail_comment_listView)
    ListView listView;
    List<String> datas;
    ArrayAdapter<String> adapter;
    TextView tvAddress;
    TextView tvTelephone;
    ImageView ivPic;
    ImageView ivRating;
    TextView tvName;
    TextView tvPrice;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        business = (BusinessEntity.Business)getIntent().getSerializableExtra("business");
        Log.i("TAG", "onCreate:DetaiActivity的商户信息： "+business.getReview_list_url());
        ButterKnife.bind(this);
        initListView();
        tvAddress = (TextView)findViewById(R.id.tv_detail_address);
        tvAddress.setText(business.getAddress());
        tvTelephone = (TextView)findViewById(R.id.tv_detail_phone);
        tvTelephone.setText(business.getTelephone());
        ivPic = (ImageView)findViewById(R.id.detail_list_view_item_image);
        ivRating = (ImageView)findViewById(R.id.detail_rating);
        tvName = (TextView)findViewById(R.id.detail_shop);
        tvPrice = (TextView)findViewById(R.id.detail_price);
        tvInfo = (TextView)findViewById(R.id.detail_info);
        HttpUtil.loadImage(business.getPhoto_url(),ivPic);
        String name = business.getName().substring(0, business.getName().indexOf("("));
        if (!TextUtils.isEmpty(business.getBranch_name())) {
            name = name + "(" + business.getBranch_name() + ")";
        }
        tvName.setText(name);
        int[] stars = new int[]{R.drawable.movie_star10,
                R.drawable.movie_star20,
                R.drawable.movie_star30,
                R.drawable.movie_star35,
                R.drawable.movie_star40,
                R.drawable.movie_star45,
                R.drawable.movie_star50};
        Random rand = new Random();
        int idx = rand.nextInt(7);
        ivRating.setImageResource(stars[idx]);
        int price = rand.nextInt(100) + 50;
        tvPrice.setText("￥" + price + "/人");
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < business.getRegions().size(); j++) {
            if (j == 0) {
                sb.append(business.getRegions().get(j));
            } else {
                sb.append("/").append(business.getRegions().get(j));
            }
        }
        sb.append(" ");

        for (int j = 0; j < business.getCategories().size(); j++) {
            if (j == 0) {
                sb.append(business.getCategories().get(j));
            } else {
                sb.append("/").append(business.getCategories().get(j));
            }
        }
        tvInfo.setText(sb.toString());
    }

    private void initListView() {
        datas = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        datas.add("aaa");
        adapter.notifyDataSetChanged();
        HttpUtil.getComment(business.getReview_list_url(), new HttpUtil.OnResponseListener<Document>() {
            @Override
            public void onResponse(Document document) {
                List<Comment> comments = new ArrayList<Comment>();
                Elements elements = document.select("div[class=comment-list] li[data-id]");
                for (Element element:elements){
                    Comment comment = new Comment();
                    Element imgElement = element.select("div[class=pic] img").get(0);
                    comment.setName(imgElement.attr("title"));
                    comment.setAvatar(imgElement.attr("src"));
                    Elements spanElements = element.select("div[class=user-info] span[class=comm-per]");
                    if (spanElements.size() > 0){
                        comment.setPrice(spanElements.get(0).text().split(" ")[1]+"/人");
                    }else {
                        comment.setPrice("");
                    }

                    Element spanElement = element.select("div[class=user-info] span[title]").get(0);
                    String rate = spanElement.attr("class");
                    comment.setRating(rate.split("-")[3]);
                    Element divElement = element.select("div[class=J_brief-cont]").get(0);
                    comment.setContent(divElement.text());
                    Elements imgElements = element.select("div[class=shop-photo] img");
                    int size = imgElements.size();
                    if (size > 3){
                        size = 3;
                    }
                    String[] imgs = new String[size];
                    for (int i = 0; i < size; i++){
                        imgs[i] = imgElements.get(i).attr("src");
                    }
                    comment.setImgs(imgs);
                    Element spanEle = element.select("div[class=misc-info] span[class=time]").get(0);
                    comment.setDate(spanEle.text());
                    comments.add(comment);
                }
                Log.i("TAG", "评论内容: "+comments);

            }
        });
    }
}
