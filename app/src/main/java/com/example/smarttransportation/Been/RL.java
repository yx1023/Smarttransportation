package com.example.smarttransportation.Been;

public class RL {
    private String solar,lunar;
    private int bg;
    private int month;
    private boolean enable;

    public RL(String solar, String lunar, int bg, int month, boolean enable) {
        this.solar = solar;
        this.lunar = lunar;
        this.bg = bg;
        this.month = month;
        this.enable = enable;
    }

    public String getSolar() {
        return solar;
    }

    public void setSolar(String solar) {
        this.solar = solar;
    }

    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
