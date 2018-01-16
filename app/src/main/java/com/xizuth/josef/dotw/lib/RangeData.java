package com.xizuth.josef.dotw.lib;

import java.util.List;

public class RangeData {

    private List<Double> dataList;
    private double maxData;
    private double minData;
    private double range;

    private static RangeData rangeData;

    private RangeData(List<Double> dataList) {
        this.dataList = dataList;
    }

    public static synchronized RangeData getInstance(List<Double> dataList) {

        if (rangeData == null) {
            rangeData = new RangeData(null);
        }
        rangeData.dataList = dataList;
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
        }

        this.minData = min;
        this.maxData = max;
        this.range = max - min;
    }

    public double getMaxData() {
        calculateData();
        return maxData;
    }

    public double getMinData() {
        calculateData();
        return minData;
    }

    public double getRange() {
        calculateData();
        return range;
    }


}
