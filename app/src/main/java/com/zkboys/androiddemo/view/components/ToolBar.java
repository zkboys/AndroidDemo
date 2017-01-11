package com.zkboys.androiddemo.view.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.zkboys.androiddemo.R;

public class ToolBar extends LinearLayout {

    public ToolBar(final Context context, AttributeSet attrs) {
        super(context, attrs);
        System.out.println(attrs);
        LayoutInflater.from(context).inflate(R.layout.tool_bar, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.myToolBar);
        String title = ta.getString(R.styleable.myToolBar_title);
        // App Logo
//        toolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        toolbar.setTitle(title);
        // Sub Title
//        toolbar.setSubtitle("Sub title");

        ((AppCompatActivity) context).setSupportActionBar(toolbar);

        //这边要留意的是setNavigationIcon需要放在 setSupportActionBar之后才会生效。
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity) context).finish();
            }
        });
    }
}

