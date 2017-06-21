package com.liuchen.dazhongdianping.Entity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class CityEntity {
    String status;
    List<String> cities;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "status='" + status + '\'' +
                ", cities=" + cities +
                '}';
    }
}
