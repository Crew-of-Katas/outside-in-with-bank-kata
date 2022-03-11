package bank.domain;

public interface Formatter {
    String formatLine(StatementLine line);

    String getHeader();
}
