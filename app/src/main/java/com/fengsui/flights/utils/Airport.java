package com.fengsui.flights.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class Airport {
    public String name;
    public String en;
    public String code;

    public Airport(JSONObject obj){
        try {
            this.name = obj.getJSONObject("AirportName").getString("Zh_tw");
            this.en = obj.getJSONObject("AirportName").getString("En");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.code = obj.getString("AirportID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName(Context context) {
        if(Tools.isTw(context)){
            return name;
        }
        return en;
    }
}
