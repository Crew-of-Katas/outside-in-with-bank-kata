package bank.tests.unit;

import bank.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static bank.tests.DatesHelper.date;
import static bank.tests.TransactionBuilder.*;
import static org.mockito.Mockito.*;

public class BankAccountTest {

    private Calendar calendar;
    private TransactionsRepository transactionsRepository;
    private BankAccount bankAccount;
    private StatementPrinter statementPrinter;

    @Before
    public void setUp() {
        calendar = mock(Calendar.class);
        transactionsRepository = mock(TransactionsRepository.class);
        statementPrinter = mock(StatementPrinter.class);
        bankAccount = new BankAccount(calendar, transactionsRepository, statementPrinter);
    }

    @Test
    public void deposit_money() throws ParseException {
        when(calendar.getDate()).thenReturn(date("10/01/2012", EnglishFormatter.BRITISH_DATE_FORMAT));

        bankAccount.deposit(500);

        Transaction transaction = aTransaction().withDeposit(500).onBritishDate("10/01/2012").build();

        verify(transactionsRepository).save(transaction);
    }

    @Test
    public void withdraw_money() throws ParseException {
        when(calendar.getDate()).thenReturn(date("12/12/2020", EnglishFormatter.BRITISH_DATE_FORMAT));

        bankAccount.withdraw(1000);

        Transaction transaction = aTransaction().withWithdrawal(1000).onBritishDate("12/12/2020").build();
        verify(transactionsRepository).save(transaction);
    }

    @Test
    public void print_bank_statement() throws ParseException {
        List<Transaction> transactions = Arrays.asList(
                aTransaction().withDeposit(150).onBritishDate("01/01/2011").build(),
                aTransaction().withWithdrawal(50).onBritishDate("05/05/2015").build()
        );
        when(transactionsRepository.retrieveAll()).thenReturn(transactions);

        bankAccount.printBankStatement();

        verify(statementPrinter).print(transactions);
    }
}
