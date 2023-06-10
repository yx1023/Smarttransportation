package com.example.smarttransportation.Been;

public class RLD {
    private int id;
    private int number;
    private int red;
    private int yellow;
    private int green;
    private boolean enable;
    private int redH, redV;
    private int yellowH, yellowV;
    private int greenH, greenV;
    private String horizontal, vertical;

    public RLD() {
        redH = red;
        redV = red;
        greenV = green;
        greenH = green;
        yellowH = yellow;
        yellowV = yellow;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getRedH() {
        return redH;
    }

    public void setRedH(int redH) {
        this.redH = redH;
    }

    public int getRedV() {
        return redV;
    }

    public void setRedV(int redV) {
        this.redV = redV;
    }

    public int getYellowH() {
        return yellowH;
    }

    public void setYellowH(int yellowH) {
        this.yellowH = yellowH;
    }

    public int getYellowV() {
        return yellowV;
    }

    public void setYellowV(int yellowV) {
        this.yellowV = yellowV;
    }

    public int getGreenH() {
        return greenH;
    }

    public void setGreenH(int greenH) {
        this.greenH = greenH;
    }

    public int getGreenV() {
        return greenV;
    }

    public void setGreenV(int greenV) {
        this.greenV = greenV;
    }

    public String getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(String horizontal) {
        this.horizontal = horizontal;
    }

    public String getVertical() {
        return vertical;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }
}
