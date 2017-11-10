package com.example.android.crypcy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CurrencyConverter extends AppCompatActivity {




    // ethereum equivalent variable
    private double ethereum;

    // bitcoin equivalent variable
    private double bitcoin;

    // tag
    private static final String LOG_TAG = "ConverterActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_converter);

        // here we get the intent first
        Intent intent = getIntent();

        // then we extract the bundle containing our data
        Bundle b = intent.getBundleExtra("dashBundle");

        // from the bundle we extract the symbol
        String symbol = b.getString("currencySymbol");

        // from the bundle we extract the ethereum equivalent
       ethereum = b.getDouble("ethEquivalent");


        // from the bundle we extract the bitcoine equivalent
         bitcoin = b.getDouble("btcEquivalent");



        // variable for text view
         TextView mBaseSymbol = (TextView) findViewById(R.id.base_symbol);
        mBaseSymbol.setText(symbol);


    }

    /**
     * Converts inputted amount from the user to its eth and btc equivalent
     */
    public void convertAmount (View view){

        // variable for edit text view
        EditText mInputCurrency = (EditText) findViewById(R.id.retrieve_amount);


        // input amount by user
        double convertingAmount = ParseDouble(String.valueOf(mInputCurrency.getText()));

        double amountToEth = convertingAmount / ethereum;

      double amountToBtc = convertingAmount / bitcoin;



        // variable for eth text view
        TextView mBasetoEth = (TextView) findViewById(R.id.base_to_eth);
        mBasetoEth.setText("ETH " + Double.toString(amountToEth));


        // variable for btc text view
        TextView mBasetoBtc = (TextView) findViewById(R.id.base_to_btc);
        mBasetoBtc.setText("BTC " + Double.toString(amountToBtc) );

    }


    private  double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;
            }
        }
        else return 0;
    }


}





