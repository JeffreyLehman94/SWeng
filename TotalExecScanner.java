package Scans;

import CMS2Statements.Statement;
import Reports.Entry;

import java.util.ArrayList;

/**
 * This class scans code for Executable Statements in general
 */
public class TotalExecScanner extends Scan {

    private HighLevelExecScanner hles = new HighLevelExecScanner();
    private DirectExecScanner des = new DirectExecScanner();

    /**
     * The constructor for TotalExecScanner.
     */
    public TotalExecScanner() {
        KEYWORD = "Exec";
        count = 0;
    }

    /**
     * Scans a statement using a HighLevelExecScanner and a DirectExecScanner.
     * (If one of the two scanners counts the statement, then it must be an Executable Statement.)
     * @param statement The statement to be scanned
     */
    public void scan(Statement statement) {
        hles.scan(statement);
        des.scan(statement);
    }

    /**
     * Overrides getData() in the Scan class to fit the unique way this scanner operates.
     * @return the data from the scan
     */
    public ArrayList<Entry> getData() {
        ArrayList<Entry> data = new ArrayList<Entry>();

        data.add(new Entry(KEYWORD + " Stmts", hles.getData().get(0).getValue() + des.getData().get(0).getValue()));

        return data;
    }
}
