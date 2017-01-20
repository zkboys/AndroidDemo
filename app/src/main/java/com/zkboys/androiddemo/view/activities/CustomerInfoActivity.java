package com.zkboys.androiddemo.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.view.components.Keyboard;
import com.zkboys.sdk.model.TableInfo;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomerInfoActivity extends BaseActivity {
    private static final String TABLE_EXTRA_NAME = "table";
    private final String MALE = "M";
    private final String FEMALE = "F";

    private enum EditTextType {
        NAME, MOBILE, PEOPLE_NUMBER
    }

    private List<String> mTelItems;
    private List<String> mCustomerNameItems;
    private TableInfo mTable;
    private EditTextType currentEditText = EditTextType.NAME;

    @Bind(R.id.rv_customer_keyboard)
    Keyboard mNumberKeyboardRecyclerView;

    @Bind(R.id.til_customer_info_name)
    TextInputLayout mCustomerNameInputLayout;

    @Bind(R.id.tv_customer_info_table_name)
    TextView mTableName;

    @Bind(R.id.et_customer_info_customer_name)
    EditText mCustomerName;

    @Bind(R.id.rg_customer_info_gender)
    RadioGroup mCustomerGender;

    @Bind(R.id.et_customer_info_customer_mobile)
    EditText mCustomerMobile;

    @Bind(R.id.et_customer_info_customer_people_number)
    EditText mCustomerPeopleNumber;

    @Bind(R.id.et_customer_input_name)
    EditText mCustomerInputName;

    @Bind(R.id.btn_customer_info_submit)
    Button mSubmit;

    /**
     * 启动当前Activity
     */
    public static void actionStart(Context context, TableInfo table) {
        Intent intent = new Intent(context, CustomerInfoActivity.class);
        intent.putExtra(TABLE_EXTRA_NAME, table);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);
        ButterKnife.bind(this);

        mTable = getIntent().getParcelableExtra(TABLE_EXTRA_NAME);
        mTableName.setText(mTable.getName());
        mTelItems = Arrays.asList(getResources().getStringArray(R.array.telephone_keyboard));
        mCustomerNameItems = Arrays.asList(getResources().getStringArray(R.array.customer_name));

        showCustomerNameKeyboard();
        initEditText();
    }

    @OnClick({R.id.btn_customer_info_submit})
    void onClick(View view) {
        String tableId = mTable.getId();
        String name = getNameText();
        String gender = mCustomerGender.getCheckedRadioButtonId() == R.id.rb_customer_info_male ? MALE : FEMALE;
        String mobile = getMobileText();
        String peopleNumber = getPeopleNumberText();

//        if (TextUtils.isEmpty(name)) {
//            showNameError(R.string.error_customer_name_required);
//            return;
//        }
//        if (TextUtils.isEmpty(mobile)) {
//            showMobileError(R.string.error_mobile_required);
//            return;
//        }
//        if (mobile.length() != 11) {
//            // TODO: 格式校验
//            showMobileError(R.string.error_mobile_format);
//            return;
//        }
        if (TextUtils.isEmpty(peopleNumber)) {
            showPeopleNumberError(R.string.error_people_number_required);
            return;
        }

        // TODO: submit
        showShortToast("tableId：" + tableId + "  姓氏：" + name + "  性别：" + gender + "  电话：" + mobile + "  人数：" + peopleNumber);
        finish();
        OrderActivity.actionStart(this, tableId);
    }

    public void initEditText() {
        // 输入其他姓氏
        mCustomerInputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setNameText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // 输入框点击获取焦点，但是不弹出软键盘
        View.OnTouchListener editTextTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // 如果软键盘已经弹起，隐藏软键盘
                hideSoftInput();
                return false;
            }
        };

        View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    switch (view.getId()) {
                        case R.id.et_customer_info_customer_name:
                            currentEditText = EditTextType.NAME;
                            showCustomerNameKeyboard();
                            break;
                        case R.id.et_customer_info_customer_mobile:
                            currentEditText = EditTextType.MOBILE;
                            showTelephoneKeyboard();
                            break;
                        case R.id.et_customer_info_customer_people_number:
                            currentEditText = EditTextType.PEOPLE_NUMBER;
                            showTelephoneKeyboard();
                            break;
                    }
                }
            }
        };

        mCustomerName.setOnTouchListener(editTextTouchListener);
        mCustomerName.setOnFocusChangeListener(focusChangeListener);
        mCustomerName.setInputType(InputType.TYPE_NULL);

        mCustomerMobile.setOnTouchListener(editTextTouchListener);
        mCustomerMobile.setOnFocusChangeListener(focusChangeListener);
        mCustomerMobile.setInputType(InputType.TYPE_NULL);

        mCustomerPeopleNumber.setOnTouchListener(editTextTouchListener);
        mCustomerPeopleNumber.setOnFocusChangeListener(focusChangeListener);
        mCustomerPeopleNumber.setInputType(InputType.TYPE_NULL);
    }

    public void showTelephoneKeyboard() {
        mCustomerNameInputLayout.setVisibility(View.GONE);
        mNumberKeyboardRecyclerView
                .setItems(mTelItems)
                .setSpanCount(3)
                .setItemPadding(new int[]{0, 30, 0, 30})
                .setItemClickListener(new Keyboard.ItemClickListener() {
                    @Override
                    public void onClick(View v, String number) {
                        if (mTelItems.get(9).equals(number)) {
                            // 左下角 清空
                            if (currentEditText == EditTextType.MOBILE) setMobileText("");
                            if (currentEditText == EditTextType.PEOPLE_NUMBER) setPeopleNumberText("");
                        } else if (mTelItems.get(11).equals(number)) {
                            // 右下角 删除
                            if (currentEditText == EditTextType.MOBILE) shiftMobileText();
                            if (currentEditText == EditTextType.PEOPLE_NUMBER) shiftPeopleNumberText();
                        } else {
                            // 点击数字
                            if (currentEditText == EditTextType.MOBILE) appendMobileText(number);
                            if (currentEditText == EditTextType.PEOPLE_NUMBER) appendPeopleNumberText(number);
                        }
                    }
                });
    }

    public void showCustomerNameKeyboard() {
        mCustomerNameInputLayout.setVisibility(View.VISIBLE);
        mNumberKeyboardRecyclerView
                .setItems(mCustomerNameItems)
                .setSpanCount(5)
                .setItemPadding(new int[]{0, 15, 0, 15})
                .setItemClickListener(new Keyboard.ItemClickListener() {
                    @Override
                    public void onClick(View v, String name) {
                        setNameText(name);
                    }
                });
    }

    public String getNameText() {
        return mCustomerName.getText().toString();
    }

    public void setNameText(String name) {
        mCustomerName.setError(null);
        mCustomerName.setText(name);
    }

    public void showNameError(int stringId) {
        mCustomerName.requestFocus();
        currentEditText = EditTextType.NAME;
        showCustomerNameKeyboard();
        mCustomerName.setError(getResources().getString(stringId));
    }

    public String getMobileText() {
        // TODO：反格式化
        return mCustomerMobile.getText().toString();
    }

    public void setMobileText(String mobile) {
        mCustomerMobile.setError(null);
        // TODO: 格式化、限制长度为11
        mCustomerMobile.setText(mobile);
    }

    public void appendMobileText(String mobile) {
        String oldValue = getMobileText();
        if ("".equals(oldValue) && !"1".equals(mobile)) {
            setMobileText("");
            return;
        }
        if (oldValue.length() >= 11) {
            return;
        }
        setMobileText(oldValue + mobile);
    }

    public void shiftMobileText() {
        String oldValue = getMobileText();
        if (!"".equals(oldValue)) {
            setMobileText(oldValue.substring(0, oldValue.length() - 1));
        }
    }

    public void showMobileError(int stringId) {
        mCustomerMobile.requestFocus();
        currentEditText = EditTextType.MOBILE;
        showTelephoneKeyboard();
        mCustomerMobile.setError(getResources().getString(stringId));
    }

    public String getPeopleNumberText() {
        return mCustomerPeopleNumber.getText().toString();
    }

    public void setPeopleNumberText(String peopleNumber) {
        mCustomerPeopleNumber.setError(null);
        mCustomerPeopleNumber.setText(peopleNumber);
    }

    public void appendPeopleNumberText(String peopleNumber) {
        String oldValue = getPeopleNumberText();
        if ("".equals(oldValue) && "0".equals(peopleNumber)) {
            setPeopleNumberText("");
        } else {
            setPeopleNumberText(oldValue + peopleNumber);
        }
    }

    public void shiftPeopleNumberText() {
        String oldValue = getPeopleNumberText();
        if (!"".equals(oldValue)) {
            setPeopleNumberText(oldValue.substring(0, oldValue.length() - 1));
        }
    }

    public void showPeopleNumberError(int stringId) {
        mCustomerPeopleNumber.requestFocus();
        currentEditText = EditTextType.PEOPLE_NUMBER;
        showTelephoneKeyboard();
        mCustomerPeopleNumber.setError(getResources().getString(stringId));
    }
}
