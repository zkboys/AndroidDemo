package com.zkboys.androiddemo.view.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.utils.PreferenceUtil;
import com.zkboys.androiddemo.view.adapter.SelectStoreMerchantAdapter;
import com.zkboys.androiddemo.view.adapter.SelectStoreStoreAdapter;
import com.zkboys.sdk.model.MerchantInfo;
import com.zkboys.sdk.model.StoreInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectStoreActivity extends BaseActivity {

    private SelectStoreMerchantAdapter mMerchantAdapter;
    private SelectStoreStoreAdapter mStoreAdapter;
    private List<MerchantInfo> merchantInfoList;

    private static final String MERCHANTS_EXTRA_NAME = "MERCHANTS_EXTRA_NAME";

    @Bind(R.id.rv_select_store_merchants)
    RecyclerView mMerchantRecyclerView;

    @Bind(R.id.rv_select_store_stores)
    RecyclerView mStoreRecyclerView;

    /**
     * 启动当前Activity
     */
    public static void actionStart(Context context, List<MerchantInfo> merchants) {
        Intent intent = new Intent(context, SelectStoreActivity.class);
        intent.putParcelableArrayListExtra(MERCHANTS_EXTRA_NAME, (ArrayList<? extends Parcelable>) merchants);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_store);
        ButterKnife.bind(this);

        merchantInfoList = getIntent().getParcelableArrayListExtra(MERCHANTS_EXTRA_NAME);

        initMerchantRecyclerView();
        initStoreRecyclerView();
    }

    private void initMerchantRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMerchantAdapter = new SelectStoreMerchantAdapter(this);
        mMerchantRecyclerView.setLayoutManager(layoutManager);
        mMerchantRecyclerView.setAdapter(mMerchantAdapter);
        mMerchantAdapter.initData(merchantInfoList);
        mMerchantAdapter.setChooseListener(new SelectStoreMerchantAdapter.OnChooseListener() {
            @Override
            public void onChoose(MerchantInfo merchantInfo) {
                mStoreAdapter.initData(merchantInfo.getStores());
            }
        });
    }

    private void initStoreRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mStoreAdapter = new SelectStoreStoreAdapter(this);
        mStoreRecyclerView.setLayoutManager(layoutManager);
        mStoreRecyclerView.setAdapter(mStoreAdapter);
        mStoreAdapter.initData(merchantInfoList.get(0).getStores());

        mStoreAdapter.setChooseListener(new SelectStoreStoreAdapter.OnChooseListener() {
            @Override
            public void onChoose(final StoreInfo storeInfo) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SelectStoreActivity.this);
                dialogBuilder
                        .setTitle("提示")
                        .setMessage("确定进入 \"" + storeInfo.getName() + "\"？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                                PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(SelectStoreActivity.this);
                                preferenceUtil.setMerchantId(storeInfo.getMerchantId());
                                preferenceUtil.setStoreId(storeInfo.getId());
                                MainActivity.actionStart(SelectStoreActivity.this);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
            }
        });
    }

}
