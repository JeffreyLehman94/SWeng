package Scans;

import CMS2Statements.Statement;
import Reports.Entry;

import java.util.ArrayList;

/**
 * This class scans code for Misplaced END-SYSTEM Statements
 */
public class MisplacedEndSysScanner extends Scan {

    private boolean correctENDSYS = false; // assume false for now

    /**
     * The constructor for MisplacedEndScanner.
     */
    public MisplacedEndSysScanner() {
        KEYWORD = "MisplacedEndSystem";
        count = 0; // the number of misplaced END-SYSTEM statements
    }

    /**
     * Scans a statement and counts it if it is a Misplaced END-SYSTEM Statement
     * @param statement The statement to be scanned
     */
    public void scan(Statement statement) {
        if (correctENDSYS) { // if this scanner is run even though an END-SYSTEM statement was already found
            correctENDSYS = false; // the END-SYSTEM statement must be in the wrong spot
            count++;
        }
        if (!statement.isDirectCode() && getFirstToken(statement.getText()).equals("END-SYSTEM")) {
            correctENDSYS = true; // assume this to be true until a statement is found afterward.
        }
    }


    /**
     * Overrides getData() in the Scan class to increment count if NO END-SYSTEM statement was found at all.
     */
    @Override
    public ArrayList<Entry> getData() {
        ArrayList<Entry> data = new ArrayList<Entry>();

        if (count == 0 && !correctENDSYS) { // if the final statement read wasn't END-SYSTEM, something's wrong
            count++;
        }
        data.add(new Entry(KEYWORD + " Stmts", count));
        return data;
    }
}
