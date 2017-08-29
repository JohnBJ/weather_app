package za.co.dvt.weather.helpers;

/**
 * Created by NKOSINATHI J on 8/27/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

public class CustomSharedPreference {
    private SharedPreferences sharedPref;
    private Gson gson;
    public CustomSharedPreference(Context context) {
        sharedPref = context.getSharedPreferences(AppHelper.PREFERENCES_TAG, Context.MODE_PRIVATE);
    }

    public void setDataSourceIfPresent(boolean isData){
        sharedPref.edit().putBoolean(AppHelper.IS_DATA_PRESENT, isData).apply();
    }
    public boolean getDataSourceIfPresent(){
        return sharedPref.getBoolean(AppHelper.IS_DATA_PRESENT, false);
    }
    public void setLocationInPreference(String cityName){
        sharedPref.edit().putString(AppHelper.LOCATION_PREFS, cityName).apply();
    }
    public String getLocationInPreference(){
        return sharedPref.getString(AppHelper.LOCATION_PREFS, "");
    }
}