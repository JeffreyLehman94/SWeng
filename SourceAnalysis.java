package Reports;

import Report.Column;
import Scans.*;

import java.util.ArrayList;

/**
 * SourceAnalysis is a type of report which scans select features of CMS-2Y code. These features are:
 * High Level CMS:
 *   Executable Statements
 *   Executable Lines
 *   Data Statements
 *   Data Lines
 *   Comment Statements
 *   Comment Lines
 *   NonComment Lines
 *   Other Statements
 * CMS-2 Direct:
 *   Executable Statements
 *   Data Statements
 *   Comment Lines
 * Total:
 *   Executable Statements
 *   Source Lines
 *   CSWTC Statements
 *   MX LV
 *   Delimt Statements
 *
 * @author Sean Zimmerman
 */
public class SourceAnalysis extends Report {

    public SourceAnalysis() {
        this.totalLinesArrayPos = 14;

        this.scans = new ArrayList<Scan>();

        // The header String for the report
        header = Column.padLeft("Source Analysis Summary", 78);
        header += "\n";
        header += "               +----------------- High Level CMS-2 ------------------+";
        header += " +-- CMS-2 DIRECT --+";
        header += " +--- Total ---+";

        header_grand = Column.padLeft("Source Analysis Summary", 78);
        header_grand += "\n";
        header_grand += Column.padLeft("Grand Summary", 77);

        scans.add(new HighLevelExecScanner());
        scans.add(new HighLevelDataScanner());
        scans.add(new CommentScanner());
        scans.add(new HighLevelNonCommentScanner());
        scans.add(new HighLevelOtherScanner());
        scans.add(new DirectExecScanner());
        scans.add(new DirectDataScanner());
        scans.add(new DirCommentScanner());
        scans.add(new TotalExecScanner());
        scans.add(new TotalCSWTCScanner());
        scans.add(new TotalMXLVScanner());
        scans.add(new TotalDelimtStmtsScanner());
    }

