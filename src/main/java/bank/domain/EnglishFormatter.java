package bank.domain;

import java.text.SimpleDateFormat;

public class EnglishFormatter implements Formatter {
    public static final String BRITISH_DATE_FORMAT = "dd/MM/yyyy";
    public static final String AMERICAN_DATE_FORMAT = "MM/dd/yyyy";
    private static final String HEADER = "date || credit || debit || balance";
    private final SimpleDateFormat dateFormatter;

    private EnglishFormatter(String dateFormat) {
        this.dateFormatter = new SimpleDateFormat(dateFormat);
    }

    public static EnglishFormatter forAmerican() {
        return new EnglishFormatter(AMERICAN_DATE_FORMAT);
    }

    public static EnglishFormatter forBritish() {
        return new EnglishFormatter(BRITISH_DATE_FORMAT);
    }

    @Override
    public String formatLine(StatementLine line) {
        int amount = line.getAmount();
        if (amount > 0) {
            return dateFormatter.format(line.getDate()) + " || " + amount + ".00 || || " + line.getBalance() + ".00";
        }
        return dateFormatter.format(line.getDate()) + " || || " + Math.abs(amount) + ".00 || " + line.getBalance() + ".00";

    }

    @Override
    public String getHeader() {
        return HEADER;
    }
}
