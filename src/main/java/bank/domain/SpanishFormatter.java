package bank.domain;

import java.text.SimpleDateFormat;

public class SpanishFormatter extends Formatter {

    public SpanishFormatter() {
        super(new SimpleDateFormat("dd/MM/yyyy"), ",");
    }

    @Override
    public String getHeader() {
        return formatLine("fecha", "crédito", "débito", "balance");
    }
}
