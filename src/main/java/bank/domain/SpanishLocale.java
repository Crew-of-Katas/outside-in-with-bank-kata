package bank.domain;

public class SpanishLocale extends Locale {

    public SpanishLocale(String dateFormat) {
        super(dateFormat);
    }

    @Override
    public String getDecimalSeparator() {
        return ",";
    }
}
