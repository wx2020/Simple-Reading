package cn.wx2020.simplereadingdemo;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class NewsJsonUtils {

    public static void refreshNewsJson(final RequestQueue mQueue,
                                       final String url,
                                       final VolleyCallback callback){
        initNewsJson(mQueue, url, callback);
    }

    public static void initNewsJson(RequestQueue mQueue, String url, final VolleyCallback callback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) { callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage(), error);
            }
        });
        mQueue.add(jsonObjectRequest);

    }

    public interface VolleyCallback {
        void onSuccess(JSONObject result);
    }
}
