package com.example.android.crypcy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    public static final String LOG_TAG = MainActivity.class.getName();

    /** Adapter for the list of currency comaprison */
    private static CurrencyBoardAdapter mAdapter;

    /** URL for cryptocurrency price comparison data from cryptocompare site site */
    private static final String CRYPTOCOMPARE_REQUEST_URL =" https://min-api.cryptocompare.com/data/pricemulti?fsyms=ETH,BTC&tsyms=NGN,CAD,CNY,BND,EUR,USD,AUD,CHF,DKK,GHS,HKD,INR,JPY,KZT,NAD,NZD,OMR,RUB,SAR,SGD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find a reference to the {@link ListView} in the layout
        ListView currencyListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        currencyListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new CurrencyBoardAdapter(this, new ArrayList<CurrencyBoard>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        currencyListView.setAdapter(mAdapter);


        // Set an item click listener on the ListView, which sends an intent to open another acitivity to display the profile details

        currencyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // we create a new currencydashboard object that will be used to retrieve data of the current clicked item
                CurrencyBoard currencyboardItem = mAdapter.getItem(position);


                // we initiate an intent to call the Coverter activity
                Intent intent = new Intent(getApplicationContext(), CurrencyConverter.class);

                // Here we use the bundle as the data container
                Bundle b = new Bundle();

                //Inserting data into the bundle
                b.putString("currencySymbol", currencyboardItem.getBaseCurrency());
                b.putDouble("ethEquivalent", currencyboardItem.getEthEquivalent());
                b.putDouble("btcEquivalent", currencyboardItem.getBtcEquivalent());

                // inserting bundle into the intent
                intent.putExtra("dashBundle", b);

                // Send the intent to launch a new activity
                startActivity(intent);
            }
        });


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();



        if (networkInfo != null && networkInfo.isConnected()) {

            // Start the AsyncTask to fetch the earthquake data
            CryptoCurrencyAsyncTask task = new CryptoCurrencyAsyncTask();
            task.execute(CRYPTOCOMPARE_REQUEST_URL);

        } else {

            //otherwise , display error
            // First, hide loading indicator so error message will be visible

            // Hide loading indicator because the data has been loaded
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Set empty state text to display "No earthquakes found."
            mEmptyStateTextView.setText(R.string.no_internet_connection);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class CryptoCurrencyAsyncTask extends AsyncTask<String, Void, List<CurrencyBoard>> {


        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of
         * {@link CurrencyBoard}s as the result.
         */

        @Override
        protected List<CurrencyBoard> doInBackground(String... urls) {

            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<CurrencyBoard> result = CurrencyQuery.fetchDashboardData(urls[0]);
            return result;

        }

        @Override
        protected void onPostExecute(List<CurrencyBoard> data) {

            // Hide loading indicator because the data has been loaded
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Set empty state text to display "No _developers found)
            mEmptyStateTextView.setText(R.string.no_currency);

            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }

        }
    }

}
