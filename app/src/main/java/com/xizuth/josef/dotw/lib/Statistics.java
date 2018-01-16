package com.xizuth.josef.dotw.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by josef on 10/22/17.
 * Lib for calculate Statistics basics
 */

public final class Statistics {

    private Statistics() {
    }

    public static double mean(List<Double> listData) {
        double sum = 0;

        for (Double data : listData) {
            sum += data;
        }

        return sum / listData.size();
    }

    public static double meanDeviation(List<Double> listData) {
        double mean = mean(listData);
        double sum = 0;

        for (Double data : listData) {
            sum += Math.pow((mean - data), 2);
        }

        return Math.sqrt(sum / listData.size());
    }

    public static double variance(List<Double> listData){
        double mean = mean(listData);
        double sum = 0;

        for (Double data : listData) {
            sum += Math.pow((mean - data), 2);
        }

        return sum / listData.size();
    }

    public static double meanDeviation(double varianza){
        return Math.sqrt(varianza);
    }

    public static double covariance(ArrayList<Double> listData) {
        return meanDeviation(listData) / mean(listData);
    }

    public static double mode(List<Double> listData) {

        double mode = 0;
        int maxTimeRepeat = 1;

        for (double data : listData) {
            int timesRepeat = 0;

            for (double dataTime : listData) {
                if (data == dataTime)
                    timesRepeat++;
            }

            if (timesRepeat > maxTimeRepeat) {
                mode = data;
                maxTimeRepeat = timesRepeat;
            }
        }

        return mode;
    }

    public static double median(ArrayList<Double> listData) {

        double median;

        Double[] listAux = listData.toArray(new Double[listData.size()]);
        Arrays.sort(listAux);

        if (listAux.length % 2 == 0) {
            int firstNumberHalf = listAux.length / 2;

            median = (listAux[firstNumberHalf] + listAux[firstNumberHalf + 1]) / 2;
        } else {
            median = listAux[listAux.length / 2];
        }

        return median;
    }

}
