package com.liuchen.dazhongdianping.Util;

import com.liuchen.dazhongdianping.Entity.CityEntity;
import com.liuchen.dazhongdianping.Entity.TuanEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/19 0019.
 */
public interface NetService {
    @GET("business/find_businesses")
    public Call<String> test(@Query("appkey")String appkey,
         @Query("sign") String sign,
         @QueryMap Map<String,String> params);
    @GET("deal/get_daily_new_id_list")
    public Call<String> getDailyIds(@Query("appkey") String appkey,
                                    @Query("sign") String sign,
                                    @QueryMap Map<String,String> params);
    @GET("deal/get_daily_new_id_list")
    public Call<String> getDailyIds2(@QueryMap Map<String,String> params);
    @GET("deal/get_batch_deals_by_id")
    public Call<String> getDailyDeals(@Query("appkey") String appkey,
                                      @Query("sign") String sign,
                                      @QueryMap Map<String,String> params);
    @GET("deal/get_batch_deals_by_id")
    public Call<TuanEntity>
    getDailyDeals2(@Query("appkey") String appkey,
                   @Query("sign") String sign,
                   @QueryMap Map<String,String> params);
    @GET("deal/get_batch_deals_by_id")
    public Call<TuanEntity>
    getDailyDeals3(@QueryMap Map<String,String> params);

    @GET("metadata/get_cities_with_businesses")
    public Call<CityEntity> getCities();

}
