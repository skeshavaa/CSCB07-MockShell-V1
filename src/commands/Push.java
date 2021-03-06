// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: santhos7
// UT Student #: 1006094673
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

import data.FileSystemI;
import errors.DirectoryException;
import errors.InvalidArgsProvidedException;

/**
 * Class Push is responsible forSaves the current working directory to 
 * the directory stack and changes the current working directory to 
 * the one the user gave if it is valid
 */
public class Push extends DirectoryManager implements CommandI {

  /**
   * Declare instance variable of Cd
   */
  private Cd goBack;

  /**
   * Declare instance variable of RedirectionManager to handle the redirection operations
   */
  RedirectionManager rManager;
  
  /**
   * Constructor for Push that initializes instance variables
   */
  public Push() {
    //Initializes the following objects
    this.goBack = new Cd();
    rManager = new RedirectionManager();
  }

  /**
   * Saves the current working directory to the directory stack and changes the
   * current working directory to the one the user gave if it is valid
   * 
   * @param filesys   refrence of FileSystemI object (MockFileSystem or FileSystem)
   * @param args  the string array of arguments
   * @param fullInput  the full line of input that the user gives into JShell
   * @param val holds a boolean value
   * 
   * @return String  An error message if any error, else null
   */
  public String run(FileSystemI filesys, String[] arg, String fullInput, boolean val) {
    //Gets the current path
    String current = filesys.getCurrentPath();
    //Seperates the parameters from everything else from the user input
    String[] args = rManager.setParams(fullInput);
    try {
      //Checks for redirection
      if (rManager.isRedirectionableCommand(fullInput));

      //Checks if the arguments are valid
      if(checkArgs(filesys, args, fullInput)){
        runPush(filesys, args, current);
      }
    } catch (InvalidArgsProvidedException e) {
      return e.getLocalizedMessage();
    }

    return null;
  }
  /**
   * Checks arguments to see if they are valid
   * 
   * @param fs  filesystem to be used
   * @param arguments  argumetns containing directory to be pushed
   * @param fullInput  full string input from the user
   * 
   * @return Boolean  returns a boolean if arguments are valid for command or not
   */
  @Override
  public boolean checkArgs(FileSystemI fs, String[] arguments, String fullInput) 
      throws InvalidArgsProvidedException {
    if (arguments.length == 0) {
      throw new InvalidArgsProvidedException("Error : No parameters provided");
    }else if(arguments.length > 1){
      String parameter = String.join(" ", arguments);
      throw new InvalidArgsProvidedException("Error : Multiple Parameters have been provided : " 
                    + parameter + " Only 1 valid directory path is required");
    }
    return true;
  }

  /**
   * After running checkArgs, runPush is run to actual push directory onto directory stack
   * 
   * @param filesys  filesystem to be used
   * @param args  arguments containing directory ot be pushed
   * @param current  current path of the location the user is in, in the filesystem
   * 
   * @throws DirectoryException  throws director exception if invalid path is pushed
   */
  private void runPush(FileSystemI filesys, String[] args, String current) throws DirectoryException{
    if (goBack.run(args, filesys)) {
      filesys.getStack().push(current);
    } else {
      throw new DirectoryException("Error: Invalid Directory : " + args[0] + " is not a valid directory");
    }  
    return;  
  }
}
