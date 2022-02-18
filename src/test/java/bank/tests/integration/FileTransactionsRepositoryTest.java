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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileTransactionsRepositoryTest extends TransactionsRepositoryTest {
    public static final String TRANSACTIONS_FILE = "src/test/resources/initial_transactions.txt";

    protected void deleteAllTransactions() throws IOException {
         Files.deleteIfExists(Paths.get(TRANSACTIONS_FILE));
         Files.createFile(Paths.get(TRANSACTIONS_FILE));
    }

    protected void addTransactions() throws IOException {
        for (String transactionLine: initialTransactionLines) {
            Files.write(Paths.get(TRANSACTIONS_FILE),
                    transactionLine.getBytes(), StandardOpenOption.APPEND);
        }
    }

    private List<Transaction> readTransactions(String filePath) {

        List<Transaction> transactions = new ArrayList<>();
        try {
            Path path = Paths.get(filePath);

            List<String> lines = Files.lines(path).collect(Collectors.toList());
            for (String line: lines) {
                transactions.add(parse(line));
            }

            return transactions;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    protected TransactionsRepository createRepository() {
        return new FileTransactionsRepository(TRANSACTIONS_FILE);
    }

    protected List<Transaction> readAllTransactions() {
        return readTransactions(TRANSACTIONS_FILE);
    }

}
