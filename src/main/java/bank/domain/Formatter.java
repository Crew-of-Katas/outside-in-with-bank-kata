package bank.domain;

import java.util.Date;

public class Formatter {
    public static final String DD_MM_YYYY = "dd/MM/yyyy";
    public static final String MM_DD_YYYY = "MM/dd/yyyy";
    protected final Header header;
    protected final Locale locale;

    protected Formatter(Header header, Locale locale) {
        this.header = header;
        this.locale = locale;
    }

    public static Formatter forAmerican() {
        return new Formatter(new EnglishHeader(), new EnglishLocale(MM_DD_YYYY));
    }

    public static Formatter forBritish() {
        return new Formatter(new EnglishHeader(), new EnglishLocale(DD_MM_YYYY));
    }

    public static Formatter forSpanish() {
        return new Formatter(new SpanishHeader(), new SpanishLocale(DD_MM_YYYY));
    }

    public String formatLine(StatementLine line) {
        int amount = line.getAmount();
        Date date = line.getDate();
        if (amount > 0) {
            return formatDate(date) + " || " + amount + locale.getDecimalSeparator() +"00 || || " + line.getBalance() + locale.getDecimalSeparator() + "00";
        }
        return formatDate(date) + " || || " + Math.abs(amount) + locale.getDecimalSeparator() +"00 || " + line.getBalance() + locale.getDecimalSeparator() + "00";

    }

    private String formatDate(Date date) {
        return locale.formatDate(date);
    }

    public String getHeader() {
        return header.getDate() + " || " + header.getCredit() + " || " + header.getDebit() + " || " + header.getBalance();
    }
}
