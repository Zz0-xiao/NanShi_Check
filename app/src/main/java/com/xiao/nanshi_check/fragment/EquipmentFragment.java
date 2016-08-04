package com.xiao.nanshi_check.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiao.nanshi_check.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentFragment extends Fragment {
    private View view;

    public EquipmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_equipment, container, false);
        return view;
    }

}
