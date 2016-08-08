package hodov.greenhouse;

import android.content.Context;
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

            // get the TextView for item name and item description
            TextView textViewItemValue = (TextView)
                    convertView.findViewById(R.id.text_view_item_value);
            TextView textViewItemLower = (TextView)
                    convertView.findViewById(R.id.text_view_item_lower);
            TextView textViewItemHigh = (TextView)
                    convertView.findViewById(R.id.text_view_item_high);

            //sets the text for item name and item description from the current item object
            textViewItemValue.setText(currentItem.value);
            textViewItemLower.setText(currentItem.lowerBoundThreshold);
            textViewItemHigh.setText(currentItem.upperBoundThreshold);
            // returns the view for the current row
            return convertView;
        }

}


