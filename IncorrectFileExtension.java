package Scans;

import CMS2Statements.Statement;
import Controller.Controller;
import Reports.Entry;

import java.util.ArrayList;

/**
 * Created by Zim on 4/15/2017.
 */
public class IncorrectFileExtension extends Scan {
    public IncorrectFileExtension(){
        KEYWORD = "File Extn";
        count = 0;
    }

    public void scan(Statement statement){

    }

    /**
     * This scanner does not check statements, it checks the extension of a file once.
     * Therefore this is necessary and the scan function is not.
     */
//    public void checkExtension(String extension){
//        if(extension.equals("txt")){
//            count++;
//        }
//    }

    /**
     * Overrides Scan's getData() method so that it returns how many procedures match.
     * @return number of matching procedures
     */
    public ArrayList<Entry> getData() {
        ArrayList<Entry> data = new ArrayList<Entry>();

        if (!Controller.currentFileNameFull.equals(Controller.currentFileName + ".txt"))
            count = 1;
        data.add(new Entry(KEYWORD + " Mishaps", count));

        return data;
    }
}
