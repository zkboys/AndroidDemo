package com.zkboys.androiddemo.view.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zkboys.androiddemo.R;

public class ToolBar extends AppBarLayout {

    public ToolBar(final Context context, AttributeSet attrs) {
        super(context, attrs);
        System.out.println(attrs);
        LayoutInflater.from(context).inflate(R.layout.tool_bar, this);
        TextView titleView = (TextView) findViewById(R.id.tv_tool_bar_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.myToolBar);
        String title = ta.getString(R.styleable.myToolBar_title);
        // App Logo
//        toolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        toolbar.setTitle(""); // 设置默认ToolBar title为空，通过TextView 实现一个居中的title
        titleView.setText(title);
        // Sub Title
//        toolbar.setSubtitle("Sub title");

        ((AppCompatActivity) context).setSupportActionBar(toolbar);

        //这边要留意的是setNavigationIcon需要放在 setSupportActionBar之后才会生效。
        toolbar.setNavigationIcon(R.drawable.ic_back);
        Drawable navigationIcon = toolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.setAlpha(150); // 设置透明度，改成灰色图标
        }
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity) context).finish();
            }
        });
    }
}

