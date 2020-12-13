package Ultilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Ultitities {

    public static Date StringToDate(String datestring) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return format.parse(datestring);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String DateToString(Date date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static String FortmatDateString(String datestring){
        return DateToString(StringToDate(datestring));
    }
}
