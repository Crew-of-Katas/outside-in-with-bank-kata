package bank.domain;

import java.util.ArrayList;
import java.util.List;

public class LineStatementPrinter implements StatementPrinter {
    private final Formatter formatter;
    private final DatesByDescendingOrder datesByDescendingOrder;
    private Display display;



    public LineStatementPrinter(Display display, Formatter formatter) {
        this.display = display;
        this.formatter = formatter;
        datesByDescendingOrder = new DatesByDescendingOrder();
    }

    @Override
    public void print(List<Transaction> transactions) {
        display.print(formatter.getHeader());

        List<StatementLine> lines = getStatementLines(transactions);
        for (StatementLine line : lines) {
            int amount = line.getAmount();
            if (amount == 0) {
                return;
            }
            String formattedLine = formatter.formatLine(line);

            display.print(formattedLine);
        }
    }

    private List<StatementLine> getStatementLines(List<Transaction> transactions) {
        List<StatementLine> lines = getLines(transactions);
        datesByDescendingOrder.sort(lines);
        return lines;
    }

    private List<StatementLine> getLines(List<Transaction> transactions) {
        int balance = 0;

        List<StatementLine> lines = new ArrayList<>();
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
            lines.add(new StatementLine(transaction, balance));
        }
        return lines;
    }
}
