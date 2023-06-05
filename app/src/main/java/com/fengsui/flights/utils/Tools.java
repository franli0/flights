package com.fengsui.flights.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class Tools {
    public final static String LOG_TAG = "fengsui";

    public static void setLocale(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        if(config.locale.equals(locale) && config.densityDpi == DisplayMetrics.DENSITY_XHIGH){
            return;
        }
        config.setLocale(locale);
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Log.d("dpi", displayMetrics.densityDpi+"");
        displayMetrics.densityDpi = DisplayMetrics.DENSITY_XHIGH;
        config.densityDpi = DisplayMetrics.DENSITY_XHIGH;
        resources.updateConfiguration(config, displayMetrics);
    }

    public static boolean isTw(Context context){
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        return config.locale.equals(Locale.TAIWAN) || config.locale.equals(Locale.CHINA);
    }

    public static void dumpIntent(Intent i){

        Bundle bundle = i.getExtras();
        dumpBundle(bundle);
    }

    public static void dumpBundle(Bundle bundle){

        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> it = keys.iterator();
            Log.e(LOG_TAG,"Dumping Intent start");
            while (it.hasNext()) {
                String key = it.next();
                Log.e(LOG_TAG,"[" + key + "=" + bundle.get(key)+"]");
            }
            Log.e(LOG_TAG,"Dumping Intent end");
        }
    }

    public static JSONArray loadJSONFromAsset(Context context, String name) {
        try {
            InputStream is = context.getAssets().open(name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer, "UTF-8"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new JSONArray();
    }

    public static void openWeb(Context context, String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.cloudmosa.puffinTV");
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }
}
