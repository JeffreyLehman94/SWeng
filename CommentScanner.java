package Scans;

import CMS2Statements.Statement;
import Reports.Entry;
import java.util.ArrayList;

/**
 * This class scans code for High Level Comments
 */
public class CommentScanner extends LineScan {

    /**
     * The constructor for CommentScanner
     */
    public CommentScanner(){
        KEYWORD = "Comment";
        count = 0;
        lineCount = 0;
    }

    /**
     * Scans a statement and counts it and its lines if it is a High Level Comment
     * @param statement The statement to be scanned
     */
    public void scan(Statement statement){
        if(!statement.isDirectCode() && getFirstToken(statement.getText()).equals("COMMENT")){
            count++;
            tallyLines(statement);
            statement.setClassified(true);
        }
    }
}
