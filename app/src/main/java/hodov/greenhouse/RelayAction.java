package hodov.greenhouse;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Andrey on 04.08.2016.
 */
public class RelayAction extends DialogFragment {

    String controller;
    String relay;

    public RelayAction(String controller, String relay){
        this.controller = controller;
        this.relay = relay;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.relay_action_msg)
                .setItems(R.array.mode_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        String action;

                        switch (which) {
                            case 0: action = "auto"; break;
                            case 1: action = "on"; break;
                            case 2: action = "off"; break;
                            default: action = ""; break;
                        }

                        jsonPostRequest(controller,relay,action);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private void jsonPostRequest(String controller, String relay, String action) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = getActivity().getResources().getString(R.string.url_raspberry_android);
        JSONObject obj = new JSONObject();
        try {
            obj.put("controller", controller);
            obj.put("relay", relay);
            obj.put("position", action);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: get new storage


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

