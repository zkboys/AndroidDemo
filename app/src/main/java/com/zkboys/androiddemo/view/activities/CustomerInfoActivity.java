package com.zkboys.androiddemo.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.view.components.Keyboard;
import com.zkboys.sdk.model.TableInfo;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CustomerInfoActivity extends BaseActivity {

    private List<String> mTelItems;
    private List<String> mCustomerNameItems;

    @Bind(R.id.rv_customer_keyboard)
    Keyboard mNumberKeyboardRecyclerView;

    @Bind(R.id.til_customer_info_name)
    TextInputLayout mNameInput;


    /**
     * 启动当前Activity
     */
    public static void actionStart(Context context, TableInfo table) {
        Intent intent = new Intent(context, CustomerInfoActivity.class);
        intent.putExtra("table", table);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);
        ButterKnife.bind(this);
        TableInfo table = getIntent().getParcelableExtra("table");
        mTelItems = Arrays.asList(getResources().getStringArray(R.array.telephone_keyboard));
        mCustomerNameItems = Arrays.asList(getResources().getStringArray(R.array.customer_name));
        showTelephoneKeyboard();
//        showCustomerNameKeyboard();

    }

    public void showTelephoneKeyboard() {
        mNameInput.setVisibility(View.GONE);
        mNumberKeyboardRecyclerView
                .setItems(mTelItems)
                .setSpanCount(3)
                .setItemPadding(new int[]{0, 30, 0, 30})
                .setItemClickListener(new Keyboard.ItemClickListener() {
                    @Override
                    public void onClick(View v, String number) {
                        // 点击 .
                        if (mTelItems.get(9).equals(number)) {
                            showShortToast("点击了 【" + number + "】");
                            // 动态改变键盘内容
                            showCustomerNameKeyboard();
                        } else if (mTelItems.get(11).equals(number)) {
                            showShortToast("点击了 【" + number + "】");
                        } else {
                            showShortToast("点击了数字：【" + number + "】");
                        }
                    }
                });
    }

    public void showCustomerNameKeyboard() {
        mNameInput.setVisibility(View.VISIBLE);
        mNumberKeyboardRecyclerView
                .setItems(mCustomerNameItems)
                .setSpanCount(5)
                .setItemPadding(new int[]{0, 15, 0, 15})
                .setItemClickListener(new Keyboard.ItemClickListener() {
                    @Override
                    public void onClick(View v, String name) {
                        // 点击 .
                        showShortToast("点击了：【" + name + "】");
                    }
                });
    }
}
