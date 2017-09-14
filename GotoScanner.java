package Scans;

import CMS2Statements.Statement;

/**
 * This class scans code for High Level GOTO Statements
 */
public class GotoScanner extends Scan {

    public GotoScanner(){
        KEYWORD = "Goto";
        count = 0;
    }

    /**
     * Scans a statement and counts it if it is a High Level Goto Statement
     * @param statement The statement to be scanned
     */
    public void scan(Statement statement){
        if(!statement.isDirectCode() && getFirstToken(statement.getText()).equals("GOTO")){
            count++;
        }
    }
}
