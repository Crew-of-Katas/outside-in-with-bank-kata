package bank.domain;

import java.text.SimpleDateFormat;

public class Formatter {
    public static final String BRITISH_DATE_FORMAT = "dd/MM/yyyy";
    public static final String AMERICAN_DATE_FORMAT = "MM/dd/yyyy";
    private final SimpleDateFormat dateFormatter;

    public Formatter(String dateFormat) {
        this.dateFormatter = new SimpleDateFormat(dateFormat);
    }

    String formatLine(StatementLine line) {
        int amount = line.getAmount();
        if (amount > 0) {
            return dateFormatter.format(line.getDate()) + " || " + amount + ".00 || || " + line.getBalance() + ".00";
        }
        return dateFormatter.format(line.getDate()) + " || || " + Math.abs(amount) + ".00 || " + line.getBalance() + ".00";

    }
}
