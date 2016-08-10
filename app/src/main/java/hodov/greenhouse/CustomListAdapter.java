package hodov.greenhouse;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by skorokhodov_a on 08.08.2016.
 */
public class CustomListAdapter extends BaseAdapter {


        private Context context; //context
        private ArrayList <Relay> items = new ArrayList<>(); //data source of the list adapter

        //public constructor
        public CustomListAdapter(Context context, ArrayList <Relay> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size(); //returns total of items in the list
        }

        @Override
        public Object getItem(int position) {
            return items.get(position); //returns list item at the specified position
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // inflate the layout for each list row
            if (convertView == null) {
                convertView = LayoutInflater.from(context).
                        inflate(R.layout.list_item, parent, false);
            }

            // get current item to be displayed
            Relay currentItem = (Relay) getItem(position);

            // get the TextView for item name and item description
            TextView textViewItemName = (TextView)
                    convertView.findViewById(R.id.text_view_item_name);
            TextView textViewItemValue = (TextView)
                    convertView.findViewById(R.id.text_view_item_value);
            TextView textViewItemLower = (TextView)
                    convertView.findViewById(R.id.text_view_item_lower);
            TextView textViewItemHigh = (TextView)
                    convertView.findViewById(R.id.text_view_item_high);
            TextView textViewItemSwitcher = (TextView)
                    convertView.findViewById(R.id.text_view_item_switcher);
            TextView textViewItemMode = (TextView)
                    convertView.findViewById(R.id.text_view_item_mode);

            //sets the text for item name and item description from the current item object
            textViewItemName.setText(currentItem.name);
            textViewItemValue.setText(currentItem.value);
            textViewItemLower.setText(String.valueOf(currentItem.lowerBoundThreshold));
            textViewItemHigh.setText(String.valueOf(currentItem.upperBoundThreshold));

            String switcher = "off";
            switch (currentItem.switcher) {
                case 0: switcher = "off"; break;
                case 1: switcher = "on"; break;
            }
            textViewItemSwitcher.setText(switcher);

            if (currentItem.mode.equals("auto") ) {
                textViewItemMode.setText(currentItem.mode);
            } else {
                textViewItemMode.setText("");
            }

            switch (currentItem.name) {
                case "cooler" : convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorCooler)); break;
                case "heater" : convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorHeater)); break;
                case "humidifier" : convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorHumidifier)); break;
                case "illuminator" : convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorIlluminator)); break;
                case "sprinkler" : convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorSprinkler)); break;
            }

            // returns the view for the current row
            return convertView;
        }

}


