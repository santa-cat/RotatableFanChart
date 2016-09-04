package com.example.santa.rotatablefanchart;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by santa on 16/8/25.
 */
public class ChartLayout extends RelativeLayout {

    private FanChart mChart;
    private InnerPointViewBase mPoint;
    private Indicator mIndicator;

    public ChartLayout(Context context) {
        this(context, null);
    }

    public ChartLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ChartLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        mIndicator = new Indicator();

    }

    @Override
    protected void onFinishInflate() {
        int count = getChildCount();
        if (count != 2) {
            throw new IllegalStateException("do not support more than 2 children");
        }
        for(int i = 0 ; i < count; i++) {
            View view = getChildAt(i);
            if (view instanceof InnerPointViewBase) {
                mPoint = (InnerPointViewBase) view;
            } else {
                mChart = (FanChart) view;
            }
        }
        mChart.setPointAngle(mPoint.getDireation());
        mChart.setOnRotationListener(new FanChart.OnRotationListener() {
            @Override
            public void setText(String name, String percent) {
                mPoint.setText(name, percent);
            }
        });
        super.onFinishInflate();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mIndicator.setCenterPoint(getWidth()/2, getHeight()/2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.d("DEBUG", "event.getX()= "+event.getX());
//                Log.d("DEBUG", "event.getY()= "+event.getY());
                mIndicator.setLastPoint(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                double sweepAngle = mIndicator.getSweepAngle(new PointF(event.getX(), event.getY()));
                mChart.setRotation((float) (mChart.getRotation()+sweepAngle));

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mChart.rotateToMid();
                break;
        }

        return true;
    }

}
