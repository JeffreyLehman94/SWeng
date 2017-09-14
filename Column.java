package Report;

import java.util.ArrayList;

/**
 * Represents a column to be used in a table like format to generate a well formatted report.
 *
 * @author Sean Zimmerman
 */
public class Column {
    // The number of spaces used between columns when printing the report
    public static final int SPACES_BETWEEN_COLUMNS = 1;

    // The data of the column
    private ArrayList<String> data;
    private boolean rightJustify;
    private boolean headerRightJustify;
    private int width;

    // For printf the format is "%-xs" for left justify and "%xs" for right justify
    // x is the number of characters in the column. Spaces will be used for padding
    /**
     * Constructor for the Column
     * @param rightJustify whether or not the column is right justified
     * @param headerRightJustify whether or not the header is right justified
     * @param width the width of the column
     */
    public Column(boolean rightJustify, boolean headerRightJustify, int width) {
        data = new ArrayList<String>();

        this.rightJustify = rightJustify;
        this.headerRightJustify = headerRightJustify;
        this.width = width;
    }

    /**
     * Adds a row of data to the column
     * @param cell The data to be added
     */
    public void addData(String cell) {
        this.data.add(cell);
    }

    /**
     * Returns the value of the specified row of this column in correct formatting for the table.
     * @param num The row number wanted
     * @return The correctly formatted value in row num
     */
    public String getFormattedRowNum(int num) {
        if(num >= 2) {
            if(rightJustify) {
                return padLeft(data.get(num), width);
            } else {
                return padRight(data.get(num), width);
            }
        } else {
            if(headerRightJustify) {
                return padLeft(data.get(num), width);
            } else {
                return padRight(data.get(num), width);
            }
        }
    }

    /**
     * Returns a total for the entire column
     * @return The total of all the integers in the column and the total number of non zero entries
     */
    public ArrayList<Integer> totalNum() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int total = 0;
        int numEntries = 0;
        int temp = 0;

        // The first 3 entries of the column are always filler like column name
        for(int i = 3; i < data.size(); i++) {
            temp = Integer.parseInt(data.get(i));
            total += temp;
            if(temp != 0) {
                numEntries++;
            }
        }

        result.add(total);
        result.add(numEntries);

        return result;
    }

    /**
     * Accessor for width
     * @return the column's width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Accessor for data
     * @return returns an ArrayList of Strings for the column's data
     */
    public ArrayList<String> getData() {
        return this.data;
    }

    /**
     * Pads a String with " " to the right to the given length (n) (used for left justify).
     * @param s the given String
     * @param n the needed length
     * @return the modified String
     */
    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    /**
     * Pads a String with " " to the left to the given length (n) (used for right justify).
     * @param s the given String
     * @param n the needed length
     * @return the modified String
     */
    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }
}
