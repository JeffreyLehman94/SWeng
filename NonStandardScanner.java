package Scans;

import CMS2Statements.Statement;

/**
 * A Non-Standard Prime is one in which the suffix of the procedure will be:
 * "INT", "INI", "MSG", "ERR", "SUC", "IOB", "IOC", "PER", "MSG", "TDP", "BGD", or "TCR"
 */
public class NonStandardScanner extends Scan {
    boolean found;
    private String[] keywords = new String[]{
            "INT", "INI", "MSG", "ERR", "SUC", "IOB", "IOC", "PER", "MSG", "TDP", "BGD", "TCR"
    };

    public NonStandardScanner() {
        KEYWORD = "N/S Prime";
        count = 0;
        found = false;
    }

    /**
     * Determines if the suffix of the procedure name is one of the approved suffixes
     * @param statement The statement to be scanned
     */
    public void scan(Statement statement) {
        found = false;
        String firstTok = getFirstToken(statement.getText());
        String secondTok = getSecondToken(statement.getText());

        // PROCEDURE ABSTRACT was the only other time PROCEDURE is used as first token
        if(firstTok.equals("PROCEDURE") && !secondTok.equals("ABSTRACT")) {
            // if the last three chars of the procedure name do not match any approved suffixes then it is non standad prime
            int i = 0;
            while(!found && i < keywords.length) {
                if(secondTok.substring(secondTok.length()-3).equals(keywords[i])) {
                    found = true;
                }
                i++;
            }
            // None of the suffixes were matched, so the name is non standard
            if(!found) {
                count++;
            }
        }
    }
}
