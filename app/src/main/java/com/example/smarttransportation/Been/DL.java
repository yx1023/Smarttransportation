package com.example.smarttransportation.Been;

import com.google.gson.annotations.SerializedName;

public class DL {
    private Integer roadId;
    @SerializedName("Name")
    private String name;
    private Integer state;

    public Integer getRoadId() {
        return roadId;
    }

    public void setRoadId(Integer roadId) {
        this.roadId = roadId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
