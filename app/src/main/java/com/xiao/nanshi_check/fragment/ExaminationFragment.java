package com.xiao.nanshi_check.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.activity.MainActivity;
import com.xiao.nanshi_check.adapter.RecyclerAdapter;
import com.xiao.nanshi_check.model.ModelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExaminationFragment extends Fragment {

    private String content;
    private View view;
    private RecyclerView recyclerView;

    private List<ModelBean> beanList;
    private RecyclerAdapter adapter;

    private String des[] = {
            "逗逼你好1", "逗逼你好2", "逗逼你好3",
            "逗逼你好4", "逗逼你好5", "逗逼你好6",
            "逗逼你好7", "逗逼你好8", "逗逼你好9",};

    private int resId[] = {
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,};

    public ExaminationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText("成绩");
//        return textView;
        view = inflater.inflate(R.layout.item, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        content = getArguments().getString("content");
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        initData();
    }


    private void initData() {
        beanList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ModelBean bean = new ModelBean();
            bean.setResId(resId[i]);
            bean.setTitle(des[i]);
            beanList.add(bean);
        }
        adapter = new RecyclerAdapter(getActivity(), beanList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
//                startActivity(new Intent(getActivity(), TwoActivity.class));
            }
        });
    }
}

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("考试");


        return textView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        ((MainActivity)getActivity()).visible();//显示

    }
}
*/