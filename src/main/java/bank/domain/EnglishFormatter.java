package bank.domain;

import java.text.SimpleDateFormat;

public class EnglishFormatter extends Formatter {
    public static final String BRITISH_DATE_FORMAT = "dd/MM/yyyy";
    public static final String AMERICAN_DATE_FORMAT = "MM/dd/yyyy";

    private EnglishFormatter(String dateFormat) {
        super(new SimpleDateFormat(dateFormat), ".");
    }

    public static EnglishFormatter forAmerican() {
        return new EnglishFormatter(AMERICAN_DATE_FORMAT);
    }

    public static EnglishFormatter forBritish() {
        return new EnglishFormatter(BRITISH_DATE_FORMAT);
    }

    @Override
    public String getHeader() {
        return formatLine("date", "credit", "debit", "balance");
    }

}
