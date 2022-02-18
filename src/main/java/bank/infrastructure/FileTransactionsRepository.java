package bank.infrastructure;

import bank.domain.Transaction;
import bank.domain.TransactionsRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FileTransactionsRepository implements TransactionsRepository {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String SEPARATOR = ",";
    private String filePath;

    public FileTransactionsRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Transaction transaction) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            String transactionLine = transaction.getAmount()+SEPARATOR+formatter.format(transaction.getDate())+"\n";
            Files.write(Paths.get(filePath), transactionLine.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Transaction> retrieveAll() {
        List<Transaction> transactions = new ArrayList<>();
        try {
            Path path = Paths.get(filePath);


            List<String> lines = Files.lines(path).collect(Collectors.toList());
            for (String line: lines) {
                transactions.add(parse(line));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private Transaction parse(String transactionLine) throws ParseException {
        String[] tokens = transactionLine.split(SEPARATOR);
        int amount = Integer.parseInt(tokens[0]);
        String dateString = tokens[1];
        Date date = new SimpleDateFormat(DATE_FORMAT).parse(dateString);

        return new Transaction(amount,date);
    }
}
