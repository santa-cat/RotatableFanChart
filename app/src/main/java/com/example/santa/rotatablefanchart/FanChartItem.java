package com.example.santa.rotatablefanchart;

/**
 * Created by santa on 16/8/24.
 */
public class FanChartItem {
    private String name;
    private int color;
    private float count;
    private static float countSum = 0;
    private float startAngle;
    private float endAngle;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public static synchronized void addCount(float cnt){
        countSum += cnt;
    }

    public static synchronized float getCountSum() {
        return countSum;
    }

    public static synchronized void setCountSum(float countSum) {
        FanChartItem.countSum = countSum;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setAngle(float startAngle, float endAngle) {
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    public float getEndAngle() {
        return endAngle;
    }

}
