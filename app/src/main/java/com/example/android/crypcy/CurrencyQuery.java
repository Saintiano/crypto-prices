package com.example.android.crypcy;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by user on 10/28/2017.
 */

public final class CurrencyQuery {

    /** Tag for the log messages */
    private static final String LOG_TAG = CurrencyQuery.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link CurrencyQuery} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private CurrencyQuery() {
    }

    /**
     * Query the cryptocampare data site and return a {@link CurrencyBoard} object to represent a dashboard of price equivalents.
     */

   public static ArrayList<CurrencyBoard> fetchDashboardData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<CurrencyBoard> currencyboard = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return currencyboard;
    }

    /**
     * Returns new URL object from the given string URL.
     */

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the currency price JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * Return a list of {@link CurrencyBoard} objects that has been built up from
     * parsing a JSON response.
     */
    private static ArrayList<CurrencyBoard> extractFeatureFromJson(String currencyboardJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(currencyboardJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding dashboard objects  to
        ArrayList<CurrencyBoard> currencyboards = new ArrayList<>();

        // Try to parse the JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception erro object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // convert our sample response JSON into an intelligent JSON object

            JSONObject response = new JSONObject(currencyboardJSON);

            // parsing or extracting out the two JSON Objects of crypto currency
            JSONObject ethereumPrice = response.getJSONObject("ETH");
            JSONObject bitcoinPrice = response.getJSONObject("BTC");

            // getting the currency symbols
            JSONArray symbols = ethereumPrice.names();


            // Create an empty ArrayList that we can store the currency symbols to after iterating through the JSONArray
            ArrayList<String> currencySymbols = new ArrayList<>();

            for ( int i = 0; i < symbols.length(); i++){

                currencySymbols.add(symbols.getString(i));
            }


            // Naira crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double nairaToETH = ethereumPrice.getDouble("NGN");
            double nairaToBTC = bitcoinPrice.getDouble("NGN");
            String nairaSymbol = currencySymbols.get(0);
            currencyboards.add(new CurrencyBoard(nairaSymbol,nairaToETH,nairaToBTC));

            // Canadian dolloar crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double canadaToETH = ethereumPrice.getDouble("CAD");
            double canadaToBTC = bitcoinPrice.getDouble("CAD");
            String canadaSymbol = currencySymbols.get(1);
            currencyboards.add(new CurrencyBoard(canadaSymbol,canadaToETH,canadaToBTC));


            // Chinese yuan crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double chinaToETH = ethereumPrice.getDouble("CNY");
            double chinaToBTC = bitcoinPrice.getDouble("CNY");
            String chinaSymbol = currencySymbols.get(2);
            currencyboards.add(new CurrencyBoard(chinaSymbol,chinaToETH,chinaToBTC));


            // Brunei dollar crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double bruneiToETH = ethereumPrice.getDouble("BND");
            double bruneiToBTC = bitcoinPrice.getDouble("BND");
            String bruneiSymbol = currencySymbols.get(3);
            currencyboards.add(new CurrencyBoard(bruneiSymbol,bruneiToETH,bruneiToBTC));


            // Euro crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double euroToETH = ethereumPrice.getDouble("EUR");
            double euroToBTC = bitcoinPrice.getDouble("EUR");
            String euroSymbol =currencySymbols.get(4);
            currencyboards.add(new CurrencyBoard(euroSymbol,euroToETH,euroToBTC));


            // American dollars crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double dollarToETH = ethereumPrice.getDouble("USD");
            double dollarToBTC = bitcoinPrice.getDouble("USD");
            String dollarSymbol = currencySymbols.get(5);
            currencyboards.add(new CurrencyBoard(dollarSymbol,dollarToETH,dollarToBTC));


            // Australian dollars crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double aussieToETH = ethereumPrice.getDouble("AUD");
            double aussieToBTC = bitcoinPrice.getDouble("AUD");
            String aussieSymbol = currencySymbols.get(6);
            currencyboards.add(new CurrencyBoard(aussieSymbol,aussieToETH,aussieToBTC));


            // Swiss franc crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double swissToETH = ethereumPrice.getDouble("CHF");
            double swissToBTC = bitcoinPrice.getDouble("CHF");
            String swissSymbol = currencySymbols.get(7);
            currencyboards.add(new CurrencyBoard(swissSymbol,swissToETH,swissToBTC));


            // Danish krone crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double kroneToETH = ethereumPrice.getDouble("DKK");
            double kroneToBTC = bitcoinPrice.getDouble("DKK");
            String kroneSymbol = currencySymbols.get(8);
            currencyboards.add(new CurrencyBoard(kroneSymbol,kroneToETH,kroneToBTC));


            // Ghanaian Cedi crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double cediToETH = ethereumPrice.getDouble("GHS");
            double cediToBTC = bitcoinPrice.getDouble("GHS");
            String cediSymbol = currencySymbols.get(9);
            currencyboards.add(new CurrencyBoard(cediSymbol,cediToETH,cediToBTC));


            // Honk kong dollar crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double hongToETH = ethereumPrice.getDouble("HKD");
            double hongToBTC = bitcoinPrice.getDouble("HKD");
            String hongSymbol = currencySymbols.get(10);
            currencyboards.add(new CurrencyBoard(hongSymbol,hongToETH,hongToBTC));


            // Indian Rupee crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double rupeeToETH = ethereumPrice.getDouble("INR");
            double rupeeToBTC = bitcoinPrice.getDouble("INR");
            String rupeeSymbol = currencySymbols.get(11);
            currencyboards.add(new CurrencyBoard(rupeeSymbol,rupeeToETH,rupeeToBTC));


            // Japanese Yes crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double yenToETH = ethereumPrice.getDouble("JPY");
            double yenToBTC = bitcoinPrice.getDouble("JPY");
            String yenSymbol = currencySymbols.get(12);
            currencyboards.add(new CurrencyBoard(yenSymbol,yenToETH,yenToBTC));


            // Kazaksthan tenge crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double tengeToETH = ethereumPrice.getDouble("KZT");
            double tengeToBTC = bitcoinPrice.getDouble("KZT");
            String tengeSymbol = currencySymbols.get(13);
            currencyboards.add(new CurrencyBoard(tengeSymbol,tengeToETH,tengeToBTC));


            // Namibian dollar crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double namibiaToETH = ethereumPrice.getDouble("NAD");
            double namibiaToBTC = bitcoinPrice.getDouble("NAD");
            String namibiaSymbol = currencySymbols.get(14);
            currencyboards.add(new CurrencyBoard(namibiaSymbol,namibiaToETH,namibiaToBTC));


            // New Zealand dollar crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double newZeaToETH = ethereumPrice.getDouble("NZD");
            double newZeaToBTC = bitcoinPrice.getDouble("NZD");
            String newZeaSymbol = currencySymbols.get(15);
            currencyboards.add(new CurrencyBoard(newZeaSymbol,newZeaToETH,newZeaToBTC));


            // Omani rial crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double omaniToETH = ethereumPrice.getDouble("OMR");
            double omaniToBTC = bitcoinPrice.getDouble("OMR");
            String omaniSymbol = currencySymbols.get(16);
            currencyboards.add(new CurrencyBoard(omaniSymbol,omaniToETH,omaniToBTC));


            // Russian rubble crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double rubbleToETH = ethereumPrice.getDouble("RUB");
            double rubbleToBTC = bitcoinPrice.getDouble("RUB");
            String rubbleSymbol = currencySymbols.get(17);
            currencyboards.add(new CurrencyBoard(rubbleSymbol,rubbleToETH,rubbleToBTC));


            // Saudi rial crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double saudiToETH = ethereumPrice.getDouble("SAR");
            double saudiToBTC = bitcoinPrice.getDouble("SAR");
            String saudiSymbol = currencySymbols.get(18);
            currencyboards.add(new CurrencyBoard(saudiSymbol,saudiToETH,saudiToBTC));


            // Singapore dollar crypto equivalents
            // Using the symbols and price equivalents, we create a currencyboard object and add to the ArrayList of currencyboard
            double singaporeToETH = ethereumPrice.getDouble("SGD");
            double singaporeToBTC = bitcoinPrice.getDouble("SGD");
            String singaporeSymbol = currencySymbols.get(19);
            currencyboards.add(new CurrencyBoard(singaporeSymbol,singaporeToETH,singaporeToBTC));


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("CurrencyQuery", "Problem parsing the Currency CurrencyBoard JSON results", e);
        }

        // Return the list of earthquakes
        return currencyboards;
    }

}
