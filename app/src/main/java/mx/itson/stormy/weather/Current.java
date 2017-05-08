package mx.itson.stormy.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Lagunaisw on 5/09/2017.
 */
public class Current {
    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mTemperatureMin;
    private double mTemperatureMax;
    private double mHumidity;
    private double mPrecipChance;
    private String mWindBearing;
    private String mSummary;
    private long mSunrise;
    private long mSunset;

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    private String mTimeZone;

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }


    public String getWindBearing() {
        return mWindBearing;
    }

    public void setWindBearing(double windBearing) {
        if (348.75 < windBearing && windBearing > 11.25) {
            this.mWindBearing = "N";
        }
        if (11.25 < windBearing && windBearing > 33.75) {
            this.mWindBearing = "NNE";
        }
        if (56.25 < windBearing && windBearing > 78.75) {
            this.mWindBearing = "NE";
        }
        if (33.75 < windBearing && windBearing > 56.25) {
            this.mWindBearing = "ENE";
        }
        if (78.75 < windBearing && windBearing > 101.25) {
            this.mWindBearing = "E";
        }
        if (101.25 < windBearing && windBearing > 123.75) {
            this.mWindBearing = "ESE";
        }
        if (123.75 < windBearing && windBearing > 146.25) {
            this.mWindBearing = "SE";
        }
        if (146.25 < windBearing && windBearing > 168.75) {
            this.mWindBearing = "SSE";
        }
        if (168.75 < windBearing && windBearing > 191.25) {
            this.mWindBearing = "S";
        }
        if (191.25 < windBearing && windBearing > 213.75) {
            this.mWindBearing = "SSO";
        }
        if (213.75 < windBearing && windBearing > 236.25) {
            this.mWindBearing = "SO";
        }
        if (236.25 < windBearing && windBearing > 258.75) {
            this.mWindBearing = "OSO";
        }
        if (258.75 < windBearing && windBearing > 281.25) {
            this.mWindBearing = "O";
        }
        if (281.25 < windBearing && windBearing > 303.75) {
            this.mWindBearing = "ONO";
        }
        if (303.75 < windBearing && windBearing > 326.25) {
            this.mWindBearing = "NO";
        }
        if (326.25 < windBearing && windBearing > 348.75) {
            this.mWindBearing = "NNO";
        }

    }


    public String getIcon() {
        return mIcon;
    }

    public long getIconId() {
        return Forecast.getIconId(mIcon);
    }

    public String getFormatedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        Date dateTime = new Date(getTime() * 1000);

        return formatter.format(dateTime);
    }

    public String getFormatedSunriseTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        Date dateTime = new Date(getSunrise() * 1000);

        return formatter.format(dateTime);
    }

    public String getFormatedSunsetTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        Date dateTime = new Date(getSunset() * 1000);

        return formatter.format(dateTime);
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTime() {
        return mTime;
    }

    public long getSunrise() {
        return mSunrise;
    }

    public long getSunset() {
        return mSunset;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public void setSunrise(long time) {
        mSunrise = time;
    }

    public void setSunset(long time) {
        mSunset = time;
    }

    public int getTemperature() {
        return (int) Math.round(mTemperature);
    }

    public int getTemperatureMin() {
        return (int) Math.round(mTemperatureMin);
    }

    public int getTemperatureMax() {
        return (int) Math.round(mTemperatureMax);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public void setTemperatureMin(double temperature) {
        mTemperatureMin = temperature;
    }

    public void setTemperatureMax(double temperature) {
        mTemperatureMax = temperature;
    }

    public int getHumidity() {
        return (int) Math.round(mHumidity * 100);
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public int getPrecipChance() {
        return (int) Math.round(mPrecipChance * 100);
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

}
