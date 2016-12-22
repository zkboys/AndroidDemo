package com.zkboys.androiddemo.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.zkboys.androiddemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.userName)
    EditText userNameText;

    @Bind(R.id.passWord)
    EditText passWordText;

    @Bind(R.id.btn_login)
    Button loginButton;

    @Bind(R.id.btn_register)
    Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    void login() {
        String userName = userNameText.getText().toString().trim();
        String passWord = passWordText.getText().toString().trim();
        showShortToast(userName + ":" + passWord);
    }

    @OnClick(R.id.btn_register)
    void register() {
        showShortToast("注册");
    }
}
