package Reports;

import Scans.*;
import Report.Column;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * SourceReview is a type of report which scans select features of CMS-2Y code. These features are:
 * Goto Statements
 * Structure Missing
 * Missing End System
 * Incorrect File Extension
 * Multiple Components
 * System Letter Mismatch
 * Module Mnemonic
 * Non-Standard Prime
 * CSwitch Bracketed Statements
 * Direct References
 * Abstract Missing
 *
 * @author Sean Zimmerman
 */
public class SourceReview extends Report {

    public SourceReview() {
        this.totalLinesArrayPos = -1;

        this.scans = new ArrayList<Scan>();

        // The header String for the report
        header = Column.padLeft("Source Review Summary", 75);

        header_grand = Column.padLeft("Source Review Summary", 75);
        header_grand += "\n";
        header_grand += Column.padLeft("Grand Summary", 71);
        header_grand += "\n";
        header_grand += Column.padLeft("Full class/Source", 73);

        scans.add(new NestdInclScanner());
        scans.add(new ProcedureOver250Scanner());
        scans.add(new GotoScanner());
        scans.add(new SystemNotStructuredScanner());
        scans.add(new MisplacedEndSysScanner());
        scans.add(new IncorrectFileExtension());
        scans.add(new MultipleComponentsScanner());
        scans.add(new ComponentFileMismatch());
        scans.add(new SystemLetterMismatchScanner());
        scans.add(new ModuleMnemonicScanner());
        scans.add(new NonStandardScanner());
        scans.add(new CSwitchRefncScanner());
        scans.add(new DirRefScanner());
        scans.add(new AbstractNotFound());
        scans.add(new Procedure230to250Scanner()); // is not in the table before the grand summary
    }

