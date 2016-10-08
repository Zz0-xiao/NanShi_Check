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
import android.widget.Toast;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.adapter.ResultsRecylerAdapter;
import com.xiao.nanshi_check.model.StudentsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
//成绩管理
public class ResultsFragment extends Fragment {
    private View view;
    //    private String content;
    private RecyclerView recyclerView;

    private List<StudentsBean> beanList;
    private ResultsRecylerAdapter adapter;

    public ResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_resultsfragment, container, false);
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
//        ((MainActivity) getActivity()).invisible();//隐藏

    /*    FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "成绩管理", Toast.LENGTH_SHORT).show();
            }
        });*/
        initData();
    }

    private void initData() {
        beanList = new ArrayList<>();
        for (int i = 0; i < 130; i++) {
            StudentsBean bean = new StudentsBean();
            bean.setId("学号:00" + i);
            beanList.add(bean);
        }
        adapter = new ResultsRecylerAdapter(getActivity(), beanList);
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
