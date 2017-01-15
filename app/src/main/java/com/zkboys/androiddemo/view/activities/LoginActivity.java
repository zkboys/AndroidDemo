package com.zkboys.androiddemo.view.activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.presenter.LoginPresenter;
import com.zkboys.androiddemo.utils.AnimateUtil;
import com.zkboys.androiddemo.view.activities.vus.ILoginActivity;
import com.zkboys.sdk.model.MerchantInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements ILoginActivity {

    // UI references.
    @Bind(R.id.email)
    AutoCompleteTextView mEmailView;
    @Bind(R.id.password)
    EditText mPasswordView;
    @Bind(R.id.login_progress)
    View mProgressView;
    @Bind(R.id.login_form)
    View mLoginFormView;
    @Bind(R.id.email_sign_in_button)
    Button mEmailSignInButton;
    @Bind(R.id.tv_login_failed_error)
    TextView mLoginFailedError;

    private LoginPresenter loginPresenter;

    /**
     * 启动当前Activity
     */
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

    // 软键盘上登录按钮点击事件
    @OnEditorAction({R.id.password})
    boolean OnEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.login || id == EditorInfo.IME_NULL) {

            hideKeyboard();
            loginPresenter.login();
            return true;
        }
        return false;
    }

    @OnClick({R.id.email_sign_in_button})
    void OnClick(View view) {
        hideKeyboard();
        loginPresenter.login();
    }

    void hideKeyboard() {
        // 隐藏软键盘
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        AnimateUtil.showHideWithAnimate(this, mLoginFormView, !show);
        AnimateUtil.showHideWithAnimate(this, mProgressView, show);
    }

    @Override
    public String getUserName() {
        return mEmailView.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordView.getText().toString();
    }

    @Override
    public void clearUserName() {
        mEmailView.setText("");
        mEmailView.setError(null);
    }

    @Override
    public void clearPassword() {
        mPasswordView.setText("");
        mPasswordView.setError(null);
    }

    @Override
    public void clearUserNameError() {
        mEmailView.setError(null);
    }

    @Override
    public void clearPasswordError() {
        mPasswordView.setError(null);
    }

    @Override
    public void showUserNameError(int stringId) {
        mEmailView.setError(getString(stringId));
    }

    @Override
    public void showPasswordError(int stringId) {
        mPasswordView.setError(getString(stringId));
    }

    @Override
    public void focusUserName() {
        mEmailView.requestFocus();
    }

    @Override
    public void focusPassword() {
        mPasswordView.requestFocus();
    }

    @Override
    public void showLoading() {
        showProgress(true);
    }

    @Override
    public void hideLoading() {
        showProgress(false);
    }

    @Override
    public void doNext() {
        loginPresenter.getCurrentLoginUser();
    }

    @Override
    public void showFailedError(String msg) {
        mLoginFailedError.setText(msg);
    }

    @Override
    public void clearFailedError() {
        mLoginFailedError.setText(null);
    }

    @Override
    public void toMain() {
        MainActivity.actionStart(this);
        this.finish();
    }

    @Override
    public void toSelectStore(List<MerchantInfo> merchants) {
        SelectStoreActivity.actionStart(this, merchants);
        this.finish();
    }

    @Override
    public void showNoStoreError() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder
                .setTitle("提示")
                .setMessage("您尚未加入任何门店，当前系统不可用")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();

    }
}

