package bank.domain;

import java.text.SimpleDateFormat;

public class SpanishFormatter implements Formatter {
    private static final String HEADER = "fecha || crédito || débito || balance";
    private final SimpleDateFormat dateFormatter;

    public SpanishFormatter() {
        this.dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public String formatLine(StatementLine line) {
        int amount = line.getAmount();
        if (amount > 0) {
            return dateFormatter.format(line.getDate()) + " || " + amount + ",00 || || " + line.getBalance() + ",00";
        }
        return dateFormatter.format(line.getDate()) + " || || " + Math.abs(amount) + ",00 || " + line.getBalance() + ",00";

    }

    @Override
    public String getHeader() {
        return HEADER;
    }
}
