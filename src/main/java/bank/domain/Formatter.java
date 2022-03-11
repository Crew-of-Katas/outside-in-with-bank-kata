package bank.domain;

import java.text.SimpleDateFormat;

public abstract class Formatter {
    protected final SimpleDateFormat dateFormatter;
    protected final String decimalSeparator;

    public Formatter(SimpleDateFormat dateFormatter, String decimalSeparator) {
        this.dateFormatter = dateFormatter;
        this.decimalSeparator = decimalSeparator;
    }

    public abstract String getHeader();

    public String formatLine(StatementLine line) {
        int amount = line.getAmount();
        if (amount > 0) {
            return formatLine(dateFormatter.format(line.getDate()), formatAmount(amount), "", formatAmount(line.getBalance()));
        }
        return formatLine(dateFormatter.format(line.getDate()),"", formatAmount(+ Math.abs(amount)), formatAmount(line.getBalance()));

    }

    protected String formatLine(String date, String credit, String debit, String balance) {
        if(debit.isEmpty())  {
            return date + " || " + credit + " || || " + balance;
        }
        if(credit.isEmpty())  {
            return date  + " || || " + debit + " || " + balance;
        }
        return date + " || " + credit + " || " + debit + " || " + balance;
    }

    private String formatAmount(int amount) {
        return amount + decimalSeparator +  "00";
    }
}
