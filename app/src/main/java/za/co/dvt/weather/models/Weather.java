package za.co.dvt.weather.models;

/**
 * Created by NKOSINATHI J on 8/27/2017.
 */

public class Weather {
    public String maxTemp;
    public String minTemp;
    public String humidity;

    private Weather instance;
    private Weather()
    {
        instance = new Weather();
    }

    public Weather getInstance()
    {
        return instance;
    }

    public Weather(String maxTemp, String minTemp, String humidity) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.humidity = humidity;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }
}
