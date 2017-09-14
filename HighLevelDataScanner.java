package Scans;

import CMS2Statements.Statement;
import Reports.Entry;

import java.util.ArrayList;

/**
 * This class scans code for High Level Data Statements.
 */
public class HighLevelDataScanner extends LineScan {

    private boolean inDataBlock;

    /**
     * Constructor for HighLevelDataScanner.
     */
    public HighLevelDataScanner() {
        KEYWORD = "Data";
        count = 0;
        lineCount = 0;
        inDataBlock = false;
    }

    /**
     * Scans a statement and counts it and its lines if it is a High Level Data Statement.
     * (This method has been updated to more closely resemble HighLevelExecScanner's more efficient design.)
     * @param statement The statement to be scanned
     */
    public void scan(Statement statement) {
        String s = statement.getText();
        if (!getFirstToken(s).equals("COMMENT")) {
            if (!statement.isDirectCode()) {
                if (s.contains("END-SYS-DD")) {
                    inDataBlock = false;
                } else if (s.contains("SYS-DD")) {
                    inDataBlock = true;
                } else if (inDataBlock) {
                    count++;
                    tallyLines(statement);
                    statement.setClassified(true);
                }
            }
        }
    }
}
