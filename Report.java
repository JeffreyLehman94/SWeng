package Reports;

import Report.Column;
import Scans.Scan;
import java.util.ArrayList;

/**
 * A Report consistents of a collection of Scans that the user wants to run on the given input file.
 * To create a new report, make a subclass of whatever title, and in the default constructor
 * instantiate scans with whatever scan objects desired.
 * Then fill in the functionality for generateReport().
 *
 *
 * @author Sean Zimmerman
 */
public abstract class Report {
    protected ArrayList<Column> data = new ArrayList<Column>();
    protected ArrayList<Column> grandSummary = new ArrayList<Column>();
    protected ArrayList<Scan> scans;

    protected int totalLinesArrayPos;

    protected String header;
    protected String header_grand;

    /**
     * Accessor for totalLinesArrayPos
     * @return an array position later used by Controller.FileScanner
     */
    public int getTotalLinesArrayPos() {
        return totalLinesArrayPos;
    }

    /**
     * Generate the columns for the given Report.
     * @return The columns to be used for the report.
     */
    public abstract ArrayList<Column> generateReportColumns();

    /**
     * Accessor for scans
     * @return the scans in this Report
     */
    public ArrayList<Scan> getScans() {
        return this.scans;
    }

    /**
     * Generates the report given a header String to put on top of the report and then the
     * properly formatted general report underneath. Header String will want to include anything
     * not in the normal table, which only includes column names and column data.
     * @param header Any additional information to be printed onto the report
     * @param data The data to be printed
     */
    public static void reportGeneration(String header, ArrayList<Column> data) {
        /*
         To convert this to just a String return just append "\n" at the end of every line
         instead of System.out.println(line) after the nested loop.
          */
        System.out.println(header);

        // Print the data
        String line;

        // For every row in the data
        for(int i = 0; i < data.get(0).getData().size(); i++) {
            line = "";
            // For every column in the data
            for(int j = 0; j < data.size(); j++) {
                // Append the correctly formatted String for that column
                line += data.get(j).getFormattedRowNum(i);
                // Append the specified number of spaces between each column
                line += numSpaces(Column.SPACES_BETWEEN_COLUMNS);
            }
            System.out.println(line);
        }
    }

    /**
     * Create a String consisting of only the specified number of spaces.
     * @param num The number of spaces to create
     * @return The String with the number of spaces
     */
    public static String numSpaces(int num) {
        String result = "";

        for(int i = 0; i < num; i++) {
            result += " ";
        }

        return result;
    }

    /**
     * Fills the columns with a single row of data (whatever was originally there + data)
     * @param newData Data to fill it with
     */
    public void fillColumn(ArrayList<String> newData) {
        for(int i = 0; i < data.size(); i++) {
            data.get(i).addData(newData.get(i));
        }
    }

    public ArrayList<Column> getData() {
        return this.data;
    }

    public ArrayList<Column> getGrandSummary() {
        return this.grandSummary;
    }

    public String getHeader() {
        return this.header;
    }

    public String getHeader_grand() {
        return this.header_grand;
    }

    /**
     * Generates all of the Reports for this report, usually including the normal report + grand summary
     */
    public abstract void generateReports();

    /**
     * Gives you a new report of the given type.
     * @param input The type of report you want
     * @return The desired type, or null
     */
    public static Report getReport(String input) {
        if(input.equals("SourceAnalysis")) {
            return new SourceAnalysis();
        } else if(input.equals("SourceReview")) {
            return new SourceReview();
        }
        return null;
    }
}
