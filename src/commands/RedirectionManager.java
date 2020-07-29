// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Shawn Santhoshgeorge
//
// Student2:
// UTORID user_name: shaiskan
// UT Student #: 1006243940
// Author: Keshavaa Shaiskandan
//
// Student3:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Tirth Patel
//
// Student4:
// UTORID user_name: pate1101
// UT Student #: 1006315765
// Author: Abhay Patel
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package commands;

import java.util.ArrayList;
import java.util.Arrays;

import data.FileSystemI;

/**
 * Class RedirectionManager is responsible for handling redirection of output to
 * file
 */
public class RedirectionManager {

    /**
     * Declare instance of ErrorHandler to handle error messages
     */
    private ErrorHandler errorManager;

    /**
     * Declares instance variable of ArrayList that holds all the commands that can
     * handle redirection
     */
    private ArrayList<String> redirectClasses;

    /**
     * Declare instance variables of String to hold teh file name and what type of
     * redirection is to be used
     */
    String mode, fileName;

    /**
     * Constructor for RedirectionManager which initializes the instance variables
     * and populates teh ArrayList with command names
     */
    public RedirectionManager() {
        // Initializes a ErrorHandler Object
        errorManager = new ErrorHandler();
        // Creates a ArrayList Object called redirectClasses
        redirectClasses = new ArrayList<String>();
        // Initializes the ArrayList with values
        redirectClasses.addAll(Arrays.asList("cat", "echo", "find", "history", "ls", "man", "pwd", "tree"));
    }

    /**
     * Checks if the given command is a redirectionable command or
     * 
     * @param fs        refrence of the file system(FileSystem or MockFileSystem)
     * @param fullInput the user input
     * @return true if command is redirectionable and false other wise
     */
    public boolean isRedirectionableCommand(FileSystemI fs, String fullInput) {
        // Retrives the command from the user input
        String command = fullInput.split(" ")[0];

        // If the given command is a redirectionalble command
        if (redirectClasses.contains(command)) {
            // Command is redirectionable
            return true;
        } else {
            // Checks if the command uses either > or >> in
            // the parameters and returns an error if so
            if (Arrays.asList(fullInput.split(" ")).contains(">")
                    || Arrays.asList(fullInput.split(" ")).contains(">>")) {
                // Returns the respective error
                outputResult(fs,
                        errorManager.getError("Redirection Not allowed", command + " does not support redirection"));
                // Command is not redirectionable
                return false;
            }
        }

        // If the given command is a redirectionalble command return true
        return redirectClasses.contains(fullInput.split(" ")[0]);
    }

    /**
     * 
     * @param fs        refrence of the file system(FileSystem or MockFileSystem)
     * @param fullInput the user input
     * @return the parameters for the command to run or null if multiple file names
     *         are provided
     */
    public String[] setParams(FileSystemI fs, String fullInput) {
        // Initializes an array containing the words of the parsedInput
        String[] params = fullInput.split(" ");

        // If the user used the single arrow >> which sets redirection to overwrite a
        // file
        if (Arrays.asList(params).contains(">")) {
            // Sets the redirection mode to overwrite
            mode = "O";
            // Collects the file name
            fileName = setFileName(params, ">");
            // Collects the parameters for the command
            params = Arrays.copyOfRange(params, 1, Arrays.asList(params).indexOf(">"));
            // If the user used the single arrow >> which sets redirection to append a file
        } else if (Arrays.asList(params).contains(">>")) {
            // Sets the redirection mode to append
            mode = "A";
            // Collects the file name
            fileName = setFileName(params, ">>");
            // Collects the parameters for the command
            params = Arrays.copyOfRange(params, 1, Arrays.asList(params).indexOf(">>"));
            // If the user did not use redirection for a redirectionable command
        } else {
            // Collects the parameters for the command
            params = Arrays.copyOfRange(fullInput.split(" "), 1, fullInput.split(" ").length);
            // Sets the fileName and mode to be empty
            fileName = "";
            mode = "";
        }

        // If the user provided multiple file names return the error
        if (fileName.startsWith("Error")) {
            // Print the error to the console
            outputResult(fs, fileName);
            // Return no parameters
            return null;
        }

        // Returns the parameters
        return params;
    }

    /**
     * Collects the file name from the user input
     * 
     * @param params the user input but split up
     * @param type   either append(">>") or overwrite(">")
     * @return the file name or null with the error printed on the console
     */
    public String setFileName(String[] params, String type) {
        // Collects all the content after the either ">" or ">>"
        String[] fileName = Arrays.copyOfRange(params, Arrays.asList(params).indexOf(type) + 1, params.length);
        // If the user provided no file name for the redirection
        if (fileName.length == 0) {
            // Sends the error message instead of a fileName
            return errorManager.getError("No parameters provided", "");
            // If the user provided multiple file names
        } else if (fileName.length > 1) {
            // Converts the array to String
            String parameter = Arrays.toString(params);
            parameter = parameter.substring(1, parameter.length() - 1).replace(",", "").trim();
            // Sends the error message instead of a fileName
            return errorManager.getError("Multiple parameters provided", parameter + " Only one is required");
        }
        // If the user provided one file name then return that file name
        return fileName[0];
    }

    /**
     * Redirects the content to a file or back to the console
     *
     * @param fs     refrence of the file system(FileSystem or MockFileSystem)
     * @param result the string to be outputed to the console
     * @return returns null is ther is not issue or returns the result or does the
     *         redirection
     */
    public String outputResult(FileSystemI fs, String result) {
        // Checks if the operation returns any input or not
        if (result == null) {
            return null;
            // If the return was some sort of an Error prints the error out
        } else if (result.startsWith("Error")) {
            return result.trim();
            // If the user want to overwrite a file with this new result
        } else if (mode.equals("O")) {
            fs.fileOverwrite(result, fileName);
            // Resets the redirection
            mode = "";
            return null;
            // If the user want to append to the file with this new result
        } else if (mode.equals("A")) {
            fs.fileAppend(result, fileName);
            // Resets the redirection
            mode = "";
            return null;
            // If the user chooses not to redirect the results to a file
        } else {
            // If the user is not using redirection at all
            return result.trim();
        }
    }
}