    /**
     * Generate the columns for the given Report.
     * @return The columns to be used for the report.
     */
    public ArrayList<Column> generateReportColumns() {
        Column nameColumn = new Column(false, false, 10);

        nameColumn.addData("");
        nameColumn.addData("  Name");
        nameColumn.addData("---------");

        data.add(nameColumn);

        Column typeColumn = new Column(true, true, 4);

        typeColumn.addData("");
        typeColumn.addData("Type");
        typeColumn.addData("----");

        data.add(typeColumn);

        Column execstsmt = new Column(true, false, 6);

        execstsmt.addData(" Exec");
        execstsmt.addData(" Stmts");
        execstsmt.addData("------");

        data.add(execstsmt);

        Column execlines = new Column(true, false, 6);

        execlines.addData("Exec");
        execlines.addData("Lines");
        execlines.addData("------");

        data.add(execlines);

        Column datastmts = new Column(true, false, 6);

        datastmts.addData("Data");
        datastmts.addData("Stmts");
        datastmts.addData("------");

        data.add(datastmts);

        Column datalines = new Column(true, false, 6);

        datalines.addData("Data");
        datalines.addData("Lines");
        datalines.addData("------");

        data.add(datalines);

        Column commentstmts = new Column(true, true, 6);

        commentstmts.addData("Cmmnt");
        commentstmts.addData("Stmts");
        commentstmts.addData("------");

        data.add(commentstmts);

        Column commentlines = new Column(true, true, 6);

        commentlines.addData("Cmmnt");
        commentlines.addData("Lines");
        commentlines.addData("------");

        data.add(commentlines);

        Column noncmtlines = new Column(true, true, 6);

        noncmtlines.addData("NonCmt");
        noncmtlines.addData("Lines");
        noncmtlines.addData("------");

        data.add(noncmtlines);

        Column otherstmts = new Column(true, true, 5);

        otherstmts.addData("Other");
        otherstmts.addData("Stmts");
        otherstmts.addData("-----");

        data.add(otherstmts);

        Column execstmtsdir = new Column(true, false, 6);

        execstmtsdir.addData(" Exec");
        execstmtsdir.addData(" Stmts");
        execstmtsdir.addData("------");

        data.add(execstmtsdir);

        Column datastmtsdir = new Column(true, false, 6);

        datastmtsdir.addData(" Data");
        datastmtsdir.addData(" Stmts");
        datastmtsdir.addData("------");

        data.add(datastmtsdir);

        Column commentlinesdir = new Column(true, false, 6);

        commentlinesdir.addData("Cmmnt");
        commentlinesdir.addData("Lines");
        commentlinesdir.addData("------");

        data.add(commentlinesdir);

        Column execstmtstotal = new Column(true, false, 7);

        execstmtstotal.addData(" Exec");
        execstmtstotal.addData(" Stmts");
        execstmtstotal.addData("-------");

        data.add(execstmtstotal);

        Column sourcelines = new Column(true, true, 7);

        sourcelines.addData("Source");
        sourcelines.addData("Lines");
        sourcelines.addData("-------");

        data.add(sourcelines);

        Column cswtcstmts = new Column(true, false, 5);

        cswtcstmts.addData("CSWTC");
        cswtcstmts.addData("Stmts");
        cswtcstmts.addData("----");

        data.add(cswtcstmts);

        Column mxlv = new Column(true, true, 2);

        mxlv.addData("MX");
        mxlv.addData("LV");
        mxlv.addData("--");

        data.add(mxlv);

        Column delimtstmts = new Column(true, true, 6);

        delimtstmts.addData("Delimt");
        delimtstmts.addData("Stmts");
        delimtstmts.addData("------");

        data.add(delimtstmts);

        // Grand Summary

        Column empty = new Column(true, true, 19);

        empty.addData("");
        empty.addData("");
        empty.addData("");
        empty.addData("");
        empty.addData("");
        empty.addData("");
        empty.addData("");
        empty.addData("");
        empty.addData("");
        empty.addData("");
        empty.addData("");
        empty.addData("");
        empty.addData("");
        empty.addData("");
        empty.addData("");
        empty.addData("");
        empty.addData("");

        grandSummary.add(empty);

        Column labeling = new Column(true, true, 2);

        labeling.addData("A-");
        labeling.addData("B-");
        labeling.addData("C-");
        labeling.addData("D-");
        labeling.addData("E-");
        labeling.addData("F-");
        labeling.addData("G-");
        labeling.addData("H-");
        labeling.addData("I-");
        labeling.addData("J-");
        labeling.addData("K-");
        labeling.addData("");
        labeling.addData("");
        labeling.addData("");
        labeling.addData("");
        labeling.addData("");
        labeling.addData("");

        grandSummary.add(labeling);

        // 38

        Column expandedLabel = new Column(false, false, 40);

        expandedLabel.addData("High Level Executable Statements");
        expandedLabel.addData("High Level Executable Lines");
        expandedLabel.addData("High Level Data Statements");
        expandedLabel.addData("High Level Data Lines");
        expandedLabel.addData("High Level Comment Statements");
        expandedLabel.addData("High Level Comment Lines");
        expandedLabel.addData("High Level Non-Comment Lines (B+D+H)");
        expandedLabel.addData("High Level Other Statements");
        expandedLabel.addData("Direct Code Executable Statements");
        expandedLabel.addData("Direct Code Data Statements");
        expandedLabel.addData("Direct Code Comment Statements");
        expandedLabel.addData("TOTAL EXECUTABLE STATEMENTS (A+I)");
        expandedLabel.addData("TOTAL LINES (F+G+I+J+K)");
        expandedLabel.addData("CSWITCH Bracketed Statements");
        expandedLabel.addData("MX LV (Maximum Structure Level)");
        expandedLabel.addData("Average Number of DS per Procedure");
        expandedLabel.addData("TOTAL DELIMITED STATEMENT COUNT");

        grandSummary.add(expandedLabel);

        Column numbers = new Column(true, true, 7);

        grandSummary.add(numbers);

        return data;
    }

    /**
     * Fills the columns of the grand summary with totals of the data columns
     */
    private void fillGrandSummary() {
        Column numbers = grandSummary.get(3);

        for(int i = 2; i <= 16; i++) {
            numbers.addData("" + data.get(i).totalNum().get(0));
        }
        numbers.addData("0"); // For average number of delimited statements per procedure, not sure what to do there
        numbers.addData("" + data.get(17).totalNum().get(0));
    }

    /**
     * Have the Report generate all necessary reports
     */
    public void generateReports() {

        Report.reportGeneration(this.header, this.data);
        System.out.println();
        fillGrandSummary();
        Report.reportGeneration(this.header_grand, this.grandSummary);
    }

}
