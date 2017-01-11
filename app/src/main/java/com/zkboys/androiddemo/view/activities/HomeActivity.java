package com.zkboys.androiddemo.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.presenter.HomePresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity {
    @Bind(R.id.btn_home_show_recycler_view)
    Button mBtnHomeShowRecyclerView;

    private HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        homePresenter = new HomePresenter(this);
    }

    /**
     * 启动当前Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.btn_home_show_recycler_view, R.id.btn_home_get_user, R.id.btn_home_logout})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home_show_recycler_view:
                RecyclerViewDemoActivity.actionStart(this);
                break;
            case R.id.btn_home_get_user:
                homePresenter.getUserById("111");
                break;
            case R.id.btn_home_logout:
                homePresenter.logout();
                showShortToast("退出登录");
                this.finish();
                LoginActivity.actionStart(this);
                break;
        }
    }
}

