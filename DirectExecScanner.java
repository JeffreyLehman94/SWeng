package Scans;

import CMS2Statements.Statement;

public class DirectExecScanner extends Scan {

    private String[] keyWords = new String[]{
            "SC", "LLP", "LLPN", "CNT", "XR", "XRL", "ROR", "RSC", "RMS",
            "RXOR", "TSF", "DA", "DAN", "DC", "FA", "FAN", "FM", "FD",
            "FAR", "FANR", "FMR", "FDR", "XS", "IPI", "AEI", "PEI", "IO", "IR", "RP",
            "ANA", "AA", "AB", "ANB", "M", "D", "BC", "CXI", "C", "CL", "CM", "CG",
            "JEP", "JOP", "DJZ", "DJNZ", "JP", "JN", "JZ", "JNZ", "JBNZ", "JS", "JL",
            "JNF", "JOF", "JNE", "JE", "JG", "JGE", "JLT", "JLE", "JNW", "JW", "RJ",
            "JNC", "RJSC", "J", "JC", "JSC", "SCT", "SCI", "HSCT", "HSCI",  "HLC", "HDLC",
            "HRZ", "HDRZ", "HRS", "HDRS", "HSF", "HDSF", "HCP", "HDCP", "HOR", "HA", "HAN",
            "HXOR", "HAND", "HC", "HCL", "HCM", "HCB", "ZA", "ZB", "NOOP", "HNO"
    };

    public DirectExecScanner() {
        KEYWORD = "Exec";
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
