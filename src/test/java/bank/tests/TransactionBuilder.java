package bank.tests;

import bank.domain.Formatter;
import bank.domain.Transaction;

import java.text.ParseException;

import static bank.tests.DatesHelper.*;

public class TransactionBuilder {
    private int amount;
    private String date;
    private String dateFormat;

    public static TransactionBuilder aTransaction() {
        return new TransactionBuilder();
    }

    public TransactionBuilder onBritishDate(String date) {
        this.date = date;
        dateFormat = Formatter.DD_MM_YYYY;
        return this;
    }

    public TransactionBuilder onAmericanDate(String date) {
        this.date = date;
        dateFormat = Formatter.MM_DD_YYYY;
        return this;
    }

    public TransactionBuilder onSpanishDate(String date) {
        this.date = date;
        dateFormat = Formatter.DD_MM_YYYY;
        return this;
    }

    public Transaction build() throws ParseException {
        return new Transaction(amount, date(date, dateFormat));
    }

    public TransactionBuilder withDeposit(int amount) {
        return withAmount(amount);
    }

    public TransactionBuilder withWithdrawal(int amount) {
        return withAmount(-amount);
    }

    private TransactionBuilder withAmount(int amount) {
        this.amount = amount;
        return this;
    }
}
