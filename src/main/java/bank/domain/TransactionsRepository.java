package bank.domain;

import java.util.List;

public interface TransactionsRepository {
    void save(Transaction transaction);

    List<Transaction> retrieveAll();
}
