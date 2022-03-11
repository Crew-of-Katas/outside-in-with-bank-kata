package bank.tests;

import bank.domain.EnglishFormatter;
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
        dateFormat = EnglishFormatter.BRITISH_DATE_FORMAT;
        return this;
    }

    public TransactionBuilder onAmericanDate(String date) {
        this.date = date;
        dateFormat = EnglishFormatter.AMERICAN_DATE_FORMAT;
        return this;
    }

    public TransactionBuilder onSpanishDate(String date) {
        this.date = date;
        dateFormat = EnglishFormatter.BRITISH_DATE_FORMAT;
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
