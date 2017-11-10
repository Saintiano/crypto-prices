package com.example.android.crypcy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 10/28/2017.
 */

public class CurrencyBoardAdapter extends ArrayAdapter<CurrencyBoard> {



    private static final String LOG_TAG = CurrencyBoardAdapter.class.getSimpleName();




    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context The current context. Used to inflate the layout file.
     * CurrencyBoard  A List of QuakeInfo objects to display in a list
     */
    public CurrencyBoardAdapter(Activity context, ArrayList<CurrencyBoard> dashboard) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, dashboard);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.currency_board, parent, false);
        }



        // Create a new DeveloperInfo object and Get the {@link DeveloperInfo} object located at this position in the list
        CurrencyBoard currentDashboard = getItem(position);




        // Find the text view in the dashboard_item.xml layout with the ID profile_name
        TextView baseCurrency = (TextView) listItemView.findViewById(R.id.base_currency);
        // display the base currency of the particular dashboard item
        baseCurrency.setText(currentDashboard.getBaseCurrency());


        // Find the text view in the dashboard_item.xml layout with the ID profile_name
        TextView ethereumEquivalent = (TextView) listItemView.findViewById(R.id.eth_equivalent);
        // display the base currency of the particular dashboard item
        // Notice that the double value had to be converted to a string instance before being used as parameter to setText
        ethereumEquivalent.setText(Double.toString(currentDashboard.getEthEquivalent()));


        // Find the text view in the dashboard_item.xml layout with the ID profile_name
        TextView bitcoinEquivalent = (TextView) listItemView.findViewById(R.id.btc_equivalent);
        // display the base currency of the particular dashboard item
        // Notice that the double value had to be converted to a string instance before being used as parameter to setText
        bitcoinEquivalent.setText(Double.toString(currentDashboard.getBtcEquivalent()));



        return  listItemView;
    }





}
