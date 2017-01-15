package com.zkboys.androiddemo.view.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
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
                serviceTickets.remove(i);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            return hideSoftInput();
        }
        return super.onTouchEvent(event);
    }

    public boolean hideSoftInput() {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        return mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void addServiceTicket(ServiceTicket serviceTicket) {
        serviceTickets.add(serviceTicket);
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


    public void showAlertDialog(String message, DialogInterface.OnClickListener positiveButtonClickListener) {
        showAlertDialog("提示", message, "确定", positiveButtonClickListener);
    }


    public void showAlertDialog(String title, String message, String positiveButtonText, DialogInterface.OnClickListener positiveButtonClickListener) {
        Dialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, positiveButtonClickListener)
                .create();
        alertDialog.show();
    }
}
