package Scans;

import CMS2Statements.Statement;
import Controller.Controller;

/**
 * Mismatch occurs when the first 3 characters of a procedure name do not match the first 3 characters of the file name
 */
public class SystemLetterMismatchScanner extends Scan {
    private String fileNameChars;

    public SystemLetterMismatchScanner() {
        KEYWORD = "Systm Lettr";
        count = 0;
    }

    /**
     * Detemines if the first 3 characters of a procedure name do not match the first 3 characters of the file name
     * @param statement The statement to be scanned
     */
    public void scan(Statement statement) {
        fileNameChars = Controller.currentFileName.substring(0,3);
        String firstTok = getFirstToken(statement.getText());
        String secondTok = getSecondToken(statement.getText());

        // PROCEDURE ABSTRACT was the only other time PROCEDURE is used as first token
        if(firstTok.equals("PROCEDURE") && !secondTok.equals("ABSTRACT")) {
            // if the first three chars of the name do not match the procedure name then it is mismatched
            if(!secondTok.substring(0,3).equals(fileNameChars)) {
                count++;
            }
        }
    }
}
