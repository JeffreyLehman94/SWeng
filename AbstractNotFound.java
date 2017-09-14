package Scans;

import CMS2Statements.Statement;
import Reports.Entry;

import java.util.ArrayList;

/**
 * The idea of this scan is that it finds the 6 keywords which indicate
 * the start of a call which requires an abstract (in accordance to the given outline), incrementing the counter
 * by 1.
 * Then it will look for the next COMMENT block. If it contains the string ABSTRACT it will decrement
 * the counter by 1. At the end of scanning a file if the counter is not 0, it signifies there is a missing
 * abstract.
 * The variable abstractSearch shows when the method should be looking for the comment block with, "ABSTRACT", just to ensure
 * the word is not miscounted somewhere else.
 */
public class AbstractNotFound extends Scan {
    private String[] keyWords = new String[]{
            "SYSTEM", "SYS-DD", "SYS-PROC", "PROCEDURE", "EXEC-PROC", "FUNCTION"
    };
    public AbstractNotFound() {
        KEYWORD = "Abstract";
        count = 0;
    }

    public void scan(Statement statement) {
        String text = statement.getText();
        if (!statement.isDirectCode()) {
            // Checks if a COMMENT block contains the string ABSTRACT
            // Decrements counter by 1 if true
            if (getFirstToken(text).equals("COMMENT") && text.contains("ABSTRACT")) {
                count--;
//                System.out.println("FOUND ABSTRACT");
            }
            //Checks if a statement that requires an ABSTRACT has begun
            // Increments counter by 1 if a match is found
            else {
                for (String s : keyWords) {
                    if (!getFirstToken(text).equals("COMMENT") && text.contains(s) && !text.contains("END-"+s)) {
                        count++;
                        break;
                    }
                }
            }
        }
    }
}
