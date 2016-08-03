package hodov.greenhouse;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    public JSONObject storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Settings.getSetting("token") != null) {
            requestStorage();
            //TODO: прелоадер
        }
    }

    public void startNextActivity() {
        Intent intent = new Intent(this, SensorsActivity.class);
        intent.putExtra("storage", storage.toString());
        startActivity(intent);
    }

    public void requestStorage() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String urlRaspberry = this.getResources().getString(R.string.url_raspberry);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, urlRaspberry, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                storage = response;
                startNextActivity();
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
