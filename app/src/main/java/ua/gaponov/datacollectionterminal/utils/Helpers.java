package ua.gaponov.datacollectionterminal.utils;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class Helpers {

    public static Context context;
    public static SharedPreferences mSharedPref;

    public static String host;
    public static String c1UserName;
    public static String c1UserPassword;
    public static String c1ShopId;

    public static void getOptions() {
        mSharedPref = getDefaultSharedPreferences(context);
        host = mSharedPref.getString("HOST_NAME", "");
        c1UserName = mSharedPref.getString("USER_NAME", "");
        c1UserPassword = mSharedPref.getString("USER_PASS", "");
        c1ShopId = mSharedPref.getString("SHOP_ID", "");
    }
}
