package com.xiao.nanshi_check.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.model.EquipmentBean;
import com.xiao.nanshi_check.model.StudentsBean;

import java.util.List;

/**
 * Created by zzzzz on 16/8/5/0005.
 */

//成绩管理
public class ResultsRecylerAdapter extends RecyclerView.Adapter<ResultsRecylerAdapter.MyViewHolder> {

    private Context context;
    private List<StudentsBean> list;
    private Resources res;
//    private OnItemClickListener listener;

    public ResultsRecylerAdapter(Context context, List<StudentsBean> list) {
        this.context = context;
        this.list = list;
        res = context.getResources();
    }

    @Override
    public ResultsRecylerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resultsfragment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultsRecylerAdapter.MyViewHolder holder, final int position) {

        final StudentsBean bean = list.get(position);
        holder.tvId.setText(bean.getId());

    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tv_id);
        }
    }
}
