package Scans;

import CMS2Statements.Statement;
import Controller.Controller;

/**
 * Ensures that the first three characters of the file name are the first three characters of the names for the following:
 * SYSTEM, SYS-DD, LOC-DD, AUTO-DD, SYS-PROC, EXEC-PROC, PROCEDURE, FUNCTION
 */
public class ModuleMnemonicScanner extends Scan {
    private String[] keyWords = new String[]{
            "SYSTEM", "SYS-DD", "LOC-DD", "AUTO-DD", "SYS-PROC", "PROCEDURE", "EXEC-PROC", "FUNCTION"
    };
    private String fileNameChars;

    public ModuleMnemonicScanner() {
        KEYWORD = "Module Mnemonic";
        count = 0;
    }

    /**
     * Checks if the first three characters of the file name are the first three characters of the names
     * of the keyword types.
     * @param statement The statement to be scanned
     */
    public void scan(Statement statement) {
        fileNameChars = Controller.currentFileName.substring(0,3);
        String firstTok = getFirstToken(statement.getText());
        String secondTok = getSecondToken(statement.getText());

        // SYSTEM, SYS-DD, and SYS-PROC all have the names before the keyword
        // EXEC-PROC and PROCEDURE always has it after
        // Will assume before for LOC-DD and AUTO-DD and after for FUNCTION

        // handle the before cases, so firstTok is name and secondTok is the type
        // Don't have to worry about ABSTRACT for this one since the type would be firstTok in that case
        if((secondTok.equals("SYSTEM") || secondTok.equals("SYS-DD") || secondTok.equals("SYS-PROC"))
                && !firstTok.equals("COMMENT")) {
            // if the first three chars of the name do not match the procedure name then it is mismatched
            if(!firstTok.substring(0,3).equals(fileNameChars)) {
                count++;
            }
        }

        // handle the after cases, EXEC-PROC has some weird formatting so I will deal with that after
        if((firstTok.equals("PROCEDURE") || firstTok.equals("FUNCTION"))
                && !secondTok.equals("ABSTRACT")) {
            // if the first three chars of the name do not match the procedure name then it is mismatched
            if(!secondTok.substring(0,3).equals(fileNameChars)) {
                count++;
            }
        }

        // Get the location of EXEC-PROC in the line
        String[] line = statement.getText().trim().split(" ");
        int loc = -1;
        for(int i = 0; i < line.length; i++) {
            if(line[i].equals("EXEC-PROC")) {
                loc = i;
            }
        }
        // EXEC-PROC is in the line, so the name is loc+1
        if(loc != -1 && loc <= line.length-2) {
            // if the first three chars of the name do not match the procedure name then it is mismatched
            // need to use loc+2 since there are 2 spaces between each element
            if(!line[loc+2].substring(0,3).equals(fileNameChars)) { // 2 because 2 spaces
                count++;
            }
        }
    }
}
