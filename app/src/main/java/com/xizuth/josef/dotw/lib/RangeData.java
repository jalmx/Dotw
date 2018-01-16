package com.xizuth.josef.dotw.lib;

import android.util.Log;

import java.util.List;

public class RangeData {

    private List<Double> dataList;
    private double maxData;
    private double minData;
    private double range;

    private static RangeData rangeData;

    private RangeData(List<Double> dataList) {
        this.dataList = dataList;
        calculateData();
    }

    public static synchronized RangeData getInstance(List<Double> dataList) {

        if (rangeData == null) {
            rangeData = new RangeData(dataList);
        }

        return rangeData;
    }

    private void calculateData() {
        double min = dataList.get(0), max = dataList.get(0);

        for (Double d : dataList) {
            if (d < min) {
                min = d;
            }
            if (d > max) {
                max = d;
            }
            Log.e("RANGE", "dato: " + d);
        }

        this.minData = min;
        this.maxData = max;
        this.range = max - min;
        Log.e("RANGE", "min: " + min);
        Log.e("RANGE", "max: " + max);
        Log.e("RANGE", "range: " + range);
    }

    public double getMaxData() {
        return maxData;
    }

    public double getMinData() {
        return minData;
    }

    public double getRange() {
        return range;
    }


}
