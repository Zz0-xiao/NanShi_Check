package com.xiao.nanshi_check.adapter;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.activity.StudentManagementActivity;
import com.xiao.nanshi_check.model.StudentsBean;

import java.util.List;
import java.util.Set;

/**
 * Created by zzzzz on 16/8/4/0004.
 */
///学生管理
public class StudentManagementRecylerAdapter extends RecyclerView.Adapter<StudentManagementRecylerAdapter.MyViewHolder> {

    private Context context;
    private List<StudentsBean> list;

    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public StudentManagementRecylerAdapter(Context context, List<StudentsBean> list) {
        this.context = context;
        this.list = list;

        inflater = LayoutInflater.from(context);
    }


    @Override
    public StudentManagementRecylerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_student_management, parent, false);
//        StudentManagementRecylerAdapter holder = new StudentManagementRecylerAdapter(view);
        return new MyViewHolder(view);
//        return holder;
    }

    @Override
    public void onBindViewHolder(StudentManagementRecylerAdapter.MyViewHolder holder, final int position) {
        final StudentsBean bean = list.get(position);
        holder.id.setText(bean.getId());
        holder.grade.setText(bean.getGrade());
        holder.name.setText(bean.getName());

        holder.setData(list.get(position),position);

        if (onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(v,position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }


    public void remove(StudentsBean str){
        list.remove(str);
        notifyDataSetChanged();
    }

    public StudentsBean getItem(int pos){
        return list.get(pos);
    }




    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view,int position);
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

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public void setData(StudentsBean item, int position){
            Set<Integer> positionSet = StudentManagementActivity.positionSet;
            if (positionSet.contains(position)) {
//                itemView.setBackground(StudentManagementActivity.getResources().getDrawable(R.drawable.bg_selected));
                itemView.setBackground(context.getResources().getDrawable(R.drawable.bg_selected));
//                itemView.setBackground();
            } else {
                itemView.setBackground(context.getResources().getDrawable(R.drawable.btn_common));
            }
//            name.setText(item);
        }
    }
}


