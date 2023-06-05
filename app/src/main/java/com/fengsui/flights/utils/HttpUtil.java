package com.fengsui.flights.utils;

import android.content.Context;
import androidx.annotation.Nullable;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
    private final static String TAG = HttpUtil.class.getSimpleName();
    private RequestQueue queue;
    private Context mContext;

    public HttpUtil(Context context){
        mContext = context;
        initQueue();
    }

    public interface Callback {
        void onHttpResponse(String response);
        void onHttpError(VolleyError error);
    }

    public static class FsiRequest extends StringRequest {
        public FsiRequest(int method,String url, Response.Listener<String> listener,
                          Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
            if(url.startsWith("https:")){
                SsX509TrustManager.allowAllSSL();
            }
            setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }

    }

    public static class PostRequest extends FsiRequest {

        public PostRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        @Nullable
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            final Map<String, String> params = new HashMap<>();
            return params;
        }
    }

    private void initQueue() {
        Cache cache = new DiskBasedCache(mContext.getCacheDir(), 2 * 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        queue = new RequestQueue(cache, network);
        queue.start();
    }

    public void addRequest(FsiRequest request) {
        queue.add(request);
    }

    public void get(String uri, Callback callback) {
        queue.add(new FsiRequest(Request.Method.GET, uri ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(callback != null){
                            callback.onHttpResponse(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if(callback != null){
                    callback.onHttpError(error);
                }
            }
        }));
    }

    public void post(String uri, Callback callback){
        queue.add(new PostRequest(Request.Method.POST, uri ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(callback != null){
                            callback.onHttpResponse(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if(callback != null){
                    callback.onHttpError(error);
                }
            }
        }));
    }
}
