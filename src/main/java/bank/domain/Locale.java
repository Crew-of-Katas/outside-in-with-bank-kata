package bank.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Locale {
    protected final SimpleDateFormat dateFormatter;

    public Locale(String dateFormat) {
        this.dateFormatter = new SimpleDateFormat(dateFormat);
    }

    public String formatDate(Date date) {
        return dateFormatter.format(date);
    }

    public abstract String getDecimalSeparator();
}
