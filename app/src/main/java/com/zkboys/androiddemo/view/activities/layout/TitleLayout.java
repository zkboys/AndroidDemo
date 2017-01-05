package com.zkboys.androiddemo.view.activities.layout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zkboys.androiddemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TitleLayout extends LinearLayout {

    @Bind(R.id.btn_title_left)
    Button mTitleLeftButton;

    @Bind(R.id.tv_title_text)
    TextView mTitleText;
    @Bind(R.id.btn_title_right)
    Button mTitleRightButton;

    private ButtonClickListener mButtonClickListener;

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.title, this);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_title_left, R.id.btn_title_right})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_title_left:
                if (mButtonClickListener != null) {
                    mButtonClickListener.OnLeftButtonClick();
                } else {
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.btn_title_right:
                if (mButtonClickListener != null) {
                    mButtonClickListener.OnRightButtonClick();
                } else {
                    Toast.makeText(getContext(), "You clicked Right Button!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void setTitle(String title) {
        mTitleText.setText(title);
    }

    public void setButtonClickListener(ButtonClickListener buttonClickListener) {
        mButtonClickListener = buttonClickListener;
    }


    public interface ButtonClickListener {
        public void OnLeftButtonClick();

        public void OnRightButtonClick();
    }
}

