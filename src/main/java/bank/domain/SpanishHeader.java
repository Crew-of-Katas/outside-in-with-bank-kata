package bank.domain;

public class SpanishHeader implements Header {
    @Override
    public String getBalance() {
        return "balance";
    }

    @Override
    public String getDebit() {
        return "débito";
    }

    @Override
    public String getCredit() {
        return "crédito";
    }

    @Override
    public String getDate() {
        return "fecha";
    }
}
