package Scans;

import CMS2Statements.Statement;

public class HighLevelOtherScanner extends Scan {

    public HighLevelOtherScanner() {
        KEYWORD = "Other";
        count = 0;
    }

    public void scan(Statement statement) {
        if(!statement.isDirectCode() && !statement.isClassified()){
            statement.setClassified(true);
            count++;
        }
    }
}
