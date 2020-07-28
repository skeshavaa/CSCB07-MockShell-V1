package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Man;

/**
 * Class ManTest runs all the different test cases for Man
 */
public class ManTest {

    /**
    * Declare instance of MockFileSystem so we can access the preset filesystem
    */
    private static MockFileSystem fs;

    /**
    * Declare instance of Man to be tested
    */
    private static Man man;

    /**
    * Declare two different instance of a String 
    * objects called expected and actual 
    */
    private String expected, actual;

    /**
     * Sets up the test environment and initializes the instance variables
     * 
     * @throws Exception
    */
    @Before
    public void setup() throws Exception{
        //Gets a specific preset FileSystem
        fs = MockFileSystem.getMockFileSys("EMPTYSYS");
        // Initializes a Man Object
        man = new Man();


    }

    
    /**
     * Destroys the FileSystem after all the testcases have run
     * 
     * @throws Exception
    */
    @After
    public void tearDown() throws Exception {
        //Declares and initializes a Feild variable 
        //to the fileSys variable in FileSystem
        Field feild = fs.getClass().getDeclaredField("filesys");
        //Allows the value of this variable in FileSystem to be accessed
        feild.setAccessible(true);
        //Changes the value of the variable in FileSystem to null
        feild.set(null, null);
    }

    /**
     * Test A : User provides no input command for Man to look up
     */
    @Test
    public void testANoArgs(){
        //Declares and initializes an empty array
        String[] emptyArr = {};
        //Expected return from Man
        expected = "Error : No parameters provided : Man requires one supported command";
        //Actual return from Man after the operation has been run
        actual = man.run(fs,emptyArr, "man ", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);      
    }

    /**
     * Test B : User provides a random unspported command of JShell
     */
    @Test
    public void testBUnsupportedCommand(){
        //Expected return from Man
        expected = "Error: Invalid Command : LOL is not a supported command supported one is required";
        //Actual return from Man after the operation has been run
        actual = man.run(fs,"LOL".split(" "), "man LOL", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);      
    }

    /**
     * Test C : User provides a random supported command of JShell
     */
    @Test
    public void testCSupportedCommand(){
        //Expected return from Man
        expected =         
        "Command : exit" 
        + "\n\tCloses the current session and leaves the Shell"
        + "\n\n\tParameter : None"
        + "\n\n\tIf any or multiple of these parameter are not meet an "
        + "\n\terror will be outputed respectively"
        + "\n\n\tREDIRECTION : This command does not allow the output"
        + "\n\tto be redirected to a file instead of the console "
        + "\n\tif there is any output for the command";
        //Actual return from Man after the operation has been run
        actual = man.run(fs,"exit".split(" "), "man exit", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);      
    }

    /**
     * Test D : User asks for the manual for man
     */
    @Test
    public void testDCommandMan(){
        //Expected return from Man
        expected = 
        "Command: man CMD" 
        + "\n\tProvides documentation on all commands within THIS Java Shell"
        + "\n\tProvides information such as arguments and function."
        + "\n\n\tREDIRECTION : This command allows the output to be redirected "
        + "\n\tto the a file instead of the console"
        + "\n\n\tParameter: Requires one supported CMD"
        + "\n\n\tIf any or multiple of these parameter are not meet an "
        + "\n\terror will be outputed respectively"
        + "\n\n\tREDIRECTION : This command does not allow the output"
        + "\n\tto be redirected to a file instead of the console "
        + "\n\tif there is any output for the command"
        + "\n\n\tSample Use Case : man man "
        + "\n\tThis would output the the mannual for man"
        + "\n\n\tSample Use Case: man man > mannul"
        + "\n\tThe file named mannul gets overwritten with the mannual for man";
        //Actual return from Man after the operation has been run
        actual = man.run(fs,"man".split(" "), "man man", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);      
    }

    /**
     * Test E : User provided multiple commnads
     */
    @Test
    public void testEMultipleArgs(){
        //Expected return from Man
        expected = "Error : Multiple Parameters have been provided : speak ls, only supported one is required";
        //Actual return from Man after the operation has been run
        actual = man.run(fs,"speak ls".split(" "), "man speak ls", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);  
    }


    
}