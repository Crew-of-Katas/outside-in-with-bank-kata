package bank.domain;

import java.util.Date;

public class StatementLine {
    private final Transaction transaction;
    private final int balance;

    public StatementLine(Transaction transaction, int balance) {
        this.transaction = transaction;
        this.balance = balance;
    }

    public int getAmount() {
        return transaction.getAmount();
    }

    public Date getDate() {
        return transaction.getDate();
    }

    public int getBalance() {
        return balance;
    }
}
