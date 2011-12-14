package ext;

import org.jsoup.Jsoup;
import play.Play;
import play.templates.JavaExtensions;

import java.io.UnsupportedEncodingException;

public class StringExtensions extends JavaExtensions {
    public static String truncate(String input) {
        return truncate(input, 80);
    }

    public static String truncate(String input, int length) {
        return truncate(input, length, "...");
    }

    public static String truncate(String input, int length, String suffix) {
        if (input.length() > length) {
            input = input.substring(0, length - suffix.length()) + suffix;
        }

        return input;
    }
    
    public static String stripTags(String input) {
        return Jsoup.parse(input).text();
    }
    
    public static String hex(String str) {
        byte[] bytes;
        try {
            bytes = str.getBytes(Play.defaultWebEncoding);
        } catch (UnsupportedEncodingException e) {
            bytes = str.getBytes();
        }

        StringBuffer hex = new StringBuffer();
        for (byte b: bytes) {
            hex.append(String.format("%%%02x", b));
        }
        
        return hex.toString();
    }

    public static String mailto(String email) {
        return mailto(email, email);
    }
    
    public static String mailto(String email, String label) {
        return String.format("<script type=\"text/javascript\">document.write(decodeURIComponent('%s'))</script>",
                                      hex(String.format("<a href=\"mailto:%s\">%s</a>", email, label)));
    }
}
