package bank.domain;

public class EnglishHeader implements Header {
    @Override
    public String getBalance() {
        return "balance";
    }

    @Override
    public String getDebit() {
        return "debit";
    }

    @Override
    public String getCredit() {
        return "credit";
    }

    @Override
    public String getDate() {
        return "date";
    }
}
