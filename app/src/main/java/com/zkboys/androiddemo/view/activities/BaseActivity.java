package com.zkboys.androiddemo.view.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 关闭所有activity,退出app
     */
    protected void finishAll() {
        ActivityCollector.finishAll();
    }

    protected Activity getActivity() {
        return this;
    }

    protected void showShortToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
