package za.co.dvt.weather.myweatherapp;

import android.Manifest;
import android.app.Service;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import za.co.dvt.weather.controllers.WeatherFeedController;
import za.co.dvt.weather.helpers.AppHelper;
import za.co.dvt.weather.helpers.CustomSharedPreference;

public class WeatherActivity extends AppCompatActivity implements LocationListener{

    private static final String TAG = WeatherActivity.class.getSimpleName();
    private CustomSharedPreference sharedPreference;
    private final int REQUEST_LOCATION = 200;
    private LocationManager locationManager;
    private Location location;
    private String isLocationSaved;
    private String apiUrl;
    private RequestQueue queue;
    private Typeface weatherFont;
    private TextView cityField, detailsField, currentMaxTemperatureField, currentMinTemperatureField, weatherIcon, dateTimeField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        try {
            weatherFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/weathericons-regular-webfont.ttf");

            cityField = (TextView) findViewById(R.id.city_field);
            detailsField = (TextView) findViewById(R.id.details_field);
            dateTimeField = (TextView) findViewById(R.id.date_time_field);
            currentMinTemperatureField = (TextView) findViewById(R.id.min_temperature_field);
            currentMaxTemperatureField = (TextView) findViewById(R.id.max_temperature_field);
            weatherIcon = (TextView) findViewById(R.id.weather_icon);
            weatherIcon.setTypeface(weatherFont);
            locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
            queue = Volley.newRequestQueue(this);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(WeatherActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            } else {
                //if(isLocationSaved.equals("")){
                // make API call with longitude and latitude
                try {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5100, 5, this);
                    if (locationManager != null) {

                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        WeatherFeedController.placeIdTask asyncTask = new WeatherFeedController.placeIdTask(new WeatherFeedController.AsyncResponse() {
                            public void processFinish(String weather_city, String weather_description, String min_temp, String max_temp, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {
                                try {
                                    dateTimeField.setText("TODAY, " + getTodayDateInStringFormat());
                                    cityField.setText(weather_city);
                                    detailsField.setText(weather_description);
                                    currentMaxTemperatureField.setText("max " + max_temp + "C");
                                    currentMinTemperatureField.setText("min " + min_temp + "C");
                                    weatherIcon.setText(Html.fromHtml(weather_iconText));
                                } catch (Exception ex) {
                                    Log.e(TAG, "Problem displaying weather data from api: " + ex.getMessage());
                                }
                            }
                        });
                        asyncTask.execute(location.getLatitude() + "", location.getLongitude() + ""); //  asyncTask.execute("Latitude", "Longitude")
                    }
                } catch (Exception ex) {
                    Log.e(TAG, "Problem getting permissions and getting weather: " + ex.getMessage());

                }
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Problem setting font: " + ex.getMessage());
        }
    }

    private String getTodayDateInStringFormat(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return df.format(c.getTime());
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(WeatherActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

}
