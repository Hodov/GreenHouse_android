package hodov.greenhouse;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class SensorsActivity extends AppCompatActivity {

    public static JSONObject storage;
    static ArrayList<String> keys = new ArrayList<String>();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        try {
            storage = new JSONObject(getIntent().getStringExtra("storage"));
            createKeys();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sensors, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_sensors, container, false);
            TextView textViewAirTemperature = (TextView) rootView.findViewById(R.id.section_label_temperature);
            TextView textViewAirHumidity = (TextView) rootView.findViewById(R.id.section_label_humidity);
            TextView textViewSoilHumidity = (TextView) rootView.findViewById(R.id.section_label_soil);
            TextView textViewLight = (TextView) rootView.findViewById(R.id.section_label_light);

            //TODO: Insert data from storage

            String airTemperature = getAirTemperature(getArguments().getInt(ARG_SECTION_NUMBER)-1);
            String airHumidity = getAirHumidity(getArguments().getInt(ARG_SECTION_NUMBER)-1);
            String soilHumidity = getSoilHumidity(getArguments().getInt(ARG_SECTION_NUMBER)-1);
            String light = getLight(getArguments().getInt(ARG_SECTION_NUMBER)-1);

            textViewAirTemperature.setText(airTemperature);
            textViewAirHumidity.setText(airHumidity);
            textViewSoilHumidity.setText(soilHumidity);
            textViewLight.setText(light);


            TextView heaterRelay = (TextView) rootView.findViewById(R.id.heater_relay);
            TextView coolerRelay = (TextView) rootView.findViewById(R.id.cooler_relay);
            TextView humidifierRelay = (TextView) rootView.findViewById(R.id.humidifier_relay);
            TextView illuminatorRelay = (TextView) rootView.findViewById(R.id.illuminator_relay);

            heaterRelay.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DialogFragment action = new RelayAction(keys.get(getArguments().getInt(ARG_SECTION_NUMBER)-1), "heater");
                    action.show(getFragmentManager(), "actions");
                    return false;
                }
            });
            coolerRelay.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DialogFragment action = new RelayAction(keys.get(getArguments().getInt(ARG_SECTION_NUMBER)-1), "cooler");
                    action.show(getFragmentManager(), "actions");
                    return false;
                }
            });
            humidifierRelay.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DialogFragment action = new RelayAction(keys.get(getArguments().getInt(ARG_SECTION_NUMBER)-1), "humidifier");
                    action.show(getFragmentManager(), "actions");
                    return false;
                }
            });
            illuminatorRelay.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DialogFragment action = new RelayAction(keys.get(getArguments().getInt(ARG_SECTION_NUMBER)-1), "illuminator");
                    action.show(getFragmentManager(), "actions");
                    return false;
                }
            });

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return storage.length();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return keys.get(position);
        }
    }

    public void requestStorage() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String urlRaspberry = this.getResources().getString(R.string.url_raspberry);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, urlRaspberry, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                storage = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);

            }
        });
        queue.add(jsonRequest);
    }

    private void createKeys() {
        Iterator<String> iter = storage.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            keys.add(key);
        }
    }

    private static String getAirTemperature(int position) {
        try {
            return String.valueOf(storage.getJSONObject(keys.get(position)).getJSONObject("sensors").getJSONObject("airTemperature").getInt("value"))+"\u2103";
        } catch (JSONException e) {
            e.printStackTrace();
            return "N/A";
        }
    }

    private static String getAirHumidity(int position) {
        try {
            return String.valueOf(storage.getJSONObject(keys.get(position)).getJSONObject("sensors").getJSONObject("airHumidity").getInt("value"))+"\u0025";
        } catch (JSONException e) {
            e.printStackTrace();
            return "N/A";
        }
    }

    private static String getSoilHumidity(int position) {
        try {
            return String.valueOf(storage.getJSONObject(keys.get(position)).getJSONObject("sensors").getJSONObject("soilHumidity").getInt("value"));
        } catch (JSONException e) {
            e.printStackTrace();
            return "N/A";
        }
    }

    private static String getLight(int position) {
        try {
            return String.valueOf(storage.getJSONObject(keys.get(position)).getJSONObject("sensors").getJSONObject("light").getInt("value"))+"lx";
        } catch (JSONException e) {
            e.printStackTrace();
            return "N/A";
        }
    }


}

