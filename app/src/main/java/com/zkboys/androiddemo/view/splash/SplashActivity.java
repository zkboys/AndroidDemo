package com.zkboys.androiddemo.view.splash;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.application.ZKBoysApplication;
import com.zkboys.androiddemo.view.BaseActivity;
import com.zkboys.androiddemo.view.login.LoginActivity;
import com.zkboys.androiddemo.view.main.MainActivity;
import com.zkboys.sdk.common.C;
import com.zkboys.androiddemo.utils.LogUtil;
import com.zkboys.androiddemo.utils.PreferenceUtil;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.ClientVersionInfo;
import com.zkboys.sdk.oauth.model.OAuthToken;

public class SplashActivity extends BaseActivity {
    ISplashPresenter presenter;
    ServiceTicket checkVisionServiceTicket;
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

    public void checkVisionFail(String failMassage) {
        showAlertDialog(failMassage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
            }
        });
    }

    public void needUpdate(ClientVersionInfo clientVersionInfo) {
        final Byte promote = clientVersionInfo.getPromote();
        final String upgradePrompt = clientVersionInfo.getUpgradePrompt();
        final String appUrl = clientVersionInfo.getAppUrl();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage("更新日志\n" + upgradePrompt);
        dialogBuilder.setTitle("版本更新");
        dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
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
    }

    public void doNext() {
        OAuthToken oAuthToken = ((ZKBoysApplication) getApplication()).getOAuthContext().load();
        if (oAuthToken != null) {
            PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(this);
            String mchId = preferenceUtil.getMerchantId();
            String storeId = preferenceUtil.getStoreId();
            if (TextUtils.isEmpty(mchId) || TextUtils.isEmpty(storeId)) {
                toLogin();
                return;
            }

            toMain();
            return;
        }

        toLogin();
    }

    public void toMain() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(C.SPLASH_SHOW_TIME);
                    MainActivity.actionStart(SplashActivity.this);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void toLogin() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(C.SPLASH_SHOW_TIME);
                    LoginActivity.actionStart(SplashActivity.this);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
