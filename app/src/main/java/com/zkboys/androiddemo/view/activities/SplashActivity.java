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
import com.zkboys.sdk.model.ClientVersionInfo;
import com.zkboys.sdk.oauth.model.OAuthToken;
import com.zkboys.sdk.service.DeliverymanService;
import com.zkboys.sdk.httpjson.ServiceTicket;

public class SplashActivity extends BaseActivity {
    SplashPresenterInteractor presenter;
    DeliverymanService deliverymanService;
    ServiceTicket findDeliveryManInfoServiceTicket, checkVisionServiceTicket;
    final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        deliverymanService = ((ZKBoysApplication) getApplication()).getZKBoysSDK().getDeliverymanService();

        presenter = new SplashPresenter(this, deliverymanService);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkVisionServiceTicket = presenter.checkVision("android", getVersionCode(getApplicationContext()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (findDeliveryManInfoServiceTicket != null) {
            findDeliveryManInfoServiceTicket.cancel();
        }

        if (null != checkVisionServiceTicket) {
            checkVisionServiceTicket.cancel();
        }
    }

    /**
     * 启动当前Activity
     * 使用方法: 任何位置调用 SplashActivity.actionStart(...);即可,具体需要什么参数,根据需求确定
     */
    /*
    public static void actionStart(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.putExtra("param1", param1);
        intent.putExtra("param2", param2);
        context.startActivity(intent);
    }
    */
    public void checkVisionResult(boolean success, String msg, final ClientVersionInfo clientVersionInfo) {
        LogUtil.d(TAG, String.valueOf(success));
        if (success && (clientVersionInfo.getPromote() == C.NEED_UPDATE || clientVersionInfo.getPromote() == C.FORCE_UPDATE)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("更新日志\n" + clientVersionInfo.getUpgradePrompt());
            builder.setTitle("版本更新");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                    Uri uri = Uri.parse(clientVersionInfo.getAppUrl());

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    showShortToast("请在浏览器中下载安装包文件");

                    startActivity(intent);

                    finish();


                }
            });


            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (clientVersionInfo.getPromote() == C.FORCE_UPDATE) { // 强制更新
                        dialog.dismiss();
                        getActivity().finish();
                    } else {
                        dialog.dismiss();
                        doNext();
                    }
                }
            });

            builder.setCancelable(false);

            builder.create().show();

        } else {
            doNext();
        }

    }

    private void doNext() {
        OAuthToken oAuthToken = ((ZKBoysApplication) getApplication()).getOAuthContext().load();
        if (oAuthToken != null) {
            LogUtil.d("SplashActivity", "可以进行一些需要认证的查询");
        } else { // 跳转到登录页面
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(C.SPLASH_SHOW_TIME); // 一段时间之后跳转到登录
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public int getVersionCode(Context context) { // 获取版本号(内部识别号)
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            LogUtil.d(TAG, "当前版本:" + String.valueOf(pi.versionCode));
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }
}
