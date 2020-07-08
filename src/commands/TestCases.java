package commands;

import java.util.ArrayList;

import data.FileSystem;

public class TestCases {
  
  private FileSystem fs;
  
  private Cd cd;
  private Pwd pwd;
  private Mkdir mkdir;
  private Ls ls;
  private Echo echo;
  private Man man;
  
  private String output;
  private String currentPath;
  
  public TestCases() {
    this.fs = FileSystem.getFileSys();
    this.cd = new Cd();
    this.pwd = new Pwd();
    this.mkdir = new Mkdir();
    this.ls = new Ls();
    this.echo =  new Echo();
    this.man = new Man();
  }
  
  public void setupEnviro() {
    String[] setupDirs = new String[1];
    
    //C Folder
    setupDirs[0] = "users";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "pics";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "Sys";
    mkdir.MakeDirectory(setupDirs);
    echo.run(setupDirs, "echo \"Wow what a project\" > A2");
    
    //users Folder
    setupDirs[0] = "C/users";
    cd.run(setupDirs, "cd C/users");
    setupDirs[0] = "desktop";
    mkdir.MakeDirectory(setupDirs);
    
    //desktop Folder
    setupDirs[0] = "C/users/desktop";
    cd.run(setupDirs, "cd C/users/desktop");    
    echo.run(setupDirs, "echo \"Hello TA\" > CSCB07");
    echo.run(setupDirs, "echo \"2+2=5\" > Hwk");
    setupDirs[0] = "../../";
    cd.run(setupDirs, "cd ../../");
    
    //pics Folder
    echo.run(setupDirs, "echo \"this is a picturefile indeed\" > pics/picfile");
    setupDirs[0] = "pics";
    cd.run(setupDirs, "cd pics");
    echo.run(setupDirs, "echo \"Hello TA from the pics Folder\" > CSCB07");
    setupDirs[0] = "..";
    cd.run(setupDirs, "cd ..");

    //Sys Folder
    setupDirs[0] = "Sys";
    cd.run(setupDirs, "cd Sys");
    setupDirs[0] = "IO";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "LOL";
    mkdir.MakeDirectory(setupDirs);
    
    //IO Folder
    setupDirs[0] = "IO";
    cd.run(setupDirs, "cd IO");
    setupDirs[0] = "keyboard";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "Mouse";
    mkdir.MakeDirectory(setupDirs);
    
    //keyboard Folder
    setupDirs[0] = "keyboard";
    cd.run(setupDirs, "cd keyboard");
    echo.run(setupDirs, "echo \"QWERTY\" > keys");
    echo.run(setupDirs, "echo \"RGB == ways more      F    P   S\" > RGB");
    setupDirs[0] = "../";
    cd.run(setupDirs, "cd ../");
    
    //Mouse Folder
    setupDirs[0] = "Mouse";
    cd.run(setupDirs, "cd Mouse");
    echo.run(setupDirs, "echo \"Mouse is in Mouse Folder\" > Presses");

    //C Folder
    setupDirs[0] = "../../../";
    cd.run(setupDirs, "cd ../../../");
  }
  
