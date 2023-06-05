package com.fengsui.flights.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class Ad {
    public String name;
    public String en;
    public String code;

    public Ad(JSONObject obj){
        try {
            this.name = obj.getJSONObject("ADName").getString("Zh_tw");
            this.en = obj.getJSONObject("ADName").getString("En");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.code = obj.getString("ADID");
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
