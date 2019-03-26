package com.app.testdemo.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.app.testdemo.interfaces.KeyInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Utility implements KeyInterface {

    /**
     * Internet connection available or not.
     */
    public static boolean isInternetAvailable(Context mContext) {
        boolean isNetworkAvailable = false;
        if (mContext != null) {
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (cm != null) {
                networkInfo = cm.getActiveNetworkInfo();
            }
            isNetworkAvailable = networkInfo != null && networkInfo.isConnected();
        }
        return isNetworkAvailable;
    }

    /**
     * Get converted date.
     */
    public static String convertDateFormat(String fromDate, String oldDateFormat, String newDateFormat) {
        SimpleDateFormat input = new SimpleDateFormat(oldDateFormat, Locale.getDefault());
        SimpleDateFormat output = new SimpleDateFormat(newDateFormat, Locale.getDefault());
        Date date = null;
        try {
            date = input.parse(fromDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted;
        if (date != null)
            formatted = output.format(date);
        else
            formatted = fromDate;
        return formatted;
    }

}