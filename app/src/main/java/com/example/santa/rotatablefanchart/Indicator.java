package com.example.santa.rotatablefanchart;

import android.graphics.PointF;
import android.util.Log;

/**
 * Created by santa on 16/8/25.
 */
public class Indicator {
    private PointF mLastPoint;
    private PointF mCenterPoint;

    public Indicator(){
        mLastPoint = new PointF();
        mCenterPoint = new PointF();
    }

    public void setCenterPoint(float x, float y) {
        mCenterPoint.set(x, y);
    }

    public void setLastPoint(float x, float y) {
        mLastPoint.set(x, y);
    }

    public PointF getLastPoint() {
        return mLastPoint;
    }

    //计算转过的角度
    public double getSweepAngle(PointF curPoint) {
//        Log.d("DEBUG", "curPoint y= "+curPoint.y+"curPoint x= "+curPoint.x);
        double up = calculateSquare(mCenterPoint, curPoint) + calculateSquare(mCenterPoint, mLastPoint) - calculateSquare(curPoint, mLastPoint);

        double down = 2*calculateLength(mCenterPoint, curPoint)*calculateLength(mCenterPoint, mLastPoint);

        int dir = getDirection(mLastPoint, curPoint);
        mLastPoint.set(curPoint.x, curPoint.y);
        if (down == 0)  {
            return 0;
        }
        if (down < up) {
            down = up = 1;
        }


        return dir*Math.acos(up/down)*180/Math.PI;
    }


    private double calculateLength(PointF a, PointF b) {
        float square = calculateSquare(a, b);
        return Math.sqrt(square);
    }

    private float calculateSquare(PointF a, PointF b) {
        return Math.abs(a.x - b.x) * Math.abs(a.x - b.x) + Math.abs(a.y - b.y) * Math.abs(a.y - b.y);
    }



    private int getDirection(PointF oldP, PointF newP) {

        if (oldP.x == mCenterPoint.x || newP.x == mCenterPoint.x) {
            return 0;
        }

        if ((oldP.x - mCenterPoint.x)*(newP.x - mCenterPoint.x) <0) {
            return 0;
        }

        double kOld = (oldP.y - mCenterPoint.y)/(oldP.x - mCenterPoint.x);
        double kNew = (newP.y - mCenterPoint.y)/(newP.x - mCenterPoint.x);

        if (kNew > kOld) {
            return 1;
        } else if (kNew < kOld){
            return -1;
        } else {
            return 0;
        }
    }


}
