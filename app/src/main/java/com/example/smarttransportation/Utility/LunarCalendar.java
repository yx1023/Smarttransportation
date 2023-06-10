package com.example.smarttransportation.Utility;

import java.util.Calendar;
import java.util.Date;

public class LunarCalendar {
    private static final long[] lunarInfo = new long[] {
            0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2, // ……
    };

    private int year;
    private int month;
    private int day;
    private boolean leap;
    private int leapMonth;

    public void computeChineseFields(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.leap = false;
        this.leapMonth = 0;

        long lunar = 0;
        long offset = 0;

        // 计算距离阳历1970年1月31日（农历正月初一）的天数
        Calendar cal = Calendar.getInstance();
        cal.set(1970, 0, 31);
        Date base = cal.getTime();
        offset = (day - base.getDate()) * 86400000;

        cal.set(year, month - 1, day);
        Date current = cal.getTime();
        offset = current.getTime() - base.getTime();

        for (int i = 0; i < lunarInfo.length; i++) {
            lunar = lunarInfo[i];
            int daysInMonth = (int) (lunar & 0x1F);
            if (year <= (lunar >> 9) && month <=((lunar >> 5) & 0x0F) ){
                break;
            }
            offset -= daysInMonth * 86400000;
        }

        offset += 8 * 86400000;

        int daySpan = (int) (offset / 86400000);

        for (int i = 0; i < lunarInfo.length; i++) {
            lunar = lunarInfo[i];
            int daysInMonth = (int) (lunar & 0x1F);
            int monthSpan = (int) ((lunar >> 5) & 0x0F);
            boolean isLeapMonth = ((lunar >> 4) & 0x01) == 1;
            if (isLeapMonth) {
                leapMonth = monthSpan;
            }
            int yearSpan = (int) (lunar >> 9);

            if (daySpan < daysInMonth) {
                break;
            }

            daySpan -= daysInMonth;

            if (monthSpan == leapMonth) {
                leap = true;
                if (daySpan >= daysInMonth) {
                    daySpan -= daysInMonth;
                } else {
                    month--;
                }
            }

            if (!leap && monthSpan == leapMonth + 1) {
                month--;
            }
            if (month < 0) {
                month = 12 + month;
                yearSpan--;
            }
        }

        this.year = (int) (year - lunarInfo[0] >> 9);
    }

    public String getChineseDateString() {
        String[] dayNames = new String[]{
                "零", "初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十", "十一", "十二", "十三",
                "十四", "十五", "十六", "十七", "十八", "十九", "二十", "廿一", "廿二", "廿三", "廿四",
                "廿五", "廿六", "廿七", "廿八", "廿九", "三十"
        };
        String[] monthNames = new String[]{
                "", "正月", "二月", "三月", "四月", "五月", "六月",
                "七月", "八月", "九月", "十月", "十一月", "十二月"
        };

        String result = "";

//        if (leap) {
//            result += "闰";
//            result += monthNames[leapMonth];
//        } else {
//            result += monthNames[month];
//        }
//
//        result += "月";
        result += dayNames[day];

        return result;
    }
}
