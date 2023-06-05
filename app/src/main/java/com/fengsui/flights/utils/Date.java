package com.fengsui.flights.utils;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class Date {
    public int y = 2023;
    public int m = 0;
    public int d = 1;

    public Date(){
        Calendar calendar = Calendar.getInstance();
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);
        d = calendar.get(Calendar.DATE);
    }

    public Date(int y, int m, int d){
        this.y = y;
        this.d = d;
        this.m = m;
    }

    public boolean isSame(int y,int m, int d){
        return this.y == y && this.m == m && this.d == d;
    }

    public boolean isSame(Date date){
        return isSame(date.y, date.m, date.d);
    }

    public String getMonthText(){
        if(m<9){
            return "0"+(m+1);
        }
        return ""+(m+1);
    }

    public String getDayText(){
        if(d<10){
            return "0"+d;
        }
        return ""+d;
    }

    public void copy(Date date){
        this.y = date.y;
        this.d = date.d;
        this.m = date.m;
    }

    public boolean isBefore(Date date){
        return date.y>y || (date.y == y && date.m > m) || (date.y==y && date.m == m && date.d > d);
    }

    public boolean isAfter(Date date){
        return date.y<y || (date.y == y && date.m < m) || (date.y==y && date.m == m && date.d < d);
    }

    @NonNull
    @Override
    public String toString() {
        return y+"-"+getMonthText()+"-"+getDayText();
    }
}
