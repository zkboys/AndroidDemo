package com.zkboys.androiddemo.view.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.androiddemo.common.C;
import com.zkboys.androiddemo.presenter.SplashPresenter;
import com.zkboys.androiddemo.presenter.vus.SplashPresenterInteractor;
import com.zkboys.androiddemo.utils.LogUtil;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.ClientVersionInfo;
import com.zkboys.sdk.oauth.model.OAuthToken;

public class SplashActivity extends BaseActivity {
    SplashPresenterInteractor presenter;
    ServiceTicket findDeliveryManInfoServiceTicket, checkVisionServiceTicket;
    final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        presenter = new SplashPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkVisionServiceTicket = presenter.checkVision("android", getVersionCode(getApplicationContext()));
        serviceTickets.add(checkVisionServiceTicket);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 启动当前Activity
     * 使用方法: 任何位置调用 SplashActivity.actionStart(...);即可,具体需要什么参数,根据需求确定
     */
    public static void actionStart(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.putExtra("param1", param1);
        intent.putExtra("param2", param2);
        context.startActivity(intent);
    }

    public void checkVisionResult(boolean success, final String msg, final ClientVersionInfo clientVersionInfo) {
        LogUtil.d(TAG, String.valueOf(success));
        if (!success) {
            showShortToast(msg);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(C.SPLASH_SHOW_TIME);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else if (clientVersionInfo.getPromote() == C.NEED_UPDATE || clientVersionInfo.getPromote() == C.FORCE_UPDATE) {
            final Byte promote = clientVersionInfo.getPromote();
            final String upgradePrompt = clientVersionInfo.getUpgradePrompt();
            final String appUrl = clientVersionInfo.getAppUrl();
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setMessage("更新日志\n" + upgradePrompt);
            dialogBuilder.setTitle("版本更新");
            dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss(); // 隐藏掉 弹框
                    showShortToast("请在浏览器中下载安装包文件");
                    Uri uri = Uri.parse(appUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    finish();
                }
            });

            dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (promote == C.FORCE_UPDATE) { // 强制更新
                        dialog.dismiss();
                        getActivity().finish();
                    } else {
                        dialog.dismiss();
                        doNext();
                    }
                }
            });

            dialogBuilder.setCancelable(false);
            dialogBuilder.create().show();
        } else {
            doNext();
        }

    }

    private void doNext() {
        OAuthToken oAuthToken = ((ZKBoysApplication) getApplication()).getOAuthContext().load();
        if (oAuthToken != null) {
            LogUtil.d("SplashActivity", "可以进行一些需要认证的查询， 比如根据token查询用户");
            // 跳转到首页 TODO 如果首页需要用户登录，这里要验证 oAuthToken 是否有效
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(C.SPLASH_SHOW_TIME); // 一段时间之后跳转到首页
                        HomeActivity.actionStart(SplashActivity.this);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            // 跳转到登录页面
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(C.SPLASH_SHOW_TIME); // 一段时间之后跳转到登录
                        LoginActivity.actionStart(SplashActivity.this);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 获取版本号(内部识别号)
     *
     * @param context
     * @return
     */
    public int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            LogUtil.d(TAG, "当前版本:" + String.valueOf(pi.versionCode));
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
