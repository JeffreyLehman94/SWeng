package Scans;

import CMS2Statements.Statement;

/**
 * Created by Zim on 4/15/2017.
 */
public class MultipleComponentsScanner extends Scan {

    private boolean inComponent = false;

    public MultipleComponentsScanner(){
        KEYWORD = "Mult Comp";
        count = 0;
    }

    public void scan(Statement statement){
        if(!inComponent && getSecondToken(statement.getText()).equalsIgnoreCase("system")){
            inComponent = true;
        }else if(inComponent && getFirstToken(statement.getText()).equalsIgnoreCase("end-system")){
            inComponent = false;
            count++; //Does this need to count how many, or just confirm if there is more than one?
        }


    }
}
