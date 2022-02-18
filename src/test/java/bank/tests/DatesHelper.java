package bank.tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatesHelper {
    public static Date date(String representation, String dateFormat) throws ParseException {
        return new SimpleDateFormat(dateFormat).parse(representation);
    }
}
