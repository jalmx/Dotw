package com.xizuth.josef.dotw;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xizuth.josef.dotw.admob.AdMob;
import com.xizuth.josef.dotw.lib.ParserDataList;
import com.xizuth.josef.dotw.lib.RangeData;
import com.xizuth.josef.dotw.lib.Statistics;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static int ERROR_NO_DATA = 1;
    private final static int ERROR_INCORRECT_DATA = 2;
    private Button calculateButton;
    private TextView meanText,
            deviationText,
            intervalText,
            modeText,
            medianText,
            varianceText,
            cvText,
            rangeText;
    private EditText dataText;
    private TextView nDataText;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_manu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item_clear:
                clearUI();
                return true;
            /*case R.id.item_formula:

                return true;
            case R.id.item_about:

              return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews() {

        calculateButton = (Button) findViewById(R.id.calculate_button);
        dataText = (EditText) findViewById(R.id.data_text);
        nDataText = (TextView) findViewById(R.id.n_data);
        meanText = (TextView) findViewById(R.id.mean_text);
        medianText = (TextView) findViewById(R.id.median_text);
        modeText = (TextView) findViewById(R.id.mode_text);
        deviationText = (TextView) findViewById(R.id.mean_deviation);
        varianceText = (TextView) findViewById(R.id.variance_text);
        cvText = (TextView) findViewById(R.id.cv_text);
        rangeText = (TextView) findViewById(R.id.range_text);
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
                Log.e("MAIN", "calculateValues: ERROR", e);
            }

        } else {
            messageError(ERROR_NO_DATA);
        }
    }

    private void setValuesUI(ArrayList<Double> listData) {
        setMean(listData);
        setMedian(listData);
        setMode(listData);
        setDeviation(listData);
        setVariance(listData);
        setCV(listData);
        setRange(listData);
        setInterval(listData);
        setNData(listData);
    }

    private void clearUI() {
        TextView[] textViews = {dataText, nDataText, meanText, medianText, modeText, varianceText,
                deviationText, cvText, rangeText, intervalText};
        for (TextView tv: textViews){
            tv.setText("");
        }
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

    private void setNData(List<Double> listData) {
        String textNData = getText(R.string.no_data) + ": ";
        int nValue = listData.size();
        textNData += String.format("%d", nValue);
        nDataText.setText(textNData);
    }

    private void setMean(ArrayList<Double> listData) {
        String textMean = getText(R.string.average) + ": ";
        double meanValue = Statistics.mean(listData);
        textMean += String.format("%.2f", meanValue);
        meanText.setText(textMean);
    }

    private void setMedian(ArrayList<Double> listData) {
        String textMedian = getText(R.string.median) + ": ";
        double medianValue = Statistics.median(listData);
        textMedian += String.format("%.2f", medianValue);
        medianText.setText(textMedian);
    }

    private void setMode(ArrayList<Double> listData) {
        String textMode = getText(R.string.mode) + ": ";
        double modeValue = Statistics.mode(listData);

        if (modeValue == 0) {
            textMode += "-";
        } else {
            textMode += String.format("%.2f", modeValue);
        }

        modeText.setText(textMode);
    }

    private void setDeviation(ArrayList<Double> listData) {
        String textDeviation = getText(R.string.mean_deviation) + ": ";
        double deviationValue = Statistics.meanDeviation(listData);
        textDeviation += String.format("%.2f", deviationValue);
        deviationText.setText(textDeviation);
    }

    private void setVariance(List<Double> listData) {
        String textVariance = getText(R.string.variance) + ": ";
        double varianceValue = Statistics.variance(listData);
        textVariance += String.format("%.2f", varianceValue);
        varianceText.setText(textVariance);
    }

    private void setCV(List<Double> listData) {
        String textCV = getText(R.string.cv) + ": ";
        double cvValue = Statistics.covariance(listData);
        textCV += String.format("%.2f", cvValue);
        cvText.setText(textCV);
    }

    private void setRange(List<Double> listData) {
        RangeData rangeData = RangeData.getInstance(listData);

        String textRange = getText(R.string.range) + ": ";
        double rangeValue = RangeData.getInstance(listData).getRange();
        textRange += String.format("%.2f", rangeValue);

        String textMin = getText(R.string.min) + ": ";
        double minValue = RangeData.getInstance(listData).getMinData();
        textMin += String.format("%.2f", minValue);

        String textMax = getText(R.string.max) + ": ";
        double maxValue = RangeData.getInstance(listData).getMaxData();
        textMax += String.format("%.2f", maxValue);

        rangeText.setText(String.format("%s %s %s", textRange, textMin, textMax));
    }

    private void setInterval(ArrayList<Double> listData) {
        String textInterval = getString(R.string.mean_mean_deviation);
        double meanValue = Statistics.mean(listData);
        double deviationValue = Statistics.meanDeviation(listData);
        double down = meanValue - deviationValue;
        double up = meanValue + deviationValue;

        textInterval = String.format("%s: %.2f , %.2f", textInterval, down, up);
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
