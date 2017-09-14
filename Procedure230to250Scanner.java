package Scans;

import CMS2Statements.Statement;
import Reports.Entry;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * This class scans code for procedures with 230-250 statements.
 */
public class Procedure230to250Scanner extends Scan {

    private boolean inProcBlock;
    public static TreeSet<String> procNames = new TreeSet<>();
    private String procName;
    private int occurCount = 0;

    /**
     * Constructor for Procedure230to250Scanner.
     */
    public Procedure230to250Scanner() {
        KEYWORD = "230-250";
        count = 0;
        inProcBlock = false;
        procName = "";
        occurCount = 0;
    }

    /**
     * Scans a procedure's statement and counts the procedure if it has 230-250 statements.
     * @param statement The statement to be scanned
     */
    public void scan(Statement statement) {
        String s = getFirstToken(statement.getText());
        if (!statement.isDirectCode() && s.equals("PROCEDURE")) {
            inProcBlock = true;
            procName = getSecondToken(statement.getText());
        } else if (!statement.isDirectCode() && s.equals("END-PROC")) {
            inProcBlock = false;
            if (count >= 230 && count <= 250) {
                procNames.add(procName);
                occurCount++;
            }
            count = 0;
        } else if (inProcBlock) {
            count++;
        }
    }

    /**
     * Overrides Scan's getData() method so that it returns how many procedures match.
     * @return number of matching procedures
     */
    public ArrayList<Entry> getData() {
        ArrayList<Entry> data = new ArrayList<Entry>();

        data.add(new Entry(KEYWORD + " Procs", occurCount));
        // the actual names of these procedures can be accessed via procNames, which is public

        return data;
    }
}
