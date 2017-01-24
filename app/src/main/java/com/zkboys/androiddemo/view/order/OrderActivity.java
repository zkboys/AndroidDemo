package com.zkboys.androiddemo.view.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.view.BaseActivity;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.DishCategoryInfo;
import com.zkboys.sdk.model.DishInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderActivity extends BaseActivity {

    private String tableId;
    private DishCategoryAdapter mCategoryAdapter;
    private DishAdapter mDishAdapter;
    private DishPresenter presenter;
    private ServiceTicket mCategoryTicket;
    private GridLayoutManager mDishGridLayoutManager;
    @Bind(R.id.rv_order_dish_category)
    RecyclerView mCategoryRecyclerView;

    @Bind(R.id.rv_order_dishes)
    RecyclerView mDishRecyclerView;

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
        initDishView();

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
            public void onClick(DishCategoryInfo categoryInfo, int position) {
                int categoryPosition = mCategoryAdapter.getCategoryPositionInPlainData(position);
                mDishGridLayoutManager.scrollToPositionWithOffset(categoryPosition, 0);
            }
        });
    }

    public void initDishView() {
        mDishGridLayoutManager = new GridLayoutManager(this, 3);
        mDishGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mDishAdapter.isCategory(position)) {
                    return mDishGridLayoutManager.getSpanCount();
                }
                return 1;
            }
        });
        mDishAdapter = new DishAdapter(this);
        mDishRecyclerView.setLayoutManager(mDishGridLayoutManager);
        mDishRecyclerView.setAdapter(mDishAdapter);
        mDishAdapter.setOnItemClickListener(new DishAdapter.OnItemClickListener() {
            @Override
            public void onClick(DishInfo dishInfo, int position) {
                showShortToast(dishInfo.getName());
            }
        });
    }

    public void setInitCategoryData(List<DishCategoryInfo> data) {
        mCategoryAdapter.initData(data);
        mDishAdapter.initData(data);
    }
}
