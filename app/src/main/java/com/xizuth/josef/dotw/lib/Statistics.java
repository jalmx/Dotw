package com.xizuth.josef.dotw.lib;

import java.util.ArrayList;

/**
 * Created by josef on 10/22/17.
 * Lib for calculate Statistics basics
 */

public final class Statistics {

    private Statistics() {
    }

    public static double mean(ArrayList<Double> listData){
        double sum = 0;

        for (Double data : listData) {
            sum+= data;
        }

        return sum / listData.size();
    }

    public static double meanDesviation(ArrayList<Double> listData, double mean){
        double sum = 0;

        for (Double data : listData) {
            sum+= Math.pow((mean - data),2);
        }

        return sum / listData.size();
    }

    public static double meanDesviation(ArrayList<Double> listData){
        double mean = mean(listData);
        double sum = 0;

        for (Double data : listData) {
            sum+= Math.pow((mean - data),2);
        }

        return sum / listData.size();
    }
}
