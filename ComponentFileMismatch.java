package Scans;

import CMS2Statements.Statement;
import Controller.Controller;

/**
 * This class scans code for Component Name/File Mismatches.
 */
public class ComponentFileMismatch extends Scan {

    /**
     * Constructor for ComponentFileMismatch.
     */
    public ComponentFileMismatch(){
        KEYWORD = "File Name";
        count = 0;
    }

    /**
     * Scans a statement and counts it if it is a SYSTEM statement whose name doesn't match the file's name.
     * (If the SYSTEM statement doesn't have a name, that's a mismatch as well.)
     * @param statement The statement to be scanned
     */
    public void scan(Statement statement){
        String s = statement.getText();
        if (!getFirstToken(s).equals("COMMENT") && !getFirstToken(s).equals("END-SYSTEM")&& s.contains("SYSTEM")
                && !getFirstToken(s).toUpperCase().equals(Controller.currentFileName.toUpperCase())) {
            count++;
        }
    }
}
