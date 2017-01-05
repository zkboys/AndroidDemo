package com.zkboys.androiddemo.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.view.activities.layout.TitleLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity {
    @Bind(R.id.btn_home_show_recycler_view)
    Button mBtnHomeShowRecyclerView;

    @Bind(R.id.tl_home_title)
    TitleLayout mHomeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mHomeTitle.setTitle("首页");
        mHomeTitle.setButtonClickListener(new TitleLayout.ButtonClickListener() {
            @Override
            public void OnLeftButtonClick() {
                showShortToast("首页中绑定左侧按钮点击事件");
            }

            @Override
            public void OnRightButtonClick() {
                showShortToast("首页中绑定右侧点击事件");
            }
        });
    }

    /**
     * 启动当前Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.btn_home_show_recycler_view})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home_show_recycler_view:
                RecyclerViewDemoActivity.actionStart(this);
                break;
        }
    }
}

