package edu.calvin.akg8.lab02;

import android.app.Activity;
import android.content.SharedPreferences; // Import for save feature
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;  // Import the text widgets
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;  // Import the listener

import java.text.NumberFormat;


public class TipCalculatorActivity extends Activity
    implements OnEditorActionListener, OnClickListener {  // implement the listeners

    // Declare variables for the widgets
    private EditText valueEditText;
    private TextView resultTextView;
    private Button calculateButton;

    // Define an instance variable for result;
    private String valueString = "";

    // Define SharedPreferences object
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);

        // Get references to the widgets
        valueEditText = (EditText) findViewById(R.id.valueEditText);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        calculateButton = (Button) findViewById(R.id.calculateButton);

        // Set the listener
        calculateButton.setOnClickListener(this);
        valueEditText.setOnEditorActionListener(this);

        // get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public void onPause() {
        // save the instance variables
        Editor editor = savedValues.edit();
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        //calculate and display
        calculateAndDisplay();
    }

    public void calculateAndDisplay() {

        // get the value
        valueString = valueEditText.getText().toString();
        float value;
        if (valueString.equals("")) {
            value = 0;
        }
        else {
            value = Float.parseFloat(valueString);
        }

        //calculate and display the result
        float result = value * 2;

        //Display with formatting
        NumberFormat number = NumberFormat.getNumberInstance();
        resultTextView.setText(number.format(result));
    }

    // Implement the listener
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
        {
            calculateAndDisplay();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        calculateAndDisplay();
    }
}
