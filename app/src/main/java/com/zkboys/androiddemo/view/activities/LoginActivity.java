package com.zkboys.androiddemo.view.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.presenter.LoginPresenter;
import com.zkboys.androiddemo.view.activities.vus.ILoginActivity;
import com.zkboys.sdk.httpjson.ServiceTicket;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginActivity {

    @Bind(R.id.userName)
    EditText userNameText;

    @Bind(R.id.passWord)
    EditText passWordText;

    @Bind(R.id.btn_login)
    Button loginButton;

    @Bind(R.id.btn_register)
    Button registerButton;

    private ProgressDialog proDialog;
    private LoginPresenter loginPresenter;


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                loginPresenter.login();
                break;

            case R.id.btn_register:
                // TODO
                break;
        }
    }

    @Override
    public String getUserName() {
        return userNameText.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return passWordText.getText().toString().trim();
    }

    @Override
    public void clearUserName() {
        userNameText.setText("");
    }

    @Override
    public void clearPassword() {
        passWordText.setText("");
    }

    @Override
    public void showLoading() {
        proDialog = ProgressDialog.show(this, "提示", "正在登录。。。");
    }

    @Override
    public void hideLoading() {
        proDialog.hide();
    }

    @Override
    public void toMainActivity() {
        HomeActivity.actionStart(this);
    }

    @Override
    public void showFailedError(String msg) {
        showShortToast(msg);
    }

    @Override
    public void addServiceTicket(ServiceTicket serviceTicket) {
        serviceTickets.add(serviceTicket);
    }
}
