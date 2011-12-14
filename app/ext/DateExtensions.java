package ext;

import play.templates.JavaExtensions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateExtensions extends JavaExtensions {
    public static String rfc822(Date date) {
        return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US).format(date);
    }

    public static String rfc3339(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).format(date);
    }
}
