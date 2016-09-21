package com.xiao.nanshi_check.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.activity.MainActivity;
import com.xiao.nanshi_check.adapter.EquipmentRecylerAdapter;
import com.xiao.nanshi_check.adapter.RecyclerAdapter;
import com.xiao.nanshi_check.behavior.ScaleDownShowBehavior;
import com.xiao.nanshi_check.model.EquipmentBean;
import com.xiao.nanshi_check.model.ModelBean;
import com.xiao.nanshi_check.model.StudentsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentFragment extends Fragment {
    private View view;
    //    private String content;
    private RecyclerView recyclerView;

    private List<EquipmentBean> beanList;
    private EquipmentRecylerAdapter adapter;

    private FloatingActionButton fab;

    public EquipmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_equipment, container, false);



        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        content = getArguments().getString("content");
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        fab = (FloatingActionButton) view.findViewById(R.id.fab);
//        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab2);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "添加设备", Toast.LENGTH_SHORT).show();
//            }
//        });
        MainActivity parentActivity = (MainActivity) getActivity();
        parentActivity.invisible();


        initData();
    }

    private void initData() {
        beanList = new ArrayList<>();
        for (int i = 0; i < 130; i++) {
            EquipmentBean bean = new EquipmentBean();
            bean.setEquipmentIp("192.168.0." + i);
            bean.setEquipmentName("x62机床" + i);
            beanList.add(bean);
        }
        adapter = new EquipmentRecylerAdapter(getActivity(), beanList);
        recyclerView.setAdapter(adapter);
       /* adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
//                startActivity(new Intent(getActivity(), TwoActivity.class));
            }
        });*/

      /*  ScaleDownShowBehavior scaleDownShowFab = ScaleDownShowBehavior.from(fab);
        scaleDownShowFab.setOnStateChangedListener(onStateChangedListener);*/


    }


   /* private ScaleDownShowBehavior.OnStateChangedListener onStateChangedListener = new ScaleDownShowBehavior.OnStateChangedListener() {
        @Override
        public void onChanged(boolean isShow) {

        }
    };*/
}
