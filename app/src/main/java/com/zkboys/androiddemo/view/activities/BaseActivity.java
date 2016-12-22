package com.zkboys.androiddemo.view.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.zkboys.sdk.httpjson.ServiceTicket;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    protected List<ServiceTicket> serviceTickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityCollector.addActivity(this);
        serviceTickets = new ArrayList<ServiceTicket>();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);

        for (int i = 0; i < serviceTickets.size(); i++) {
            ServiceTicket ticket = serviceTickets.get(i);
            if (null != ticket) {
                ticket.cancel();
            }
        }
    }

    /**
     * 关闭所有activity,退出app
     */
    public void finishAll() {
        ActivityCollector.finishAll();
    }

    public Activity getActivity() {
        return this;
    }

    public void showShortToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
