package bank.tests.integration;

import bank.domain.Transaction;
import bank.domain.TransactionsRepository;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static bank.tests.TransactionBuilder.aTransaction;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public abstract class TransactionsRepositoryTest {

    protected TransactionsRepository repository;
    private List<Transaction> initialTransactions;

    @Before
    public void setup() throws ParseException {
        repository = createRepository();
        initialTransactions = Arrays.asList(aTransaction().withDeposit(100).onBritishDate("10/10/2021").build(),
                aTransaction().withWithdrawal(50).onBritishDate("15/10/2021").build());
        prepareData(initialTransactions);
    }

    @Test
    public void a_transaction_can_be_saved() throws ParseException {
        Transaction transaction = aTransaction().withDeposit(500).onBritishDate("25/10/2021").build();

        repository.save(transaction);

        assertThat(readAllTransactions(), is(append(initialTransactions, transaction)));
    }

    @Test
    public void transactions_can_be_retrieved() {
        assertThat(repository.retrieveAll(), is(initialTransactions));
    }

    protected abstract TransactionsRepository createRepository();

    protected abstract List<Transaction> readAllTransactions();

    protected abstract void prepareData(List<Transaction> transactions);

    private List<Transaction> append(List<Transaction> transactions, Transaction transaction) {
        List<Transaction> result = new ArrayList<>(transactions);
        result.add(transaction);
        return result;
    }
}
