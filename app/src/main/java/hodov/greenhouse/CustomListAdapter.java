package hodov.greenhouse;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
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
        private ArrayList <Relay> items; //data source of the list adapter

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

//            if (currentItem.switcher == 0) {
//                convertView.setBackgroundColor(0xFFE57373);
//            } else {
//                convertView.setBackgroundColor(0xFF81C784);
//            }
            // get the TextView for item name and item description
            TextView textViewItemName = (TextView)
                    convertView.findViewById(R.id.text_view_item_name);
            TextView textViewItemValue = (TextView)
                    convertView.findViewById(R.id.text_view_item_value);
            TextView textViewItemLower = (TextView)
                    convertView.findViewById(R.id.text_view_item_lower);
            TextView textViewItemHigh = (TextView)
                    convertView.findViewById(R.id.text_view_item_high);

            //sets the text for item name and item description from the current item object
            textViewItemName.setText(currentItem.name);
            textViewItemValue.setText(currentItem.value);
            textViewItemLower.setText(String.valueOf(currentItem.lowerBoundThreshold));
            textViewItemHigh.setText(String.valueOf(currentItem.upperBoundThreshold));



            switch (currentItem.name) {
                case "cooler" : convertView.setBackgroundColor(Color.parseColor("#4DD0E1")); break;
                case "heater" : convertView.setBackgroundColor(Color.parseColor("#FFB74D")); break;
                case "humidifier" : convertView.setBackgroundColor(Color.parseColor("#81C784")); break;
                case "illuminator" : convertView.setBackgroundColor(Color.parseColor("#e57373")); break;
                case "sprinkler" : convertView.setBackgroundColor(Color.parseColor("#90CAF9")); break;
            }

            // returns the view for the current row
            return convertView;
        }

}


