package Scans;

import CMS2Statements.Statement;
import Reports.Entry;

import java.util.ArrayList;
import java.util.Arrays;

public class TotalMXLVScanner extends Scan {
    private int maxStructLevel;
    private int currentStructLevel;

    //Words that increase structure level, and will be the first token of a statement.
    private ArrayList<String> structIncreasersFirst = new ArrayList<>(Arrays.asList(
            "LOC-DD",// "END-LOC-DD",
            "PROCEDURE",// "END-PROC",
            "EXEC-PROC",// "END-PROC",
            "FUNCTION",// "END-FUNCTION",
            "SYS-PROC-REN",// "END-SYS-PROC",
            "AUTO-DD",// "END-AUTO-DD",
            "HEAD"//, "END-HEAD"
    ));
    //Words that increase structture level, and will be the second token of a statement.
    private ArrayList<String> structIncreasersSecond = new ArrayList<>(Arrays.asList(
            "SYSTEM", // "END-SYSTEM",
            "SYS-DD",// "END-SYS-DD",
            "SYS-PROC"// "END-SYS-PROC",
    ));

    private ArrayList<String> structDecreasers = new ArrayList<>(Arrays.asList(
            "END-SYSTEM",
            "END-SYS-DD",
            "END-SYS-PROC",
            "END-LOC-DD",
            "END-PROC",
            "END-FUNCTION",
            "END-SYS-PROC",
            "END-AUTO-DD",
            "END-HEAD"
    ));

    public TotalMXLVScanner(){
        super();
        maxStructLevel = 0;
        currentStructLevel = 0;
        KEYWORD = "MX LV";
    }

    public void scan(Statement statement){
        // This acts under the assumption that the file is formatted properly.
        // If it is not then it max structure level can not be determined.

        if(structIncreasersFirst.contains(getFirstToken(statement.getText())) ||
                structIncreasersSecond.contains(getSecondToken(statement.getText()))){
            updateStructLevel(currentStructLevel + 1);
        }
        else if(structDecreasers.contains(getFirstToken(statement.getText()))){
            updateStructLevel(currentStructLevel - 1);
        } else if(statement.getText().toLowerCase().contains(" begin ")){
            updateStructLevel(currentStructLevel + 1);
        } else if(statement.getText().toLowerCase().contains(" end ")){
            updateStructLevel(currentStructLevel - 1);
        }
    }

    public ArrayList<Entry> getData() {
        ArrayList<Entry> data = new ArrayList<Entry>();

        data.add(new Entry(KEYWORD, maxStructLevel));

        return data;
    }

    private void updateStructLevel(int newStructLevel){
        currentStructLevel = newStructLevel;
        if(currentStructLevel > maxStructLevel){
            maxStructLevel = currentStructLevel;
        }
    }

}
