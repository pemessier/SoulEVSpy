package org.hexpresso.soulevspy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.hexpresso.soulevspy.R;
import org.hexpresso.soulevspy.util.KiaVinParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pemessier on 2015-10-07.
 */
public class CarFragment extends ListFragment {

    private List<ListViewItem> mItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle(R.string.action_car_information);

        KiaVinParser vin = new KiaVinParser(getContext(), "KNDJX3AEXG7123456");

        mItems.add(new ListViewItem("Vehicle Identification Number", vin.getVIN()));
        mItems.add(new ListViewItem("Brand", vin.getBrand()));
        mItems.add(new ListViewItem("Model", vin.getModel()));
        mItems.add(new ListViewItem("Trim", vin.getTrim()));
        mItems.add(new ListViewItem("Engine", vin.getEngine()));
        mItems.add(new ListViewItem("Year", vin.getYear()));
        mItems.add(new ListViewItem("Sequential Number", vin.getSequentialNumber()));
        mItems.add(new ListViewItem("Production Plant", vin.getProductionPlant()));

        // initialize and set the list adapter
        setListAdapter(new ListViewDemoAdapter(getActivity(), mItems));
    }
/*
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve theListView item
        ListViewItem item = mItems.get(position);

        // do something
        Toast.makeText(getActivity(), item.title, Toast.LENGTH_SHORT).show();
    }
*/
}

class ListViewItem {
    public final String title;
    public final String value;

    public ListViewItem(String title, String value) {
        this.title = title;
        this.value = value;
    }
}

class ListViewDemoAdapter extends ArrayAdapter<ListViewItem> {

    public ListViewDemoAdapter(Context context, List<ListViewItem> items) {
        super(context, R.layout.listview_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvValue = (TextView) convertView.findViewById(R.id.tvValue);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        ListViewItem item = getItem(position);
        viewHolder.tvTitle.setText(item.title);
        viewHolder.tvValue.setText(item.value);

        return convertView;
    }

    /**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     *
     * @see http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
     */
    private static class ViewHolder {
        TextView tvTitle;
        TextView tvValue;
    }
}