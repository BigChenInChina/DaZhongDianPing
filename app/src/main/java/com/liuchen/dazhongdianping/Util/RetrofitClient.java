package com.liuchen.dazhongdianping.Util;


import android.text.TextUtils;
import android.util.Log;

import com.liuchen.dazhongdianping.Config.Constant;
import com.liuchen.dazhongdianping.Entity.BusinessEntity;
import com.liuchen.dazhongdianping.Entity.CityEntity;
import com.liuchen.dazhongdianping.Entity.DistrictEntity;
import com.liuchen.dazhongdianping.Entity.TuanEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class RetrofitClient {
    //1)声明一个私有的静态的属性
    private static RetrofitClient INSTANCE;

    //2)声明一个公有的静态的获取1)属性的方法
    public static RetrofitClient getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitClient();
                }
            }
        }
        return INSTANCE;
    }
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private NetService netService;

    private RetrofitClient(){
        okHttpClient = new OkHttpClient.Builder().addInterceptor(new MyOkHttpInterceptor()).build();
        retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(Constant.BASEURL).addConverterFactory
                (ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        netService = retrofit.create(NetService.class);
    }

    public void test(){
        Map<String,String> params = new HashMap<String,String>();
        params.put("city","广州");
        params.put("category","美食");
        final String sign = HttpUtil.getSign(HttpUtil.APPKEY,HttpUtil.APPSECRET,params);
        Call<String> call = netService.test(HttpUtil.APPKEY, sign, params);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String string = response.body();
                Log.d("TAG", "Retrofit获得的网络响应： "+string);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }

    public void getDailyDeals3(String city,final Callback<TuanEntity> callback2){
        final Map<String,String> params = new HashMap<String, String>();
        params.put("city",city);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        params.put("date",date);
        String sign = HttpUtil.getSign(HttpUtil.APPKEY,HttpUtil.APPSECRET,params);
        Call<String> idcall = netService.getDailyIds2(params);
        idcall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    JSONArray jsonArray =jsonObject.getJSONArray("id_list");

                    int size = jsonArray.length();
                    if (size > 40){
                        size = 40;
                    }

                    StringBuilder builder = new StringBuilder();

                    for (int i = 0; i < size; i++){
                        String id = jsonArray.getString(i);
                        builder.append(id).append(",");
                    }

                    if (builder.length() > 0){
                        String idlist = builder.substring(0,builder.length() - 1);

                        Map<String,String> params2 = new HashMap<String, String>();
                        params2.put("deal_ids",idlist);
                        Call<TuanEntity> dealCall = netService.getDailyDeals3(params2);
                        dealCall.enqueue(callback2);
                    }else {
                        callback2.onResponse(null,null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
            }
        });
    }

    public void getDailyDeals(String city,final Callback<String> callback2){
        final Map<String,String> params = new HashMap<String, String>();
        params.put("city",city);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        params.put("date",date);
        String sign = HttpUtil.getSign(HttpUtil.APPKEY,HttpUtil.APPSECRET,params);
        Call<String> ids = netService.getDailyIds(HttpUtil.APPKEY, sign, params);
        ids.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject jsonObject= new JSONObject(response.body());
                    JSONArray jsonArray = jsonObject.getJSONArray("id_list");

                    int size = jsonArray.length();
                    if(size>40){
                        size = 40;
                    }

                    StringBuilder sb = new StringBuilder();

                    for(int i=0;i<size;i++){
                        String id = jsonArray.getString(i);
                        sb.append(id).append(",");
                    }

                    if(sb.length()>0){

                        String idlist = sb.substring(0,sb.length()-1);
                        Map<String,String> params2 = new HashMap<String, String>();
                        params2.put("deal_ids",idlist);
                        String sign2 = HttpUtil.getSign(HttpUtil.APPKEY,HttpUtil.APPSECRET,params2);
                        Call<String> call2= netService.getDailyDeals(HttpUtil.APPKEY, sign2, params2);
                        call2.enqueue(callback2);
                    }else{
                        callback2.onResponse(null,null);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
            }
        });
    }
    public void getDailyDeals2(String city,final Callback<TuanEntity> callback2){
        final Map<String,String> params = new HashMap<String,String>();
        params.put("city",city);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        params.put("date",date);
        String sign = HttpUtil.getSign(HttpUtil.APPKEY,HttpUtil.APPSECRET,params);
        Call<String> ids = netService.getDailyIds(HttpUtil.APPKEY, sign, params);
        ids.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                try {
                    JSONObject jsonObject= new JSONObject(response.body());
                    JSONArray jsonArray = jsonObject.getJSONArray("id_list");

                    int size = jsonArray.length();
                    if(size>40){
                        size = 40;
                    }

                    StringBuilder sb = new StringBuilder();

                    for(int i=0;i<size;i++){
                        String id = jsonArray.getString(i);
                        sb.append(id).append(",");
                    }

                    if(sb.length()>0){

                        String idlist = sb.substring(0,sb.length()-1);
                        Map<String,String> params2 = new HashMap<String, String>();
                        params2.put("deal_ids",idlist);
                        String sign2 = HttpUtil.getSign(HttpUtil.APPKEY,HttpUtil.APPSECRET,params2);
                        Call<TuanEntity> call2= netService.getDailyDeals2(HttpUtil.APPKEY, sign2, params2);
                        call2.enqueue(callback2);
                    }else{
                        callback2.onResponse(null,null);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }

    public void getCities(Callback<CityEntity> callback){
        Call<CityEntity> call = netService.getCities();
        call.enqueue(callback);
    }
    public void getFoods(String city, String region, Callback<BusinessEntity> callback){

        Map<String,String> params = new HashMap<String,String>();
        params.put("city",city);
        params.put("category","美食");
        if(!TextUtils.isEmpty(region)){
            params.put("region",region);
        }
        Call<BusinessEntity> call = netService.getFoods(params);
        call.enqueue(callback);

    }
    public void getDistricts(String city, Callback<DistrictEntity> callback){

        Map<String,String> params = new HashMap<String,String>();
        params.put("city",city);
        Call<DistrictEntity> call = netService.getDistricts(params);
        call.enqueue(callback);

    }

    public class MyOkHttpInterceptor implements Interceptor{

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url = request.url();
            HashMap<String,String> params = new HashMap<String, String>();
            Set<String> set = url.queryParameterNames();
            for (String key:set){
                params.put(key,url.queryParameter(key));
            }
            String sign = HttpUtil.getSign(HttpUtil.APPKEY,HttpUtil.APPSECRET,params);
            String urlString = url.toString();
            Log.i("TAG","原始请求路径------> "+urlString);
            StringBuilder stringBuilder = new StringBuilder(urlString);
            if (set.size() == 0){
                stringBuilder.append("?");
            }else {
                stringBuilder.append("&");
            }
            stringBuilder.append("appkey=").append(HttpUtil.APPKEY);
            stringBuilder.append("&").append("sign=").append(sign);
            Log.i("TAG","新的请求路径------>: "+stringBuilder.toString());
            Request newRequest = new Request.Builder().url(stringBuilder.toString()).build();
            return chain.proceed(newRequest);
        }
    }
}
