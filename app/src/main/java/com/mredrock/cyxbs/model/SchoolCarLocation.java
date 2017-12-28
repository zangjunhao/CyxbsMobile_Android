package com.mredrock.cyxbs.model;

import java.util.List;

/**
 * Created by glossimar on 2017/12/25.
 */

public class SchoolCarLocation {
    private String status;
    private String info;
    private List<Data> data;
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public String getInfo() {
        return info;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }

    public class Data {
        private double lat;
        private double lon;
        private double speed;
        private int id;
        public void setLat(double lat) {
            this.lat = lat;
        }
        public double getLat() {
            return lat;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
        public double getLon() {
            return lon;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }
        public double getSpeed() {
            return speed;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

    }
}