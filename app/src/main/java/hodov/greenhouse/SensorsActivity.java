package hodov.greenhouse;

import android.content.Context;
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

import android.widget.AdapterView;
import android.widget.GridView;
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
    static ArrayList<SensorController> keys = new ArrayList<SensorController>();
    static CustomListAdapter adapter;
    static GridView gridViewItems;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private static SectionsPagerAdapter mSectionsPagerAdapter;

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
            keys.clear();
            keys.addAll(createKeys(storage));
        } catch (JSONException e) {
            System.out.println(e);
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
            rootView.setTag("list");
            adapter = new CustomListAdapter(rootView.getContext(), keys.get(getArguments().getInt(ARG_SECTION_NUMBER)-1).relays);

            gridViewItems = (GridView) rootView.findViewById(R.id.grid_view);
            gridViewItems.setAdapter(adapter);

            gridViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    RelayAction action = new RelayAction(keys.get(getArguments().getInt(ARG_SECTION_NUMBER)-1).name, keys.get(getArguments().getInt(ARG_SECTION_NUMBER)-1).relays.get(position).name);
                    action.show(getFragmentManager(), "actions");
                    action.setActionResponseListener(new RelayAction.ActionResponseListener() {
                        @Override
                        public void onDataLoaded(Object data) {
                            final VolleyRequest volreq = new VolleyRequest(getActivity());
                            volreq.setResponseListener(new VolleyRequest.ResponseListener() {
                                @Override
                                public void onDataLoaded(JSONObject object) {
                                    System.out.println(object);
                                    storage = object;
                                    keys.clear();
                                    keys.addAll(createKeys(storage));
                                    //adapter.notifyDataSetChanged();
                                    mSectionsPagerAdapter.notifyDataSetChanged();


                                }
                            });
                            volreq.requestStorage();
                        }
                    });

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

        public int getItemPosition(Object object) {
            return POSITION_NONE;
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
            return keys.get(position).name;
        }
    }

    private static ArrayList<SensorController> createKeys(JSONObject storage) {
        ArrayList<SensorController> keys = new ArrayList<SensorController>();
        Iterator<String> iter = storage.keys();
        while (iter.hasNext()) {
            String key = iter.next();

            SensorController tempController = new SensorController(key);
            Iterator<String> iterInSensor = null;

            try {
                iterInSensor = storage.getJSONObject(key).getJSONObject("relays").keys();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            while (iterInSensor.hasNext()) {
                String relay = iterInSensor.next();
                Relay currRelay = GetSensorController(key, relay, storage);
                if (currRelay != null) {
                    tempController.relays.add(currRelay);
                }
            }
            keys.add(tempController);
        }
        return keys;
    }

    private static Relay GetSensorController(String controller, String relay, JSONObject storage) {
        String name = relay;
        String mode = "";
        int switcher = 0;
        int lowerBoundThreshold = 0;
        int upperBoundThreshold = 0;
        String value = "";

        Boolean relayIs = false;
        try {
            relayIs = storage.getJSONObject(controller).getJSONObject("relays").getJSONObject(relay).has("lowerBoundThreshold");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (relayIs) {
            try {
                mode = storage.getJSONObject(controller).getJSONObject("relays").getJSONObject(relay).getString("mode");
                if (storage.getJSONObject(controller).getJSONObject("relays").getJSONObject(relay).get("switcher") != null) {
                    switcher = storage.getJSONObject(controller).getJSONObject("relays").getJSONObject(relay).getInt("switcher");
                } else {
                    switcher = 0;
                }
                lowerBoundThreshold = storage.getJSONObject(controller).getJSONObject("relays").getJSONObject(relay).getInt("lowerBoundThreshold");
                upperBoundThreshold = storage.getJSONObject(controller).getJSONObject("relays").getJSONObject(relay).getInt("upperBoundThreshold");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            switch (relay) {
                case "heater" :  value = getAirTemperature(controller); break;
                case "cooler" :  value = getAirTemperature(controller); break;
                case "humidifier" :  value = getAirHumidity(controller); break;
                case "illuminator" :  value = getLight(controller); break;
                case "sprinkler" : value = getSoilHumidity(controller); break;
            }
            Relay tempRelay = new Relay(name, mode, switcher, value, lowerBoundThreshold, upperBoundThreshold);
            return tempRelay;
        }
        else {
            return null;
        }
    }

    private static String getAirTemperature(String controller) {
        try {
            return String.valueOf(storage.getJSONObject(controller).getJSONObject("sensors").getJSONObject("airTemperature").getInt("value"))+" \u2103";
        } catch (JSONException e) {
            e.printStackTrace();
            return "N/A";
        }
    }

    private static String getAirHumidity(String controller) {
        try {
            return String.valueOf(storage.getJSONObject(controller).getJSONObject("sensors").getJSONObject("airHumidity").getInt("value"))+" \u0025";
        } catch (JSONException e) {
            e.printStackTrace();
            return "N/A";
        }
    }

    private static String getSoilHumidity(String controller) {
        try {
            return String.valueOf(storage.getJSONObject(controller).getJSONObject("sensors").getJSONObject("soilHumidity").getInt("value"));
        } catch (JSONException e) {
            e.printStackTrace();
            return "N/A";
        }
    }

    private static String getLight(String controller) {
        try {
            return String.valueOf(storage.getJSONObject(controller).getJSONObject("sensors").getJSONObject("light").getInt("value"))+" lx";
        } catch (JSONException e) {
            e.printStackTrace();
            return "N/A";
        }
    }


}

