package CMS2Statements;

/**
 * Represents a statement of code.
 *
 * Created by Jay on 3/11/2017.
 */
public class Statement {
    private String text;
    private int beginningLine;
    private int endingLine;
    private boolean directCode;
    private String label;
    private boolean classified;

    /**
     * Constructor for the Statement
     * @param text              The text of a statement
     * @param beginningLine     The line the statement begins on
     * @param endingLine        The line the statement end on
     * @param directCode        Whether this statement is in a direct code block
     * @param label             This lines label. If there is none, its is an empty string
     */
    public Statement(String text, int beginningLine, int endingLine, boolean directCode, String label){
        this.text = text;
        this.beginningLine = beginningLine;
        this.endingLine = endingLine;
        this.directCode = directCode;
        this.label = label;
        this.classified = false;
    }

    /**
     * Accessor for text
     * @return the statement's text
     */
    public String getText() {
        return text;
    }

    /**
     * Accessor for beginningLine
     * @return the location of the line the statement starts on
     */
    public int getBeginningLine() {
        return beginningLine;
    }

    /**
     * Accessor for endinggLine
     * @return the location of the line the statement ends on
     */
    public int getEndingLine() {
        return endingLine;
    }

    /**
     * Accessor for directCode
     * @return whether or not the statement is Direct Code
     */
    public boolean isDirectCode() {
        return directCode;
    }

    /**
     * Accessor for getNumLines
     * @return the number of lines the statement occupies
     */
    public int getNumLines(){
        return endingLine - beginningLine + 1;
    }

    public boolean isClassified(){
        return classified;
    }

    public void setClassified(boolean classified){
        this.classified = classified;
    }
}
