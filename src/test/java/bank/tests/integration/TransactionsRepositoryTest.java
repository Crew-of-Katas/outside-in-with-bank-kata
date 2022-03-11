package bank.tests.integration;

import bank.domain.Transaction;
import bank.domain.TransactionsRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static bank.tests.TransactionBuilder.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public abstract class TransactionsRepositoryTest {

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String SEPARATOR = ",";
    protected TransactionsRepository repository;
    protected List<String> initialTransactionLines;

    @Before
    public void setup() throws IOException, ParseException {
        repository = createRepository();
        initialTransactionLines = Arrays.asList("100,10/10/2021"+"\n","-50,15/10/2021"+"\n");
        deleteAllTransactions();
        addTransactions();
    }

    @Test
    public void a_transaction_can_be_saved() throws ParseException {
        Transaction transaction = aTransaction().withDeposit(500).onBritishDate("25/10/2021").build();

        repository.save(transaction);

        List<Transaction> transactions = readAllTransactions();
        assertThat(transactions.size(), is(3));
        assertThat(transactions.get(0), is(aTransaction().withDeposit(100).onBritishDate("10/10/2021").build()));
        assertThat(transactions.get(1), is(aTransaction().withWithdrawal(50).onBritishDate("15/10/2021").build()));
        assertThat(transactions.get(2), is(aTransaction().withDeposit(500).onBritishDate("25/10/2021").build()));

    }

    @Test
    public void transactions_can_be_retrieved() throws ParseException {
        List<Transaction> transactions = readAllTransactions();

        assertThat(transactions.size(), is(2));
        assertThat(transactions.get(0), is(aTransaction().withDeposit(100).onBritishDate("10/10/2021").build()));
        assertThat(transactions.get(1), is(aTransaction().withWithdrawal(50).onBritishDate("15/10/2021").build()));
    }

    protected Transaction parse(String transactionLine) throws ParseException {
        String[] tokens = transactionLine.split(SEPARATOR);
        int amount = Integer.parseInt(tokens[0]);
        String dateString = tokens[1];
        Date date = new SimpleDateFormat(DATE_FORMAT).parse(dateString);

        return new Transaction(amount,date);
    }

    protected abstract TransactionsRepository createRepository();

    protected abstract List<Transaction> readAllTransactions();

    protected abstract void deleteAllTransactions() throws IOException;
    protected abstract void addTransactions() throws IOException, ParseException;
}
