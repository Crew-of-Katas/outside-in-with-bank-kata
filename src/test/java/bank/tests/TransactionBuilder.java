package bank.tests;

import bank.domain.Formatter;
import bank.domain.Transaction;

import java.text.ParseException;

import static bank.tests.DatesHelper.*;

public class TransactionBuilder {
    private int amount;
    private String date;

    public static TransactionBuilder aTransaction() {
        return new TransactionBuilder();
    }

    public TransactionBuilder on(String date) {
        this.date = date;
        return this;
    }

    public Transaction build() throws ParseException {
        return new Transaction(amount, date(date, Formatter.BRITISH_DATE_FORMAT));
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
