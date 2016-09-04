package com.example.santa.rotatablefanchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by santa on 16/8/24.
 */
public class InnerPointViewBase extends RelativeLayout {
    private TextView mType;
    private TextView mPercent;
    private int mTextSize = 25;
    private int mTextColor = Color.BLACK;
    private int mDireation = 90;

    public InnerPointViewBase(Context context) {
        this(context, null);
    }

    public InnerPointViewBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InnerPointViewBase(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public InnerPointViewBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, dm);

        LinearLayout linearLayout = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(CENTER_IN_PARENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        l.gravity = Gravity.CENTER;
        mType = new TextView(context);
        mType.setLayoutParams(l);
        mType.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mType.setTextColor(mTextColor);
        mType.setText("交通");

        mPercent = new TextView(context);
        mPercent.setLayoutParams(l);
        mPercent.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mPercent.setTextColor(mTextColor);
        mPercent.setText("80.00%");


        linearLayout.addView(mType);
        linearLayout.addView(mPercent);
        addView(linearLayout);

    }


    public void setText(String type, String percent) {
        mType.setText(type);
        mPercent.setText(percent);
    }

    public int getDireation() {
        return mDireation;
    }

    public void setDireation(int direation) {
        this.mDireation = direation;
    }
}
