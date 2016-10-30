package com.xiao.nanshi_check.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.model.StudentsBean;

import java.util.List;

/**
 * Created by zzzzz on 16/8/4/0004.
 */
///学生管理
public class StudentManagementRecylerAdapter extends RecyclerView.Adapter<StudentManagementRecylerAdapter.MyViewHolder> {

    private Context context;
    private List<StudentsBean> list;
    private Resources res;

//    private OnItemClickListener mOnItemClickListener = null;//点击;
//    public OnItemLongClickListener mOnLongItemClickListener = null;//长按


    private List<Boolean> listCheck;
    public  boolean isShow=false;


    public StudentManagementRecylerAdapter(Context context, List<StudentsBean> list,List<Boolean> listCheck) {
        this.context = context;
        this.list = list;

        this.listCheck=listCheck;

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

        if(isShow){
            holder.checkBox.setVisibility(View.VISIBLE);
        }else {
            holder.checkBox.setVisibility(View.GONE);
        }

//        /**
//         * 调用接口回调
//         */
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mOnItemClickListener)
//                    mOnItemClickListener.onItemClick(position, bean);
//            }
//        });
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (mOnLongItemClickListener != null) {
//                    mOnLongItemClickListener.OnItemLongClick(position, bean);
//                }
//                return true;
//            }
//        });

//        holder.itemView.setonCheckedChanged()
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener,CompoundButton.OnCheckedChangeListener {
        private TextView id;
        private TextView grade;
        private TextView name;
        private LinearLayout rootView;
        private CheckBox checkBox;
        private int position;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView= (LinearLayout) itemView.findViewById(R.id.item_root);
            id = (TextView) itemView.findViewById(R.id.tv_id);
            grade = (TextView) itemView.findViewById(R.id.tv_grade);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            checkBox= (CheckBox) itemView.findViewById(R.id.item_checkbox);

            checkBox.setOnCheckedChangeListener(this);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener!=null){
                if(checkBox.isChecked()){
                    checkBox.setChecked(false);
                    onItemClickListener.setOnItemClick(position,false);
                }else {
                    checkBox.setChecked(true);
                    onItemClickListener.setOnItemClick(position,true);
                }
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if(onItemClickListener!=null){
                return onItemClickListener.setOnItemLongClick(position);
            }
            return false;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if(onItemClickListener!=null){
                onItemClickListener.setOnItemCheckedChanged(position,isChecked);
            }
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public interface OnItemClickListener {
        void setOnItemClick(int position, boolean isCheck);
        boolean setOnItemLongClick(int position);
        void setOnItemCheckedChanged(int position, boolean isCheck);
    }
//    /**
//     * 内部接口回调方法
//     */
//    public interface OnItemClickListener {
//        void onItemClick(int position, Object object);
//    }
//
//    /**
//     * 内部接口回调方法
//     */
//    public interface OnItemLongClickListener {
//        void OnItemLongClick(int position, Object object);
//    }
//
//    /**
//     * 设置监听方法
//     *
//     * @param listener
//     */
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.mOnItemClickListener = listener;
//    }
//    /**
//     * 设置监听方法
//     *
//     * @param listener
//     */
//    public void OnItemLongClickListener(OnItemLongClickListener listener) {
//        this.mOnLongItemClickListener = listener;
//    }
}


