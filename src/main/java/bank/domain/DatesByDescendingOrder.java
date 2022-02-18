package bank.domain;

import java.util.Collections;
import java.util.List;

public class DatesByDescendingOrder {
    void sort(List<StatementLine> lines) {
        Collections.reverse(lines);
    }
}
