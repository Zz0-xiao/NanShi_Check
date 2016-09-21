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

import java.util.List;

/**
 * Created by zzzzz on 16/8/5/0005.
 */
public class EquipmentRecylerAdapter extends RecyclerView.Adapter<EquipmentRecylerAdapter.MyViewHolder> {

    private Context context;
    private List<EquipmentBean> list;
    private Resources res;
//    private OnItemClickListener listener;

    public EquipmentRecylerAdapter(Context context, List<EquipmentBean> list) {
        this.context = context;
        this.list = list;
        res = context.getResources();
    }

    @Override
    public EquipmentRecylerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_equipment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EquipmentRecylerAdapter.MyViewHolder holder, final int position) {

        final EquipmentBean bean = list.get(position);
        holder.equipmentIp.setText(bean.getEquipmentIp());
        holder.equipmentName.setText(bean.getEquipmentName());
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView equipmentIp;
        private TextView equipmentName;

        public MyViewHolder(View itemView) {
            super(itemView);
            equipmentIp = (TextView) itemView.findViewById(R.id.tv_equipment_ip);
            equipmentName = (TextView) itemView.findViewById(R.id.tv_equipment_name);
        }
    }
}