    /**
     * Generate the columns for the given Report.
     * @return The columns to be used for the report.
     */
    public ArrayList<Column> generateReportColumns() {
        Column nameColumn = new Column(false, false, 10);

        nameColumn.addData("");
        nameColumn.addData("   Name");
        nameColumn.addData("----------");

        data.add(nameColumn);

        Column typeColumn = new Column(false, false, 5);

        typeColumn.addData("");
        typeColumn.addData("Type");
        typeColumn.addData("---- ");

        data.add(typeColumn);

        Column nestdColumn = new Column(true, true, 5);

        nestdColumn.addData("Nestd");
        nestdColumn.addData("Incld");
        nestdColumn.addData("-----");

        data.add(nestdColumn);

        Column ds250 = new Column(true, false, 5);

        ds250.addData(" DS");
        ds250.addData(">250");
        ds250.addData("-----");

        data.add(ds250);

        Column goTo = new Column(true, true, 5);

        goTo.addData("GOTO");
        goTo.addData("Stmt");
        goTo.addData("-----");

        data.add(goTo);

        Column struc = new Column(true, true, 5);

        struc.addData("STRUC");
        struc.addData("Mssng");
        struc.addData("-----");

        data.add(struc);

        Column msplc = new Column(true, true, 5);

        msplc.addData("Msplc");
        msplc.addData("ENDSY");
        msplc.addData("-----");

        data.add(msplc);

        Column fileExt = new Column(true, true, 5);

        fileExt.addData("File");
        fileExt.addData("Extn");
        fileExt.addData("-----");

        data.add(fileExt);

        Column mult = new Column(true, true, 5);

        mult.addData("Mult");
        mult.addData("Comp");
        mult.addData("-----");

        data.add(mult);

        Column fileName = new Column(true, true, 5);

        fileName.addData("File");
        fileName.addData("Name");
        fileName.addData("-----");

        data.add(fileName);

        Column systltr = new Column(true, true, 5);

        systltr.addData("Systm");
        systltr.addData("Lettr");
        systltr.addData("-----");

        data.add(systltr);

        Column modMn = new Column(true, true, 5);

        modMn.addData("Modul");
        modMn.addData("Mnemn");
        modMn.addData("-----");

        data.add(modMn);

        Column nsPrime = new Column(true, true, 5);

        nsPrime.addData("N/S ");
        nsPrime.addData("Prime");
        nsPrime.addData("-----");

        data.add(nsPrime);

        Column cswtc = new Column(true, true, 5);

        cswtc.addData("CSWTC");
        cswtc.addData("Refnc");
        cswtc.addData("-----");

        data.add(cswtc);

        Column direct = new Column(true, true, 5);

        direct.addData("DIRCT");
        direct.addData("Refnc");
        direct.addData("-----");

        data.add(direct);

        Column abstr = new Column(true, true, 5);

        abstr.addData("ABSTR");
        abstr.addData("Mssng");
        abstr.addData("-----");

        data.add(abstr);

        // Grand Summary

        Column labeling = new Column(true, true, 16); // Not sure what this column is supposed to mean exactly

        labeling.addData("0CPS-3.1.1 5.1.5");
        labeling.addData("0CPS-3.1.3 5.1.1");
        labeling.addData("0CPS-3.1.3 5.1.3");
        labeling.addData("0CPS-3.1.3 5.1.3");
        labeling.addData("0CPS-3.1.3 5.1.3");
        labeling.addData("0CPS-3.1.3 5.1.3");
        labeling.addData("0CPS-3.1.3 5.1.2");
        labeling.addData("0CPS-3.1.3 5.1.2");
        labeling.addData("0CPS-3.1.3 5.1.2");
        labeling.addData("0CPS-3.1.3 5.1.1");

        grandSummary.add(labeling);

        Column expandedLabel = new Column(false, false, 47);

        expandedLabel.addData("GOTO statement found");
        expandedLabel.addData("SYSTEM not structured");
        expandedLabel.addData("Misplaced END-SYSTEM");
        expandedLabel.addData("Incorrect file extension");
        expandedLabel.addData("Multiple components in a file");
        expandedLabel.addData("Component name/file mismatch");
        expandedLabel.addData("System code letter mismatch");
        expandedLabel.addData("Module mnemonic mismatch");
        expandedLabel.addData("Non-standard prime procedure name");
        expandedLabel.addData("Component abstract not found");

        grandSummary.add(expandedLabel);

        Column numOccurances = new Column(true, true, 3);

        grandSummary.add(numOccurances);

        Column occurances = new Column(false, false, 17);

        occurances.addData("occurrence(s) in");
        occurances.addData("occurrence(s) in");
        occurances.addData("occurrence(s) in");
        occurances.addData("occurrence(s) in");
        occurances.addData("occurrence(s) in");
        occurances.addData("occurrence(s) in");
        occurances.addData("occurrence(s) in");
        occurances.addData("occurrence(s) in");
        occurances.addData("occurrence(s) in");
        occurances.addData("occurrence(s) in");

        grandSummary.add(occurances);

        Column numModules = new Column(true, true, 2);

        grandSummary.add(numModules);

        Column modules = new Column(false, false, 18);

        modules.addData("module(s) detected");
        modules.addData("module(s) detected");
        modules.addData("module(s) detected");
        modules.addData("module(s) detected");
        modules.addData("module(s) detected");
        modules.addData("module(s) detected");
        modules.addData("module(s) detected");
        modules.addData("module(s) detected");
        modules.addData("module(s) detected");
        modules.addData("module(s) detected");

        grandSummary.add(modules);

        return data;
    }

    private void fillGrandSummary() {
        Column numOccurances = grandSummary.get(2);
        Column numModules = grandSummary.get(4);
        ArrayList<Integer> temp = new ArrayList<Integer>();

        for(int i = 4; i <= 12; i++) {
            temp = data.get(i).totalNum();
            numOccurances.addData("" + temp.get(0));
            numModules.addData("" + temp.get(1));
        }
        temp = data.get(15).totalNum();
        numOccurances.addData("" + temp.get(0));
        numModules.addData("" + temp.get(1));
    }

    /**
     * Have the Report generate all necessary reports
     */
    public void generateReports() {
        Report.reportGeneration(this.header, this.data);
        fillGrandSummary();
        Report.reportGeneration(this.header_grand, this.grandSummary);

        System.out.println("\n\t\t\t\t\tProcedures containing 230-250 statements\n");
        Iterator<String> itty = Procedure230to250Scanner.procNames.iterator();
        while (itty.hasNext()) {
            System.out.print(itty.next() + "\t");
        }
        System.out.println("\n\n\t\t\tFiles Containing Procedures Over 250 Lines");
        System.out.print("FILES\t\tPROCEDURES");
        Iterator<String> ittyKey = ProcedureOver250Scanner.procNames.keySet().iterator();
        Iterator<String> ittyValue;
        String keyName;
        while (ittyKey.hasNext()) {
            keyName = ittyKey.next();
            System.out.print("\n" + keyName + "\t\t");
            ittyValue = ProcedureOver250Scanner.procNames.get(keyName).iterator();
            while (ittyValue.hasNext()) {
                System.out.print(ittyValue.next() + "\t");
            }
        }
        System.out.println("\n");
    }
}
