package com.example.santa.rotatablefanchart;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by santa on 16/8/24.
 */
public class FanChart extends View {
    private Paint mPaint;
    private List<FanChartItem> mCharts;
    private float mPointAngle = 90;
    private int mStrokeWidth = 80;
    private OnRotationListener mListener;
    DecimalFormat df = new DecimalFormat("###.00");

    public FanChart(Context context) {
        this(context, null);
    }

    public FanChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FanChart(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FanChart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mStrokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mStrokeWidth, dm);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FanChart);
        mStrokeWidth = array.getDimensionPixelSize(R.styleable.FanChart_fc_strokeWidth, mStrokeWidth);
        array.recycle();

        initPaint();
    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(Color.GREEN);
    }

    public void setStrokeWidth(int strokeWidth) {
        mPaint.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public void setCharts(List<FanChartItem> list) {
        mCharts = list;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        mIndicator.setCenterPoint(getWidth()/2, getHeight()/2);
    }


    @Override
    public void setRotation(float rotation) {
        super.setRotation(rotation);

        if (null != mListener) {
            FanChartItem item = getCurItem(rotation);
            if (null != item) {
                float percent = 100*item.getCount()/FanChartItem.getCountSum();
                mListener.setText(item.getName(), df.format(percent)+"%");
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int offset = (int) (mPaint.getStrokeWidth()/2);

        float curStartAngle = 0;
        for (FanChartItem item : mCharts) {
            mPaint.setColor(item.getColor());
            float sweepAngle = 360*item.getCount()/ FanChartItem.getCountSum();
            item.setAngle(curStartAngle, curStartAngle+sweepAngle);
            RectF rectF = new RectF(offset, offset, getWidth() - offset, getHeight() - offset);
            canvas.drawArc(rectF, curStartAngle, sweepAngle+2, false, mPaint);
            curStartAngle += sweepAngle;
        }
        rotateToMid();
    }

    private FanChartItem getCurItem(float rotation) {
        float pointAngleOffset = mPointAngle - rotation;
        pointAngleOffset = pointAngleOffset%360;
        if (pointAngleOffset < 0) {
            pointAngleOffset += 360;
        }
        for (FanChartItem item : mCharts) {
            if (item.getEndAngle() > pointAngleOffset && item.getStartAngle() < pointAngleOffset) {
                return item;
            }
        }
        return null;
    }

    public void rotateToMid(){
        float rotation = getRotation();
        FanChartItem item = getCurItem(rotation);
        if (null != item) {
            float start = rotation%360;
            float target = mPointAngle - (item.getStartAngle() + item.getEndAngle())/2;
            if (Math.abs(target - start) > Math.abs(item.getEndAngle() - item.getStartAngle())) {
                if (target > start) {
                    target -= 360;
                } else {
                    target += 360;
                }
            }
            ValueAnimator anim = ObjectAnimator.ofFloat(start, target);
            anim.setDuration(300);
            anim.setInterpolator(new DecelerateInterpolator());
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float val = (float) animation.getAnimatedValue();
                    setRotation(val);
                }
            });
            anim.start();
        }
    }

    public void setPointAngle(float pointAngle) {
        mPointAngle = pointAngle;
    }

    public void setOnRotationListener(OnRotationListener listener) {
        mListener = listener;
    }

    public interface OnRotationListener{
        void setText(String name, String percent);
    }


}
