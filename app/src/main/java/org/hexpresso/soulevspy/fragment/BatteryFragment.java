package org.hexpresso.soulevspy.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hexpresso.soulevspy.R;

/**
 * Created by pemessier on 2015-10-01.
 */
public class BatteryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_battery, container, false);
    }
}
