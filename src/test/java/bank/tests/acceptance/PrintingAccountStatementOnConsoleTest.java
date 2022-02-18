package bank.tests.acceptance;

import bank.domain.*;
import org.junit.Test;
import org.mockito.InOrder;

import java.text.ParseException;
import java.util.Arrays;

import static bank.tests.DatesHelper.date;
import static bank.tests.TransactionBuilder.aTransaction;
import static org.mockito.Mockito.*;

public class PrintingAccountStatementOnConsoleTest {
    @Test
    public void printing_statement_including_deposit_and_withdrawal_in_great_britain() throws ParseException {
        Calendar calendar = mock(Calendar.class);
        Display display = mock(Display.class);
        TransactionsRepository transactionsRepository = mock(TransactionsRepository.class);
        when(calendar.getDate()).thenReturn(date("10/01/2012", Formatter.BRITISH_DATE_FORMAT));
        when(calendar.getDate()).thenReturn(date("13/01/2012", Formatter.BRITISH_DATE_FORMAT));
        when(calendar.getDate()).thenReturn(date("14/01/2012", Formatter.BRITISH_DATE_FORMAT));

        when(transactionsRepository.retrieveAll()).thenReturn(
                Arrays.asList(
                        aTransaction().withDeposit(1000).on("10/01/2012").build(),
                        aTransaction().withDeposit(2000).on("13/01/2012").build(),
                        aTransaction().withWithdrawal(500).on("14/01/2012").build())
        );

        BankAccount bankAccount = new BankAccount(calendar, transactionsRepository, new LineStatementPrinter(display, Formatter.BRITISH_DATE_FORMAT));

        bankAccount.deposit(1000);
        bankAccount.deposit(2000);
        bankAccount.withdraw(500);
        bankAccount.printBankStatement();

        InOrder inOrder = inOrder(display);
        inOrder.verify(display).print("date || credit || debit || balance");
        inOrder.verify(display).print("14/01/2012 || || 500.00 || 2500.00");
        inOrder.verify(display).print("13/01/2012 || 2000.00 || || 3000.00");
        inOrder.verify(display).print("10/01/2012 || 1000.00 || || 1000.00");
        verify(display,times(4)).print(anyString());
    }

    @Test
    public void printing_statement_including_deposit_and_withdrawal_in_usa() throws ParseException {
        Calendar calendar = mock(Calendar.class);
        Display display = mock(Display.class);
        TransactionsRepository transactionsRepository = mock(TransactionsRepository.class);
        String anyDate = "10/24/2012";
        when(calendar.getDate()).thenReturn(date(anyDate, Formatter.AMERICAN_DATE_FORMAT));

        when(transactionsRepository.retrieveAll()).thenReturn(
                Arrays.asList(
                        aTransaction().withDeposit(1000).on(anyDate).build())
        );

        BankAccount bankAccount = new BankAccount(calendar, transactionsRepository, new LineStatementPrinter(display, Formatter.BRITISH_DATE_FORMAT));

        bankAccount.deposit(1000);
        bankAccount.printBankStatement();

        InOrder inOrder = inOrder(display);
        inOrder.verify(display).print("date || credit || debit || balance");
        inOrder.verify(display).print(anyDate + " || 1000.00 || || 1000.00");
        verify(display,times(2)).print(anyString());
    }

}
