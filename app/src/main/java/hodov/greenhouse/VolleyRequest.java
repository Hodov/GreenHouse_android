package hodov.greenhouse;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

/**
 * Created by skorokhodov_a on 10.08.2016.
 */
public class VolleyRequest {

    public interface ResponseListener {

        public void onDataLoaded(JSONObject object);
    }

    Context context;
    private ResponseListener listener;

    public VolleyRequest(Context context){
        this.context = context;
        this.listener = null;
    }

    public void setResponseListener(ResponseListener listener) {
        this.listener=listener;
    }


    public void requestStorage() {
        RequestQueue queue = Volley.newRequestQueue(context);
        String urlRaspberry = context.getResources().getString(R.string.url_raspberry);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, urlRaspberry, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (listener != null)
                    listener.onDataLoaded(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);

            }
        });
        queue.add(jsonRequest);
    }
}
