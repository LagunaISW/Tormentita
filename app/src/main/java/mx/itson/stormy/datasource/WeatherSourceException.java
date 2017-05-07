package mx.itson.stormy.datasource;

/**
 * Created by Lagunaisw on 5/10/2017.
 */
public class WeatherSourceException extends Exception {
    public WeatherSourceException() {
    }

    public WeatherSourceException(String detailMessage) {
        super(detailMessage);
    }

    public WeatherSourceException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public WeatherSourceException(Throwable throwable) {
        super(throwable);
    }
}
