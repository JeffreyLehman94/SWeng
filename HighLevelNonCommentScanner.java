package Scans;

import CMS2Statements.Statement;
import Reports.Entry;

import java.util.ArrayList;

public class HighLevelNonCommentScanner extends LineScan {

    public HighLevelNonCommentScanner() {
        KEYWORD = "NonCmt";
        count = 0;
    }

    public void scan(Statement statement) {
        if(!statement.isDirectCode() && !getFirstToken(statement.getText()).equals("COMMENT")){
            tallyLines(statement);
        }
    }

    public ArrayList<Entry> getData() {
        ArrayList<Entry> data = new ArrayList<Entry>();

        data.add(new Entry(KEYWORD + " Lines", lineCount));

        return data;
    }
}
