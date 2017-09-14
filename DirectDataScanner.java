package Scans;

import CMS2Statements.Statement;

public class DirectDataScanner extends Scan {

    private String[] keyWords = new String[]{
            "OR", "MS", "XOR", "ALP", "NLP", "SLP", "SSUM", "SDIF", "DS", "RNLP",
            "DL", "LBMP", "LIM", "LA", "LXB", "LDIF", "LSUM", "LNA", "LM", "LB",
            "SB", "SA", "SXB", "SNA", "SM", "BZ", "BS", "RA", "RI", "RAN", "MD",
            "LBJ", "LCT", "LCI", "HLCT",
            "HLCI", "HM", "HD", "HRT", "HLB", "HSIM", "HSTC", "HPI", "HAI", "HALT",
            "HWFI", "SZ"
    };

    public DirectDataScanner() {
        KEYWORD = "Data";
        count = 0;
    }

    public void scan(Statement statement) {
        String text = statement.getText();
        if (statement.isDirectCode()) {
            int commentIndex = text.indexOf(".");
            if(commentIndex == -1){
                commentIndex = 100;
                //Direct Code statements can only be one one line,
                //and one line can only 80 characters long (really 70),
                //so setting the comment as beginning at index 100
                //makes the following code much simpler.
            }
            for (String s : keyWords) {
                int  sIndex = text.indexOf(" " + s + " ");
                if(text.indexOf(s + " ") == 0){ //Handles when the word being searched for
                    sIndex = 0;                 //appears in column 11 of the source file.
                }
                if (sIndex > -1 && sIndex < commentIndex) {
                    count++;
                    break;
                }
            }
        }
    }
}