package com.example.android.crypcy;

/**
 * Created by user on 10/28/2017.
 */



public class CurrencyBoard {

    // state for the base currency
    private String mBaseCurrency;

    // state for the ethereum equivalent
    private double mEthEquivalent;

    // state for the bitcoin equivalent
    private double mBtcEquivalent;


    /*
    * Constructor for creating instances or objects of this class
    * The private fields are all initializeed with the consturctor
     */
    public CurrencyBoard(String baseCurrency, double ethEquivalent, double btcEquivalent){

        mBaseCurrency = baseCurrency;
        mEthEquivalent = ethEquivalent;
        mBtcEquivalent = btcEquivalent;

    }


    // method for getting the base currency
    public String getBaseCurrency(){

        return mBaseCurrency;
    }

    // method for getting the ethereum equivalent
    public double getEthEquivalent(){

        return mEthEquivalent;
    }


    // method for getting the bitcoin equivalent
    public  double getBtcEquivalent(){

        return mBtcEquivalent;
    }



}
