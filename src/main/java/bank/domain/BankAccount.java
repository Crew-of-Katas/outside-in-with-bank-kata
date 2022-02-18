package bank.domain;

public class BankAccount {

    private final Calendar calendar;
    private final TransactionsRepository transactionsRepository;
    private final StatementPrinter statementPrinter;

    public BankAccount(Calendar calendar, TransactionsRepository transactionsRepository, StatementPrinter statementPrinter) {
        this.calendar = calendar;
        this.transactionsRepository = transactionsRepository;
        this.statementPrinter = statementPrinter;
    }

    public void deposit(int amount) {
        transactionsRepository.save(new Transaction(amount, calendar.getDate()));
    }

    public void withdraw(int amount) {
        transactionsRepository.save(new Transaction(-amount, calendar.getDate()));
    }

    public void printBankStatement() {
        statementPrinter.print(transactionsRepository.retrieveAll());
    }
}
