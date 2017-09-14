package Controller;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

import Reports.Report;
import Reports.SourceAnalysis;

/**
 * Contains the main method of the program. Takes in file(s) and runs selected reports on them.
 *
 * @author Sean Zimmerman
 */

public class Controller {

    public static String currentFileName = "";
    public static String currentFileNameFull = ""; // needed for IncorrectFileExtension
    private final static String DEFAULTDIRECTORY = System.getProperty("user.dir");

    private static Report report = new SourceAnalysis(); // default just to have something

    /**
     * The main method of the program. Outputs the time the program takes to run.
     * @param args Any filenames (entered at the command line) to be scanned
     */
    public static void main(String args[]) {
        chooseFiles(args);
        System.exit(0);
    }

    /**
     * Choose the files and run the scan on them.
     * @param args Any filenames (entered at the command line) to be scanned
     */
    private static void chooseFiles(String [] args) {
        File files[] = getFiles(args);

        if(args.length != 0) {
            report = getReport(args[0]);
        } else {
            args = new String[1];

            String[] choiceOptions = {"Source Analysis", "Source Review"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Which type of summary do you want?",
                    "Which Report?",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choiceOptions,
                    choiceOptions[0]);
            if (choice == 0) {
                args[0] = "SourceAnalysis";
            } else {
                args[0] = "SourceReview";
            }
            report = getReport(args[0]);
        }

        report.generateReportColumns();

        for(int i = 0; i < files.length; i++) {
            currentFileNameFull = files[i].getName();
            currentFileName = currentFileNameFull.replaceFirst("[.][^.]+$", ""); // removes the file extension
            report.fillColumn(new FileScanner(files[i], getReport(args[0])).run());
        }

        // Prints the report
        report.generateReports();

        // An ArrayList of type String to keep track of invalid Files that were selected to scan
        ArrayList<String> invalidFiles = new ArrayList<>();


        for(File f: files) {
            if(!validExtension(getExtension(f))) {
                invalidFiles.add(f.getName());
                System.out.println("Invalid Extension: " + getExtension(f));
            }
        }

        if(invalidFiles.size() != 0) {
            System.out.println("Invalid Files: " + invalidFiles.toString());
        }
    }

    private static Report getReport(String s) {
        Report temp = Report.getReport(s);

        if(temp == null) {
            return new SourceAnalysis();
        } else {
            return temp;
        }
    }

    /**
     * Get the extension for a file
     * @param file The file to get the extension of
     * @return The extension of the file
     */
    private static String getExtension(File file) {
        String extension = "";

        int i = file.getAbsolutePath().lastIndexOf('.');
        int p = Math.max(file.getAbsolutePath().lastIndexOf('/'),
                file.getAbsolutePath().lastIndexOf('\\'));

        if (i > 0 && i > p) {
            extension = file.getAbsolutePath().substring(i+1);
        }

        return extension;
    }

    /**
     * Checks if the extension is of a valid type
     * @param ext Extension to check
     * @return If the extension is valid
     */
    private static boolean validExtension(String ext) {
        return ext.equals("txt");
    }

    /**
     * @author Jeffrey Lehman (updated by Alexander Mendelsohn)
     * @Date 02 Feb 2017
     * @return  files; an array of type File which the user has chosen using jfilechooser
     */
    private static File[] getFiles(String [] args){
        if (args.length == 0) {
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(true);
            chooser.showOpenDialog(new JFrame());
            File[] files = chooser.getSelectedFiles();
            return files;
        }
        File[] files = new File[args.length-1];
        for (int i = 1; i < args.length; i++) {
            files[i-1] = new File(DEFAULTDIRECTORY + "/" + args[i]);
        }
        return files;
    }
}
