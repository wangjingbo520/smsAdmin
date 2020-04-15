package com.tools.smsadmin.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tools.smsadmin.R;
import com.tools.smsadmin.model.UserInfos;

import java.util.List;

/**
 * @author wjb（C）
 * describe
 */
public class UserListAdapter extends BaseAdapter {
    private List<UserInfos.DataBean> list;
    private Context context;

    private IResetInterface iResetInterface;

    public interface IResetInterface {
        void onReset(int position);
    }


    public UserListAdapter(List<UserInfos.DataBean> list, Context context, IResetInterface iResetInterface) {
        this.list = list;
        this.context = context;
        this.iResetInterface = iResetInterface;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public UserInfos.DataBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup parent) {
        ViewHolder holder;
        if (arg1 == null) {
            holder = new ViewHolder();
            arg1 = View.inflate(context, R.layout.list_item_user, null);
            holder.tv_username = arg1.findViewById(R.id.tv_username);
            holder.tv_openning = arg1.findViewById(R.id.tv_openning);
            holder.tv_register_time = arg1.findViewById(R.id.tv_register_time);
            holder.tv_remaining_day = arg1.findViewById(R.id.tv_remaining_day);
            holder.tv_reset = arg1.findViewById(R.id.tv_reset);
            arg1.setTag(holder);
        } else {
            holder = (ViewHolder) arg1.getTag();
        }

        holder.tv_username.setText("账号:  " + list.get(position).getUsername());
        holder.tv_register_time.setText("注册日期: " + list.get(position).getRegister_date());

        int openning = list.get(position).getOpening();
        if (openning == 0) {
            holder.tv_openning.setText("未开通");
            holder.tv_openning.setTextColor(context.getResources().getColor(R.color.red));
        } else {
            holder.tv_openning.setText("已开通");
            holder.tv_remaining_day.setText("(剩" + list.get(position).getRemaining_day() + "天)");
            //  holder.tv_openning.setTextColor(context.getResources().getColor(R.color.theme_color));
        }

        holder.tv_reset.setOnClickListener(v -> {
            if (iResetInterface != null) {
                iResetInterface.onReset(position);
            }
        });


        return arg1;
    }

    private static class ViewHolder {
        private TextView tv_username;
        private TextView tv_register_time;
        private TextView tv_openning;
        private TextView tv_remaining_day;
        private TextView tv_reset;
    }

}
