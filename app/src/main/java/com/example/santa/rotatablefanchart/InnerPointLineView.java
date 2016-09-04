package com.example.santa.rotatablefanchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by santa on 16/8/24.
 */
public class InnerPointLineView extends InnerPointViewBase {
    private Paint mPaint;
    private int mStorkeWidth = 2;

    public InnerPointLineView(Context context) {
        this(context, null);
    }

    public InnerPointLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InnerPointLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public InnerPointLineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(0x00000000);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mStorkeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mStorkeWidth, dm);

        initPaint();
        super.setDireation(270);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mStorkeWidth);
        mPaint.setColor(Color.WHITE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int radius = width/2;
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(width/2, height/2, radius, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawLine(width/2, 10, width/2, height/5, mPaint);
    }

}
