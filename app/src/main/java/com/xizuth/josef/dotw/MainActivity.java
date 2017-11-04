package com.xizuth.josef.dotw;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xizuth.josef.dotw.admob.AdMob;
import com.xizuth.josef.dotw.lib.ParserDataList;
import com.xizuth.josef.dotw.lib.Statistics;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static int ERROR_NO_DATA = 1;
    private final static int ERROR_INCORRECT_DATA = 2;
    private Button calculateButton;
    private TextView meanText, deviationText, intervalText;
    private EditText dataText;
    private AdMob adMob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        actionButton();
        actionEditText();
        adMob = new AdMob(this);
    }

    private void initViews() {

        calculateButton = (Button) findViewById(R.id.calculate_button);
        dataText = (EditText) findViewById(R.id.data_text);
        meanText = (TextView) findViewById(R.id.mean_text);
        deviationText = (TextView) findViewById(R.id.deviation_text);
        intervalText = (TextView) findViewById(R.id.interval_text);

    }

    private void actionButton() {
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateValues();
                hideKeyboard(view);
            }
        });
    }

    private void actionEditText() {
        dataText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    calculateValues();
                    hideKeyboard(textView);
                    return true;
                }
                return false;
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void calculateValues() {
        clearError();
        String textRaw = dataText.getText().toString();

        if (!textRaw.isEmpty()) {
            try {
                ArrayList<Double> list = ParserDataList.listData(textRaw);
                setValuesUI(list);
            } catch (Exception e) {
                messageError(ERROR_INCORRECT_DATA);
            }

        } else {
            messageError(ERROR_NO_DATA);
        }
    }

    private void setValuesUI(ArrayList<Double> listData) {
        setMean(listData);
        setDeviation(listData);
        setInterval(listData);
    }

    private void messageError(int typeError) {
        clearError();

        if (typeError == ERROR_INCORRECT_DATA) {
            dataText.setError(getText(R.string.error_data));
        }
        if (typeError == ERROR_NO_DATA) {
            dataText.setError(getText(R.string.no_data));
        }
    }

    private void setMean(ArrayList<Double> listData) {
        String textMean = getText(R.string.average) + ": ";
        double meanValue = Statistics.mean(listData);
        textMean += String.format("%.2f", meanValue);
        meanText.setText(textMean);
    }

    private void setDeviation(ArrayList<Double> listData) {
        String textDeviation = getText(R.string.mean_deviation) + ": ";
        double deviationValue = Statistics.meanDeviation(listData);
        textDeviation += String.format("%.2f", deviationValue);
        deviationText.setText(textDeviation);
    }

    private void setInterval(ArrayList<Double> listData) {
        double meanValue = Statistics.mean(listData);
        double deviationValue = Statistics.meanDeviation(listData);
        double down = meanValue - deviationValue;
        double up = meanValue + deviationValue;

        String textInterval = String.format("%.2f , %.2f", down, up);
        intervalText.setText(textInterval);
    }

    private void clearError() {
        dataText.setError(null);
    }

    @Override
    protected void onPause() {
        adMob.pauseAdMob();
        super.onPause();
    }

    @Override
    protected void onResume() {
        adMob.resumeAdMob();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        adMob.destroyAdMob();
        super.onDestroy();
    }

}
