package Scans;

import CMS2Statements.Statement;

public class TotalCSWTCScanner extends Scan {

    public TotalCSWTCScanner() {
        KEYWORD = "CSWTC";
        count = 0;
    }

    public void scan(Statement statement) {
        if (!statement.isDirectCode()) {
            String text = getFirstToken(statement.getText());
            if (text.equals("CSWITCH")) {
                count++;
            }
        }
    }
}
