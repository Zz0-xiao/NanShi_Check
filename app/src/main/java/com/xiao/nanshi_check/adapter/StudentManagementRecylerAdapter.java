package com.xiao.nanshi_check.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.model.ModelBean;
import com.xiao.nanshi_check.model.StudentsBean;

import java.util.List;

/**
 * Created by zzzzz on 16/8/4/0004.
 */

public class StudentManagementRecylerAdapter extends RecyclerView.Adapter<StudentManagementRecylerAdapter.MyViewHolder> {

    private Context context;
    private List<StudentsBean> list;
    private Resources res;
    private OnItemClickListener listener;


    public StudentManagementRecylerAdapter(Context context, List<StudentsBean> list) {
        this.context = context;
        this.list = list;
        res = context.getResources();
    }

    @Override
    public StudentManagementRecylerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student_management, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentManagementRecylerAdapter.MyViewHolder holder, final int position) {
        final StudentsBean bean = list.get(position);
        holder.id.setText(bean.getId());
        holder.grade.setText(bean.getGrade());
        holder.name.setText(bean.getName());

        /**
         * 调用接口回调
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onItemClick(position, bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView grade;
        private TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.tv_id);
            grade = (TextView) itemView.findViewById(R.id.tv_grade);
            name = (TextView) itemView.findViewById(R.id.tv_name);

        }
    }

    /**
     * 内部接口回调方法
     */
    public interface OnItemClickListener {
        void onItemClick(int position, Object object);
    }

    /**
     * 设置监听方法
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}


