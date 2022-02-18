package bank.tests.unit;

import bank.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static bank.tests.TransactionBuilder.aTransaction;
import static java.util.Collections.emptyList;
import static org.mockito.Mockito.*;

public class StatementPrinterTest {

    private Display display;
    private StatementPrinter statementPrinter;

    @Before
    public void setUp() {
        display = mock(Display.class);
        statementPrinter = new LineStatementPrinter(display, Formatter.BRITISH_DATE_FORMAT);
    }

    @Test
    public void print_statement_when_no_transactions() {
        List<Transaction> transactions = emptyList();

        statementPrinter.print(transactions);

        verify(display).print("date || credit || debit || balance");
    }

    @Test
    public void print_statement_with_one_credit_transaction() throws ParseException {
        List<Transaction> transactions = Arrays.asList(
                aTransaction().withDeposit(1000).on("10/01/2012").build()
        );

        statementPrinter.print(transactions);

        verify(display).print("date || credit || debit || balance");
        verify(display).print("10/01/2012 || 1000.00 || || 1000.00");
    }
    @Test
    public void print_statement_with_one_debit_transaction() throws ParseException {
        List<Transaction> transactions = Arrays.asList(
                aTransaction().withWithdrawal(500).on("14/01/2012").build()
        );

        statementPrinter.print(transactions);

        verify(display).print("date || credit || debit || balance");
        verify(display).print("14/01/2012 || || 500.00 || -500.00");
    }

    @Test
    public void print_statement_with_one_transactions_of_zero() throws ParseException {
        List<Transaction> transactions = Arrays.asList(
                aTransaction().withDeposit(0).on("14/01/2012").build()
        );

        statementPrinter.print(transactions);

        verify(display).print("date || credit || debit || balance");
        verify(display,never()).print("14/01/2012 || || 0.00 || -500.00");
    }

    @Test
    public void print_statement_with_several_transactions() throws ParseException {
        List<Transaction> transactions = Arrays.asList(
                aTransaction().withDeposit(2000).on("13/01/2012").build(),
                aTransaction().withWithdrawal(500).on("14/01/2012").build()
        );

        statementPrinter.print(transactions);


        InOrder inOrder = inOrder(display);
        inOrder.verify(display).print("date || credit || debit || balance");
        inOrder.verify(display).print("14/01/2012 || || 500.00 || 1500.00");
        inOrder.verify(display).print("13/01/2012 || 2000.00 || || 2000.00");
        verify(display,times(3)).print(anyString());
    }

}
