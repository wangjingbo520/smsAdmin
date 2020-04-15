package com.tools.smsadmin;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.tools.smsadmin.http.IHandleMessage;
import com.tools.smsadmin.http.InterfaceMethod;
import com.tools.smsadmin.http.MyVolleyHandler;
import com.tools.smsadmin.http.RequestHandler;
import com.tools.smsadmin.model.UserBean;
import com.tools.smsadmin.utils.Constants;
import com.tools.smsadmin.utils.SPUtils;
import com.tools.smsadmin.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchUserActivity extends AppCompatActivity implements IHandleMessage {

    @BindView(R.id.username)
    TextView usernames;
    @BindView(R.id.tvOpenning)
    TextView tvOpenning;
    @BindView(R.id.tv_register_time)
    TextView tvRegisterTime;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_reset)
    TextView tv_reset;
    @BindView(R.id.tv_days)
    TextView tv_days;
    @BindView(R.id.tv_kami)
    TextView tv_kami;

    private SearchView mSearchView;

    private UserBean userBean;

    public MyVolleyHandler<SearchUserActivity> mHandler;

    public String content = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        ButterKnife.bind(this);
        mHandler = new MyVolleyHandler<>(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("查找用户");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.serch_share_menu, menu);
        final MenuItem item = menu.findItem(R.id.search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    ToastUtil.showMessage("用户名不能为空");
                    return false;
                }

                SearchUserActivity.this.content = query;
                getData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private void getData(String username) {
        tvRegisterTime.setText("");
        tvOpenning.setText("");
        usernames.setText("");
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        RequestHandler.addRequest(Request.Method.POST, this, mHandler, Constants.CODE_RESULT,
                params, null, true, InterfaceMethod.QUERY_USER);
    }

    @Override
    public void onHandleMessage(Message msg) {
        if (msg.what == Constants.CODE_RESULT) {
            Bundle data = msg.getData();
            String interfaceMethod = data.getString("interfaceMethod");
            String response = data.getString("response");

            if (interfaceMethod.equals(InterfaceMethod.QUERY_USER)) {
                UserBean userBean = new Gson().fromJson(response, UserBean.class);
                usernames.setText(userBean.getData().getUsername());
                int openning = userBean.getData().getOpening();
                if (0 == openning) {
                    tvOpenning.setText("未开通");
                    tv_kami.setVisibility(View.GONE);
                } else if (1 == openning) {
                    tvOpenning.setText("已开通");
                    tv_kami.setText(userBean.getData().getCamilo());
                    tv_kami.setVisibility(View.VISIBLE);
                    tv_kami.setText("卡密: " + userBean.getData().getCamilo());
                }

                tvOpenning.setText(userBean.getData().getOpening() == 0 ? "未开通" : "已开通会员");
                tvRegisterTime.setText("注册日期：" + userBean.getData().getRegister_date());
                tv_days.setText("剩余使用天数:" + userBean.getData().getRemaining_day());
                this.userBean = userBean;
                tvSure.setVisibility(View.VISIBLE);
                tv_reset.setVisibility(View.VISIBLE);
            } else if (interfaceMethod.equals(InterfaceMethod.RESET_OPENNING)) {
                ToastUtil.showMessage("重置成功");
                getData(content);
            }
        }
    }

    @OnClick({R.id.tv_sure, R.id.tv_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_reset:
                if (userBean == null) {
                    ToastUtil.showMessage("没用户");
                    return;
                }
                resetOpenning(userBean.getData().getUsername());
                break;
            case R.id.tv_sure:
                if (userBean == null) {
                    ToastUtil.showMessage("没用户");
                    return;
                }
                ManageActivity.start(this, userBean.getData().getUser_id(),
                        userBean.getData().getUsername(), userBean.getData().getOpening(), userBean.getData().getCamilo());
                break;
            default:
                break;
        }

    }

    private void resetOpenning(String username) {
        Map<String, String> params = new HashMap<>();
        String admin_name = SPUtils.getInstance().getString(Constants.ADMIN_USERNAME, "");
        params.put("username", username);
        params.put("admin_name", admin_name);
        RequestHandler.addRequest(Request.Method.POST, SearchUserActivity.this, mHandler, Constants.CODE_RESULT,
                params, null, true, InterfaceMethod.RESET_OPENNING);
    }
}
