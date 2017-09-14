package Scans;

import CMS2Statements.Statement;
import java.util.ArrayList;
import Reports.Entry;

/**
 * This class scans code for Direct Code Comments.
 */
public class DirCommentScanner extends Scan{

    /**
     * The constructor for DirCommentScanner.
     */
    public DirCommentScanner() {
        KEYWORD = "Direct Code Comment";
        count = 0;
    }

    /**
     * Scans a statement and counts it if it a Direct Code Comment is found.
     * @param statement The statement to be scanned
     */
    public void scan(Statement statement){
        if(statement.isDirectCode() && statement.getText().contains(".")){
            count++;
        }
    }

    /**
     * A slight variation of getData() in the Scan class.
     * @return the data from the scan
     */
    public ArrayList<Entry> getData() {
        ArrayList<Entry> result = new ArrayList<Entry>();

        result.add(new Entry("Direct Code Comment Lines", count));

        return result;
    }
}
