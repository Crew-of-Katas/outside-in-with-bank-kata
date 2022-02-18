package bank.infrastructure;

import bank.domain.Transaction;
import bank.domain.TransactionsRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionsRepository implements TransactionsRepository {
    List<Transaction> transactions;

    public InMemoryTransactionsRepository() {
        this.transactions = new ArrayList<>();
    }

    @Override
    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<Transaction> retrieveAll() {
        return transactions;
    }
}
