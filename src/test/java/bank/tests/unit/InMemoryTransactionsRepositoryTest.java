package bank.tests.unit;

import bank.domain.Transaction;
import bank.domain.TransactionsRepository;
import bank.infrastructure.InMemoryTransactionsRepository;
import bank.tests.integration.TransactionsRepositoryTest;

import java.text.ParseException;
import java.util.List;

public class InMemoryTransactionsRepositoryTest extends TransactionsRepositoryTest {

    protected TransactionsRepository createRepository() {
        return new InMemoryTransactionsRepository();
    }

    protected List<Transaction> readAllTransactions() {
        return repository.retrieveAll();
    }

    @Override
    protected void deleteAllTransactions() {
        repository = createRepository();
    }

    @Override
    protected void addTransactions() throws ParseException {
        for (String transactionLine: initialTransactionLines) {
            Transaction transaction = parse(transactionLine);
            repository.save(transaction);
        }
    }
}
