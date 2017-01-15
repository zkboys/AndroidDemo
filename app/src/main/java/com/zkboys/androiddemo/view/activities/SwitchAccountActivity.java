package com.zkboys.androiddemo.view.activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.presenter.LoginPresenter;
import com.zkboys.androiddemo.presenter.SwitchAccountPresenter;
import com.zkboys.androiddemo.utils.AnimateUtil;
import com.zkboys.androiddemo.utils.PreferenceUtil;
import com.zkboys.androiddemo.view.activities.vus.ILoginActivity;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.MerchantInfo;
import com.zkboys.sdk.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SwitchAccountActivity extends BaseActivity implements ILoginActivity {
    private SwitchAccountAdapter mAdapter;

    private SwitchAccountPresenter presenter;

    private ServiceTicket serviceTicket;
    private UserInfo currentSelectedUser;
    private List<UserInfo> users = new ArrayList<>();
    private LoginPresenter loginPresenter;

    @Bind(R.id.tv_switch_account_user_name)
    TextView mCurrentUser;
    @Bind(R.id.login_progress)
    View mProgressView;
    @Bind(R.id.login_form)
    View mLoginFormView;
    @Bind(R.id.tv_login_failed_error)
    TextView mLoginFailedError;
    @Bind(R.id.password)
    EditText mPasswordView;
    @Bind(R.id.tv_switch_account_tip)
    TextView mTip;

    /**
     * 启动当前Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SwitchAccountActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_account);
        ButterKnife.bind(this);
        presenter = new SwitchAccountPresenter(this);
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        serviceTicket = presenter.getAllUsers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceTicket != null) {
            serviceTicket.cancel();
        }
    }

    public void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_switch_account_users);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new SwitchAccountAdapter(users, this);
        recyclerView.setAdapter(mAdapter);
    }

    public void showUsers(List<UserInfo> users) {
        this.users = users;
        initAdapter();
    }

    public void setCurrentSelectedUser(UserInfo user) {
        AnimateUtil.showHideWithAnimate(this, mTip, false);
        AnimateUtil.showHideWithAnimate(this, mLoginFormView, true);
        currentSelectedUser = user;
        mCurrentUser.setText(user.getName());
        clearPassword();
        clearFailedError();
    }

    public UserInfo getCurrentSelectedUser() {
        return currentSelectedUser;
    }

    @OnClick({R.id.email_sign_in_button})
    void OnClick(View view) {
        hideKeyboard();
        if (currentSelectedUser == null) {
            showShortToast(getString(R.string.error_no_user_selected));
        } else {
            loginPresenter.login();
        }
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
        return currentSelectedUser.getLoginname();
    }

    @Override
    public String getPassword() {
        return mPasswordView.getText().toString();
    }

    @Override
    public void clearUserName() {

    }

    @Override
    public void clearPassword() {
        mPasswordView.setText("");
        mPasswordView.setError(null);
    }

    @Override
    public void clearUserNameError() {

    }

    @Override
    public void clearPasswordError() {
        mPasswordView.setError(null);
    }

    @Override
    public void showUserNameError(int stringId) {

    }

    @Override
    public void showPasswordError(int stringId) {
        mPasswordView.setError(getString(stringId));
    }

    @Override
    public void focusUserName() {

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
        PreferenceUtil preUtil = PreferenceUtil.getInstance(this);
        preUtil.setUserName(currentSelectedUser.getName());
        preUtil.setLoginName(currentSelectedUser.getLoginname());
        MainActivity.actionStart(this);
        finish();
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
    }

    @Override
    public void toSelectStore(List<MerchantInfo> merchants) {
        SelectStoreActivity.actionStart(this, merchants);
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
                    }
                })
                .setCancelable(false)
                .create()
                .show();
        finish();
    }


    class SwitchAccountAdapter extends RecyclerView.Adapter<SwitchAccountAdapter.MyViewHolder> {
        private int nowChoosePosition = 0;
        private List<UserInfo> users;
        private SwitchAccountActivity context;

        public SwitchAccountAdapter(List<UserInfo> users, SwitchAccountActivity context) {
            this.users = users;
            this.context = context;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final MyViewHolder holder = new MyViewHolder(LayoutInflater
                    .from(SwitchAccountActivity.this).inflate(R.layout.item_switch_account, parent, false));
            holder.bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (position != nowChoosePosition) {
                        UserInfo user = users.get(position);
                        context.setCurrentSelectedUser(user);
                        setNowChoosePosition(position);
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            UserInfo user = users.get(position);
            holder.bt.setText(user.getName());
            if (position == nowChoosePosition) {
                holder.bt.setBackground(getDrawable(R.drawable.btn_ghost));
                holder.bt.setTextColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                holder.bt.setBackground(getDrawable(R.drawable.btn_light));
                holder.bt.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
            }
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        public void setNowChoosePosition(int nowChoosePosition) {
            int oldPosition = this.nowChoosePosition;
            this.nowChoosePosition = nowChoosePosition;
            notifyItemChanged(oldPosition);
            notifyItemChanged(nowChoosePosition);
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            Button bt;

            public MyViewHolder(View view) {
                super(view);
                bt = (Button) view.findViewById(R.id.bt_switch_account_user);
            }
        }
    }

}
