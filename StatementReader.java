package CMS2Statements;

import CMS2Statements.Statement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class reads statements of from a file, records them in an ArrayList, and notes the number of lines of code
 */
public class StatementReader {
    private File file;
    private ArrayList<Statement> statements;
    private int lineNum;

    /**
     * The constructor, which also separates a file into a list of statements.
     * @param file whose code is to be read
     */
    public StatementReader(File file) {
        this.file = file;
        this.statements = new ArrayList<Statement>();
        this.lineNum = 0;
        build();
    }

    /**
     * Populates the list of statements and sets lineNum
     */
    public void build() {
        // Boolean to keep track if the scan is currently in a Direct Code block
        boolean inDirectBlock = false;

        int stmtStartLine = -1;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String statementText = "";

            while ((line = br.readLine()) != null) {
                lineNum++;
                //Change tabs into spaces
                line = transformTabs(line);
                //Take the first ten characters off. They are not used.
                if (line.length() > 10) {
                    line = line.substring(10);
                } else {
                    line = "";
                }
                ArrayList<String> parts = new ArrayList<String>();
                parts.add(line);
                if(!inDirectBlock) {
                    parts = divide(line, '$');
                }

                for(String part : parts) {
                    if(stmtStartLine == -1){
                        stmtStartLine = lineNum;
                    }
                    statementText += part;
                    if (!inDirectBlock) {
                        if (statementText.contains("$")) {
                            //New code, test
                            int dotIndex = statementText.indexOf('.');
                            String label = "";
                            if(dotIndex != -1 && isLabel(statementText, dotIndex)){
                                label = statementText.substring(0, dotIndex + 1);
                                statementText = statementText.substring(dotIndex + 1);
                            }
                            //End new code
                            statements.add(new Statement(statementText,
                                    stmtStartLine, lineNum, false, label));
                            if (getFirstToken(statementText).equals("DIRECT")) {
                                inDirectBlock = true;
                            }
                            //clear appropriate values
                            statementText = "";
                            stmtStartLine = -1;
                        }
                    } else { //current statement is in a direct code block
                        statementText = line;
                        if (getFirstToken(statementText).equals("CMS-2")) {
                            inDirectBlock = false;
                            statements.add(new Statement(statementText, stmtStartLine, lineNum, false, ""));
                        } else {
                            statements.add(new Statement(statementText, stmtStartLine, lineNum, true, ""));
                        }
                        statementText = "";
                        stmtStartLine = -1;
                    }
                }
                //statement = "";
                //System.out.println("TEST: " + line + " trimmed to " + afterDelim(line, '$'));
            }
        }
        catch(IOException ie) {

        }
    }

    /**
     * Accessor for statements.size()
     * @return the number of statements that have been read
     */
    public int numStmts(){
        return statements.size();
    }

    /**
     * Accessor for lineNum
     * @return the number of lines that have been read
     */
    public int numLines(){
        return lineNum;
    }

    /**
     * Accessor for a given Statement in statements
     * @param index the index of the Statement in statements
     * @return the requested statement
     */
    public Statement getStatement(int index){
        return statements.get(index);
    }

    /**
     * Accessor for statements
     * @return an ArrayList of the statements read
     */
    public ArrayList<Statement> getStatements(){
        return statements;
    }

    /**
     * Returns the given string with everything after the first instance of the delimiter removed.
     * @param line the String to have everything after the first instance of the delimiter removed
     * @param delimiter the given delimeter
     * @return the given string with everything after the first instance of the delimiter removed
     */
    private String trimDelim(String line, char delimiter){
        int delimiterIndex = line.indexOf(delimiter);
        if(delimiterIndex == -1) {
            return line;
        }
        return line.substring(0, delimiterIndex + 1);
    }

    /**
     * Takes a statement and returns its first token, which can identify the statement's type.
     * (If it is possible that the first token isn't followed by a space, needs to be changed.)
     * @param statement a statement of code
     * @return the first token of the statement
     */
    private String getFirstToken(String statement){
        if(statement.trim().indexOf(" ") != -1){
            return statement.trim().substring(0, statement.trim().indexOf(" ")).trim();
        }
        return statement.trim();
    }

    /**
     * Returns the original String with tabs turned into spaces
     * @param string the String in question
     * @return the original String with tabs turned into spaces
     */
    private String transformTabs(String string){
        String[] segments = string.split("\t");
        String result = "";
        for(String seg : segments){
            result = result.concat(seg + "    ");
        }
        if(result.length() >= 4) {
            result = result.substring(0, result.length() - 4);
            return result;
        }
        return string; //check.
    }

    /**
     * Returns everything after the first instance of the delimiter, or the whole string if it
     * does not contain the delimiter
     * @param string the string to be trimmed
     * @param delimiter the delimiter to return everything after
     * @return a String containing everything after the first instance of the delimiter or the whole string if it
     * does not contain the delimiter
     */
    private String afterDelim(String string, char delimiter){
        if(string.indexOf(delimiter) > -1){
            return string.substring(string.indexOf(delimiter) + 1);
        }
        return string;
    }

    /**
     * Uses a delimiter to turn a String into an ArrayList of Strings
     * @param string the String in question
     * @param delimiter the delimiter used to divide the String
     * @return the ArrayList of Strings that were separated by the delimiter in the original String
     */
    private ArrayList<String> divide(String string, char delimiter){
        ArrayList<String> result = new ArrayList<String>();
        while(string.indexOf(delimiter) > -1){
            result.add(trimDelim(string, delimiter));
            string = afterDelim(string, delimiter);
        }
        if(!string.trim().equals("")){
            result.add(string);
        }
        return result;
    }

    //The CMS2-Y Programming guide states that a dot ".", can be used for 3 things.
    //  1. To mark a label, what we are looking for.
    //  2. As a decimal point. Easy to rule out. If it is proceeded by an alphabetic character it can't be this.
    //  3. As a decimal point in octal (a radix point). The same check will work.
    // Must also check that the . isn't in a comment of course.
    private boolean isLabel(String statementText, int dotIndex){
        return  dotIndex >= 1
                && !getFirstToken(statementText).toLowerCase().equals("comment")
                && Character.isAlphabetic(statementText.charAt(dotIndex - 1));
    }

}
