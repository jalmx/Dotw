package com.xizuth.josef.dotw.lib;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by josef on 10/22/17.
 */

public final class ParserDataList {

    private ParserDataList() {
    }

    public static ArrayList<Double> listData(String dataText) {
        ArrayList<Double> data = new ArrayList<>();

        StringTokenizer tokenizer = new StringTokenizer(dataText, ",");

        while (tokenizer.hasMoreTokens()) {
            data.add(Double.parseDouble(tokenizer.nextToken()));
        }

        return data;
    }
}
