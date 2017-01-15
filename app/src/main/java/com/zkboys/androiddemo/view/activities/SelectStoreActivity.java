package com.zkboys.androiddemo.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.view.adapter.SelectStoreMerchantAdapter;
import com.zkboys.sdk.model.MerchantInfo;
import com.zkboys.sdk.model.StoreInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectStoreActivity extends BaseActivity {

    private SelectStoreMerchantAdapter mMerchantAdapter;

    @Bind(R.id.rv_select_store_merchants)
    RecyclerView mMerchantRecyclerView;

    /**
     * 启动当前Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SelectStoreActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_store);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMerchantAdapter = new SelectStoreMerchantAdapter(this);
        mMerchantRecyclerView.setLayoutManager(layoutManager);
        mMerchantRecyclerView.setAdapter(mMerchantAdapter);
        mMerchantAdapter.setChooseListener(new SelectStoreMerchantAdapter.OnChooseListener() {
            @Override
            public void onChoose(MerchantInfo merchantInfo) {
                showShortToast(merchantInfo.getName());
            }
        });

        List<MerchantInfo> merchantInfoList = new ArrayList<>();
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setId("11111");
        merchantInfo.setName("测试品牌1就这这个品牌汉子牛逼的，还是不够长还是泽呢的");
        merchantInfo.setLogo("http://img4.imgtn.bdimg.com/it/u=3217962789,3430649993&fm=23&gp=0.jpg");
        merchantInfo.setStores(new ArrayList<StoreInfo>());
        merchantInfoList.add(merchantInfo);

        MerchantInfo merchantInfo2 = new MerchantInfo();
        merchantInfo2.setId("22222");
        merchantInfo2.setName("测试品牌2");
        merchantInfo2.setLogo("http://b.hiphotos.baidu.com/image/h%3D300/sign=d3fd91ce05f431ada3d245397b37ac0f/d058ccbf6c81800a7892fd52b83533fa828b4772.jpg");
        merchantInfo2.setStores(new ArrayList<StoreInfo>());
        merchantInfoList.add(merchantInfo2);

        mMerchantAdapter.initData(merchantInfoList);
    }

}
