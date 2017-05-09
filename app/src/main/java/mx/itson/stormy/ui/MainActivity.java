package mx.itson.stormy.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;
import mx.itson.stormy.R;
import mx.itson.stormy.datasource.WeatherFromForecastIO;
import mx.itson.stormy.datasource.WeatherSource;
import mx.itson.stormy.datasource.WeatherSourceCallback;
import mx.itson.stormy.weather.Current;
import mx.itson.stormy.weather.Forecast;


public class MainActivity extends ActionBarActivity implements WeatherSourceCallback {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";
    public static final String HOURLY_FORECAST = "HOURLY_FORECAST";
    public static final String LOCATION_NAME = "LOCATION_NAME";
    private final WeatherSource mWeatherSource = new WeatherFromForecastIO(this);

    private Forecast mForecast;
    @InjectView(R.id.locationLabel)
    TextView mLocationLabel;
    @InjectView(R.id.temperatureLabel)
    TextView mTemperatureLabel;
    @InjectView(R.id.humidityValue)
    TextView mHumidityValue;
    @InjectView(R.id.precipValue)
    TextView mPrecipValue;
    @InjectView(R.id.summaryLabel)
    TextView mSummaryLabel;
    @InjectView(R.id.iconImageView)
    ImageView mIconImageView;
    @InjectView(R.id.refreshImageView)
    ImageView mRefreshImageView;
    @InjectView(R.id.progressBar)
    ProgressBar mProgressBar;
    @InjectView(R.id.minTempValue)
    TextView mMinTemp;
    @InjectView(R.id.maxTempValue)
    TextView mMaxTemp;
    @InjectView(R.id.windValue)
    TextView mWindeValue;
    @InjectView(R.id.sunriseValue)
    TextView mSunrise;
    @InjectView(R.id.sunsetValue)
    TextView mSunset;

    private double mLatitude = 27.9147934;
    private double mLongitude = -110.9430215;
    private String mLocationName = "Guaymas";
    private int mCity = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //start location service

        SmartLocation
                .with(this)
                .location()
                .provider(new LocationGooglePlayServicesWithFallbackProvider(this))
                .config(LocationParams.LAZY)
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        mLatitude = location.getLatitude();
                        mLongitude = location.getLongitude();
                        mLocationName = getLocationName(mLatitude, mLongitude);
                        // Load forecast only if it hasn't been loaded before (ie. showing
                        // placeholder text); otherwise wait for refresh button.
                        if (mTemperatureLabel.getText().toString().equals(getString(R.string.temperature_loading))) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    refreshForecast(mRefreshImageView);
                                }
                            });
                        }
                    }
                });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SmartLocation.with(this).location().stop();
    }

    public void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    public void updateDisplay() {
        Current current = mForecast.getCurrent();
        mTemperatureLabel.setText(current.getTemperature() + "");
        mHumidityValue.setText(current.getHumidity() + "%");
        mPrecipValue.setText(current.getPrecipChance() + "%");
        mSummaryLabel.setText(current.getSummary());
        mMinTemp.setText(current.getTemperatureMin() + "°");
        mMaxTemp.setText(current.getTemperatureMax() + "°");
        mWindeValue.setText(current.getWindBearing());
        mSunrise.setText(current.getFormatedSunriseTime() + "");
        mSunset.setText(current.getFormatedSunsetTime() + "");
        Drawable drawable = getResources().getDrawable((int) current.getIconId());
        mIconImageView.setImageDrawable(drawable);
    }

    public void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    @OnClick(R.id.refreshImageView)
    public void refreshForecast(View v) {


        if (isNetworkAvailable()) {
            toggleRefresh();
            if (mCity == 0) {
                mWeatherSource.getForecast(mLatitude, mLongitude);
                mLocationLabel.setText(mLocationName);
                mCity = 1;
            } else if (mCity == 1) {
                mWeatherSource.getForecast(19.3906797, -99.284042);
                mLocationLabel.setText("CDMX");
                mCity = 2;
            } else if (mCity == 2) {
                mWeatherSource.getForecast(20.6737776, -103.4056252);
                mLocationLabel.setText("Guadalajara");
                mCity = 0;
            }
        } else {
            Toast.makeText(this, getString(R.string.network_unavailable), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccess(Forecast forecast) {
        mForecast = forecast;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleRefresh();
                updateDisplay();
            }
        });
    }

    @Override
    public void onFailure(Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleRefresh();
                alertUserAboutError();
            }
        });
    }

    /**
     * Get the name of the city at the given map coordinates.
     *
     * @param latitude  Latitude of the location.
     * @param longitude Longitude of the location.
     * @return The localized name of the city.  If a geocoder isn't implemented on the device,
     * returns "Not Available". If the geocoder is implemented but fails to get an address,
     * returns "Not Found".
     */
    public String getLocationName(double latitude, double longitude) {

        String cityName = getString(R.string.unknow_city);
        if (Geocoder.isPresent()) {
            Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    cityName = address.getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cityName = getString(R.string.unreachable_city);
        }
        return cityName;
    }

    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view) {
        if (mForecast != null) {
            Intent intent = new Intent(this, DailyForecastActivity.class);
            intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast());
            intent.putExtra(LOCATION_NAME, mLocationName);
            startActivity(intent);
        }
    }

    @OnClick(R.id.hourlyButton)
    public void startHourlyActivity(View view) {
        if (mForecast != null) {
            Intent intent = new Intent(this, HourlyForecastActivity.class);
            intent.putExtra(HOURLY_FORECAST, mForecast.getHourlyForecast());
            startActivity(intent);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }
}
