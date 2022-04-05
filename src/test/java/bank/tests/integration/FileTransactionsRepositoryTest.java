package bank.tests.integration;

import bank.domain.Transaction;
import bank.domain.TransactionsRepository;
import bank.infrastructure.FileTransactionsRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FileTransactionsRepositoryTest extends TransactionsRepositoryTest {
    private static final String TRANSACTIONS_FILE = "src/test/resources/initial_transactions.txt";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String SEPARATOR = ",";
    private final SimpleDateFormat simpleDateFormat;

    public FileTransactionsRepositoryTest() {
        simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
    }

    @Override
    protected TransactionsRepository createRepository() {
        return new FileTransactionsRepository(TRANSACTIONS_FILE);
    }

    @Override
    protected List<Transaction> readAllTransactions() {
        return readTransactions(TRANSACTIONS_FILE);
    }

    @Override
    protected void prepareData(List<Transaction> transactions) {
       try {
            deleteAllTransactions();
            List<String> transactionLines = createTransactionLinesFrom(transactions);
            addTransactionLines(transactionLines);
        } catch (IOException e) {
            System.err.println("Error preparing transaction data in tests: " + e);
        }
    }

    private List<String> createTransactionLinesFrom(List<Transaction> transactions) {
        List<String> transactionLines = new ArrayList<>();
        for (Transaction transaction: transactions){
            String transactionLine = transaction.getAmount()+","+ simpleDateFormat.format(transaction.getDate())+"\n";
            transactionLines.add(transactionLine);
        }
        return transactionLines;
    }

    private void deleteAllTransactions() throws IOException {
         Files.deleteIfExists(Paths.get(TRANSACTIONS_FILE));
         Files.createFile(Paths.get(TRANSACTIONS_FILE));
    }

    private void addTransactionLines(List<String> transactionLines) throws IOException {
        for (String transactionLine: transactionLines) {
            Files.write(Paths.get(TRANSACTIONS_FILE),
                    transactionLine.getBytes(), StandardOpenOption.APPEND);
        }
    }

    private List<Transaction> readTransactions(String filePath) {
        try {
            List<String> lines = extractTransactionLinesFrom(filePath);
            return createTransactionsFrom(lines);
        } catch (IOException | ParseException e) {
            System.err.println("Error reading transaction data in test: " + e);
        }
        return Collections.emptyList();
    }

    private List<String> extractTransactionLinesFrom(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.lines(path).collect(Collectors.toList());
        return lines;
    }

    private List<Transaction> createTransactionsFrom(List<String> lines) throws ParseException {
        List<Transaction> transactions = new ArrayList<>();
        for (String line: lines) {
            transactions.add(parse(line));
        }
        return transactions;
    }

    private Transaction parse(String transactionLine) throws ParseException {
        String[] tokens = transactionLine.split(SEPARATOR);
        int amount = Integer.parseInt(tokens[0]);
        String dateString = tokens[1];
        Date date = new SimpleDateFormat(DATE_FORMAT).parse(dateString);

        return new Transaction(amount, date);
    }

}
