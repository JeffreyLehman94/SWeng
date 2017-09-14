package Scans;

import CMS2Statements.Statement;
import Reports.Entry;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * This class scans code to see how often it deviates from the compile time structure
 */
public class SystemNotStructuredScanner extends Scan {

    private ArrayList<String> structure;
    /**
     * Constructor for SystemNotStructuredScanner.
     */
    public SystemNotStructuredScanner() {
        KEYWORD = "NotStructured";
        count = 0;
        structure = new ArrayList<String>();
    }

    /**
     * Scans a statement and counts it if it deviates from the compile-time structure.
     * @param statement The statement to be scanned
     */
    public void scan(Statement statement) {
        String sT  = statement.getText();
        String s = getFirstToken(sT);
        if (!statement.isDirectCode()) {
            if (structure.isEmpty()) {
                if (!sT.contains("SYSTEM") || sT.contains("END-SYSTEM") || s.equals("COMMENT")) {
                    count++;
                } else {
                    structure.add("SYSTEM");
                }
            } else if (sT.contains("SYSTEM") && !sT.contains("END-SYSTEM") && !s.equals("COMMENT")) {
                count++;
                structure.add("SYSTEM");
            } else if (s.equals("HEAD")) {
                if (!structure.get(structure.size()-1).equals("SYSTEM"))
                    count++;
                structure.add(s);
            } else if (s.equals("END-HEAD")) {
                if (!structure.get(structure.size()-1).equals("HEAD"))
                    count++;
                structure.add(s);
            } else if (sT.contains("SYS-DD") && !sT.contains("END-SYS-DD") && !s.equals("COMMENT")) {
                if (!structure.get(structure.size()-1).equals("END-HEAD"))
                    count++;
                structure.add("SYS-DD");
            } else if (s.equals("END-SYS-DD")) {
                if (!structure.get(structure.size()-1).equals("SYS-DD"))
                    count++;
                structure.add(s);
            } else if (sT.contains("SYS-PROC") && !sT.contains("END-SYS-PROC") && !s.equals("COMMENT")) {
                if (!structure.get(structure.size()-1).equals("END-SYS-DD"))
                    count++;
                structure.add("SYS-PROC");
            } else if (s.equals("PROCEDURE")) {
                if (!structure.get(structure.size()-1).equals("SYS-PROC"))
                    count++;
                structure.add(s);
            } else if (s.equals("END-PROC")) {
                if (!structure.get(structure.size()-1).equals("PROCEDURE"))
                    count++;
                else structure.remove(structure.size()-1); // in case there are other procedures
            } else if (s.equals("END-SYS-PROC")) {
                if (!structure.get(structure.size()-1).equals("SYS-PROC"))
                    count++;
                structure.add(s);
            } else if (s.equals("END-SYSTEM")) {
                if (!structure.get(structure.size()-1).equals("END-SYS-PROC"))
                    count++;
                structure.add(s);
            } else if (structure.get(structure.size()-1).equals("END-SYSTEM")) {
                count++;
            }
        }
    }

    /**
     * Overrides Scan's getData() method so that it returns how many occurrences are found
     * @return number of occurrences
     */
    public ArrayList<Entry> getData() {
        ArrayList<Entry> data = new ArrayList<Entry>();

        data.add(new Entry(KEYWORD + " Occurences", count));

        return data;
    }
}
