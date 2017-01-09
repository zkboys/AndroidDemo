package com.zkboys.androiddemo.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zkboys.androiddemo.R;

public class DemoActivity extends BaseActivity {
    /**
     * 启动当前Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DemoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }
}
