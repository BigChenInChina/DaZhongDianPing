package com.liuchen.dazhongdianping.Util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Response;
import com.liuchen.dazhongdianping.App.MyApp;
import com.liuchen.dazhongdianping.Entity.BusinessEntity;
import com.liuchen.dazhongdianping.Entity.CityEntity;
import com.liuchen.dazhongdianping.Entity.DistrictEntity;
import com.liuchen.dazhongdianping.Entity.TuanEntity;
import com.liuchen.dazhongdianping.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * 网络访问工具类
 * <p>
 * 符合大众点评服务器要求的地址：
 * http://网址部分?参数1=值1&参数2=值2....
 * <p>
 * http://api.dianping.com/v1/business/find_businesses?appkey=49814079&sign=生成的签名&city=%xx%xx%xx%xx%xx%xx&category=%xx%xx%xx%xx%xx%xx
 * <p>
 * 请求地址中签名的生成：
 * 利用Appkey,AppSecret,以及其他访问参数(例如city,category)
 * 首先将Appkey,AppSecret以及其他访问参数拼接成一个字符串
 * 例:49814079category美食city上海90e3438a41d646848033b6b9d461ed54
 * 将拼接好的字符串进行转码(转码算法为SHA1算法)
 * 转码后就得到了签名
 * <p>
 * Created by pjy on 2017/6/19.
 */

public class HttpUtil {
    public static final String APPKEY = "49814079";
    public static final String APPSECRET = "90e3438a41d646848033b6b9d461ed54";

    public static String getURL(String url, Map<String, String> params) {

        String result = "";

        String sign = getSign(APPKEY, APPSECRET, params);

        String query = getQuery(APPKEY, sign, params);

        result = url + "?" + query;

        return result;

    }

    public static String getSign(String appKey, String appSecret, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();

        // 对参数名进行字典排序
        String[] keyArray = params.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        // 拼接有序的参数名-值串
        stringBuilder.append(appKey);
        for (String key : keyArray) {
            stringBuilder.append(key).append(params.get(key));
        }
        String codes = stringBuilder.append(appSecret).toString();
        //纯JAVA环境中，利用Codec对字符串进行SHA1转码采用如下方式
        //String sign = org.apache.commons.codec.digest.DigestUtils.shaHex(codes).toUpperCase();
        //安卓环境中，利用Codec对字符串进行SHA1转码采用如下方式
        String sign = new String(Hex.encodeHex(DigestUtils.sha(codes))).toUpperCase();
        return sign;
    }

    public static String getQuery(String appKey, String sign, Map<String, String> params) {
        // 添加签名
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("appkey=").append(appKey).append("&sign=").append(sign);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                stringBuilder.append('&').append(entry.getKey()).append('=').append
                        (URLEncoder.encode(entry.getValue(), "utf-8"));
            }
            String queryString = stringBuilder.toString();

            return queryString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("使用了不正确的字符集名称");
        }
    }

    public static void testHttpURLConnection() {

        //获取符合大众点评要求的请求地址
        Map<String, String> params = new HashMap<String, String>();
        params.put("city", "广州");
        params.put("category", "美食");
        final String url = getURL("http://api.dianping.com/v1/business/find_businesses", params);
        Log.d("TAG", "生成的网络请求地址是：" + url);
        new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    URL u = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);//该方法可写可不写，因为默认就是true
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();
                    String response = sb.toString();
                    Log.d("TAG", "HttpURLConnection获得的服务器响应内容：" + response);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void testVolley() {
        VolleyClient.getInstance().test();
    }

    public static void testRetrofit() {
        RetrofitClient.getInstance().test();
    }

    public static void getDailyDealsByVolley(String city, Response.Listener<TuanEntity> listener) {
        VolleyClient.getInstance().getDailyDeals2(city, listener);
    }

    public static void getDailyDealsByRetrofit(String city, Callback<TuanEntity> callback) {
        RetrofitClient.getInstance().getDailyDeals3(city, callback);
    }

    public static void loadImage(String url, ImageView iv) {
        VolleyClient.getInstance().loadImage(url, iv);
    }

    public static void displayImage(String url, ImageView iv) {
        Picasso.with(MyApp.CONTEXT).load(url).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).
                placeholder(R.drawable.bucket_no_picture).error(R.drawable.error404).into(iv);
    }

    public static void getCitiesByRetrofit(Callback<CityEntity> callback) {
        RetrofitClient.getInstance().getCities(callback);
    }

    public static void getCitiesByVolley(Response.Listener<String> listener) {
        VolleyClient.getInstance().getCities(listener);
    }

    public static void getFoodsByVolley(String city, String region, Response.Listener<String> listener) {
        VolleyClient.getInstance().getFoods(city, region, listener);
    }

    public static void getFoodsByRetrofit(String city, String region, Callback<BusinessEntity> callback) {
        RetrofitClient.getInstance().getFoods(city, region, callback);
    }

    public static void getDistrictsByVolley(String city, Response.Listener<String> listener) {

        VolleyClient.getInstance().getDistricts(city, listener);

    }

    public static void getDistrictsByRetrofit(String city, Callback<DistrictEntity> callback) {
        RetrofitClient.getInstance().getDistricts(city, callback);
    }

    public static void getComment(final String url, final OnResponseListener<Document> listener) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    final Document document = Jsoup.connect(url).get();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onResponse(document);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public interface OnResponseListener<T> {
        void onResponse(T t);
    }

}
