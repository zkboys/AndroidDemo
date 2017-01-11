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
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        // App Logo
////        toolbar.setLogo(R.mipmap.ic_launcher);
//        // Title
//        toolbar.setTitle("My Demo Title");
//        // Sub Title
////        toolbar.setSubtitle("Sub title");
//
//        setSupportActionBar(toolbar);
//
//        //这边要留意的是setNavigationIcon需要放在 setSupportActionBar之后才会生效。
//        toolbar.setNavigationIcon(R.drawable.ic_back);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

    }
}
