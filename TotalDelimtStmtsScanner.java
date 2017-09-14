package Scans;

import CMS2Statements.Statement;

/**
 * A scanner which extends the Scan which is called to count the numger of delimited statements
 * in cms2-y code.
 *
 * @author Jeff
 */
public class TotalDelimtStmtsScanner extends Scan {

    // Array containing all Delimiter Statements keywords in the CMS2 language
    // These are the keywords we were pointed to when asked
    // If incorrect, just delete the keywords and add in the correct
    String[] delimiters = new String[]{
            "SET", "SWAP", "GOTO", "IF", "VARY", "FIND"};

    public TotalDelimtStmtsScanner() {
        KEYWORD = "Delim";
        count = 0;
    }

    /**
     * Scans for specific keywords which start a delimited statement
     * Uses getFirstToken(statement.getText()); to ensure that it does not count the
     * keywords in COMMENTs.
     *
     * @param statement The statement to be scanned
     */
    public void scan(Statement statement) {
        String text = getFirstToken(statement.getText());
//        System.out.println(text);
        if (!statement.isDirectCode()) {
            for (String s : delimiters) {
                if (text.equals(s)) {
                    count++;
                    break;
                }
            }
        }
    }
}