  public void cdTestCases() {
    
    //Case 1: No Input
    String[] testCase1 = {};
    
    output = cd.run(testCase1, "cd");
    currentPath = pwd.run(testCase1, "pwd");
    
    if(output.equals("Error : No parameters provided : ")) 
      System.out.println("Case #1 Passed");
    else
      System.out.println("Case #1 Failed");
    
    
   //Case 2: Change Directory to pics
    String[] testCase2 = new String[1];
    testCase2[0] = "pics";
        
    output = cd.run(testCase2, "cd pics");
    currentPath = pwd.run(testCase1, "pwd");
    
    
    if(currentPath.equals("C/pics")) 
      System.out.println("Case #2 Passed");
    else
      System.out.println("Case #2 Failed");
    
    //Case 3: Change Directory back one
    testCase2[0] = "..";
    
    output = cd.run(testCase2, "cd ..");
    currentPath = pwd.run(testCase1, "pwd");
    
    
    if(currentPath.equals("C")) 
      System.out.println("Case #3 Passed");
    else
      System.out.println("Case #3 Failed");
    
    //Case 4: Change Directory to users
    testCase2[0] = "users/desktop";
    
    output = cd.run(testCase2, "cd users/desktop");
    currentPath = pwd.run(testCase1, "pwd");
    
    
    if(currentPath.equals("C/users/desktop")) 
      System.out.println("Case #4 Passed");
    else
      System.out.println("Case #4 Failed");
    
    //Case 5: Pattern -> ../..
    testCase2[0] = "../..";
    
    output = cd.run(testCase2, "cd ../..");
    currentPath = pwd.run(testCase1, "pwd");
    
    if(currentPath.equals("C")) 
      System.out.println("Case #5 Passed");
    else
      System.out.println("Case #5 Failed");
    
    //Case 6: Pattern -> Change directory to a file --> Issue
    testCase2[0] = "A2";
    
    output = cd.run(testCase2, "cd A2");
    currentPath = pwd.run(testCase1, "pwd");
    
    if(currentPath.equals("C")) 
      System.out.println("Case #6 Passed -> Not Actually");
    else
      System.out.println("Case #6 Failed -> Not Actually");
    
    //Case 7: Changing directory to keyboard Folder
    testCase2[0] = "C/Sys/IO/keyboard";
    
    output = cd.run(testCase2, "cd C/Sys/IO/keyboard");
    currentPath = pwd.run(testCase1, "pwd");
    
    if(currentPath.equals("C/Sys/IO/keyboard")) 
      System.out.println("Case #7 Passed");
    else
      System.out.println("Case #7 Failed");
    
    //Case 8: Change directory to C
    testCase2[0] = "../../../";
    
    output = cd.run(testCase2, "cd ../../../");
    currentPath = pwd.run(testCase1, "pwd");
    
    if(currentPath.equals("C")) 
      System.out.println("Case #8 Passed");
    else
      System.out.println("Case #8 Failed");
    
  }
  
  
  
  public void manTestCases() {
	  
	  String output;
	  String[] testInput = {"ls"};
	  
	  System.out.println("Testing Command: man");
	  
	  //Case 1: Get the documentation for ls
	  output = man.run(testInput, "man");
	  
	  if (output.contains("Command: ls")) {
		  System.out.println("Case #1 Passed");
	  }else {
		  System.out.println("Case #1 Failed");
	  }
	  
	  //Case 2: Get documentation for invalid input
	  testInput[0] = "false command";
	  output = man.run(testInput, "man");
	  
	  if (output.contains("Invalid Argument")) {
		  System.out.println("Case #2 Passed");
	  }else {
		  System.out.println("Case #2 Failed");
	  }
	  
	  //Case 3: Get documentation for multiple arguments (not supported)
	  String[] multipleArguments = {"ls", "cd"};
	  output = man.run(testInput, "man");
	  
	  if (output.contains("Invalid Argument")) {
		  System.out.println("Case #3 Passed");
	  }else {
		  System.out.println("Case #3 Failed");
	  }
  }
  
  public void mkdirTestCases() {
	  System.out.println("Testing Command: mkdir");
	  
	  //Case 1: Make a directory under root directory
	  String[] input = {"users"};
	  mkdir.run(input, "mkdir " + input[0]);
	  
	  ArrayList<String> allContent = new ArrayList<String>();
	  
	  for (int i = 0; i < fs.getCurrent().getList().size(); i++) {
		  allContent.add(fs.getCurrent().getList().get(i).getName());
	  }
	  
	  if (allContent.contains(input[0])) {
		  System.out.println("Case #1 Passed");
	  } else {
		  System.out.println("Case #1 Failed");
	  }
	  
	  allContent.clear();
	  
	  //Case 2: Make a directory using absolute path
	  input[0] = "C/users/newUser";
	  mkdir.run(input, "mkdir " + input[0]);
	  
	  String[] enterDirectoryAbove = {"C/users"};
	  cd.run(enterDirectoryAbove);
	  
	  for (int i = 0; i < fs.getCurrent().getList().size(); i++) {
		  allContent.add(fs.getCurrent().getList().get(i).getName());
	  }
	  
	  if (allContent.contains("newUser")) {
		  System.out.println("Case #2 Passed");
	  } else {
		  System.out.println("Case #2 Failed");
	  }
	  
	  allContent.clear();
	  
	  //Case 3: Make a directory using relative path
	  input[0] = "users/newUser2";
	  
	  mkdir.run(input, "mkdir " + input[0]);
	  cd.run(enterDirectoryAbove);
	  
	  for (int i = 0; i < fs.getCurrent().getList().size(); i++) {
		  allContent.add(fs.getCurrent().getList().get(i).getName());
	  }
	  
	  if (allContent.contains("newUser2")) {
		  System.out.println("Case #3 Passed");
	  } else {
		  System.out.println("Case #3 Failed");
	  }
	  
  }
	
	
}
