package com.zkboys.androiddemo.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.presenter.DishPresenter;
import com.zkboys.androiddemo.presenter.vus.IDishPresenter;
import com.zkboys.androiddemo.view.adapter.DishCategoryAdapter;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.DishCategoryInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderActivity extends BaseActivity {

    private String tableId;
    private DishCategoryAdapter mCategoryAdapter;
    private IDishPresenter presenter;
    private ServiceTicket mCategoryTicket;
    @Bind(R.id.rv_order_dish_category)
    RecyclerView mCategoryRecyclerView;

    /**
     * 启动当前Activity
     */
    public static void actionStart(Context context, String tableId) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra("tableId", tableId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        tableId = intent.getStringExtra("tableId");

        presenter = new DishPresenter(this);
        initCategoryView();

        mCategoryTicket = presenter.getAllDishes();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCategoryTicket != null) {
            mCategoryTicket.cancel();
        }
    }

    public void initCategoryView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mCategoryRecyclerView.setLayoutManager(layoutManager);
        mCategoryAdapter = new DishCategoryAdapter(this);
        mCategoryRecyclerView.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setOnItemClickListener(new DishCategoryAdapter.OnItemClickListener() {
            @Override
            public void onClick(DishCategoryInfo categoryInfo) {
                showShortToast(categoryInfo.getName());
            }
        });
    }

    public void setInitCategoryData(List<DishCategoryInfo> data) {
        mCategoryAdapter.initData(data);
    }
}
