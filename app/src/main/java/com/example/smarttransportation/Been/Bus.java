package com.example.smarttransportation.Been;

import java.util.List;

public class Bus {
    private Integer id;
    private List<String> busline;
    private Integer fares;
    private Integer mileage;
    private String time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getBusline() {
        return busline;
    }

    public void setBusline(List<String> busline) {
        this.busline = busline;
    }

    public Integer getFares() {
        return fares;
    }

    public void setFares(Integer fares) {
        this.fares = fares;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
