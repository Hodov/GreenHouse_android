package hodov.greenhouse;


import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by Andrey on 02.08.2016.
 */
public class Storage {

    public JSONObject storage;
    public Context context;

    public Storage(Context context) {
        this.context = context;
    }

    public void saveStorage(JSONObject response) {
        this.storage = response;
    }

    public void requestStorage(Context context) {
        System.out.println("Inside request");
        RequestQueue queue = Volley.newRequestQueue(context);
        String urlRaspberry = context.getResources().getString(R.string.url_raspberry);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, urlRaspberry, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Get response");
                saveStorage(response);
            }
        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error);
                    //do your stuff on error
                }
        });

        queue.add(jsonRequest);
    }

}
