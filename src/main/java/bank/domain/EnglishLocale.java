package bank.domain;

public class EnglishLocale extends Locale {

    public EnglishLocale(String dateFormat) {
        super(dateFormat);
    }

    @Override
    public String getDecimalSeparator() {
        return ".";
    }
}
