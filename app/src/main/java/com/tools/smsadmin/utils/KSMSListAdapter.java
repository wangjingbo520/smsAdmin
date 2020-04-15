package com.tools.smsadmin.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tools.smsadmin.R;
import com.tools.smsadmin.model.KSMSBean;
import com.tools.smsadmin.model.UserInfos;

import java.util.List;

/**
 * @author wjb（C）
 * describe
 */
public class KSMSListAdapter extends BaseAdapter {
    private List<KSMSBean.DataBean> list;
    private Context context;


    public interface IResetInterface {
        void onReset(int position);
    }


    public KSMSListAdapter(Context context, List<KSMSBean.DataBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public KSMSBean.DataBean getItem(int position) {
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
            arg1 = View.inflate(context, R.layout.list_item_ksms, null);
            holder.username = arg1.findViewById(R.id.tv_username);
            holder.remaining_size = arg1.findViewById(R.id.remaining_size);
            holder.uploadFailedSize = arg1.findViewById(R.id.uploadFailedSize);
            holder.uploadSucessSize = arg1.findViewById(R.id.uploadSucessSize);
            holder.times = arg1.findViewById(R.id.times);

            arg1.setTag(holder);
        } else {
            holder = (ViewHolder) arg1.getTag();
        }

        holder.username.setText("用户账号:  " + list.get(position).getUsername());
        holder.remaining_size.setText("剩余时间/金额:" + list.get(position).getRemaining_size());
        holder.uploadFailedSize.setText("失败:" + list.get(position).getUploadFailedSize() + "条数");
        holder.uploadSucessSize.setText("成功" + list.get(position).getUploadSucessSize() + "条");
        holder.times.setText("时间" + list.get(position).getTimes());
        return arg1;
    }

    private static class ViewHolder {
        private TextView username;
        private TextView uploadSucessSize;
        private TextView uploadFailedSize;
        private TextView times;
        private TextView remaining_size;
    }

}
