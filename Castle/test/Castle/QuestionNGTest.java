package Castle;

import java.util.List;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Xandron
 */
public class QuestionNGTest {
    
    public QuestionNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of getPrompt method, of class Question.
     */
    @Test
    public void testGetPrompt() {
        System.out.println("getPrompt");
        Question instance = null;
        String expResult = "";
        String result = instance.getPrompt();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPrompt method, of class Question.
     */
    @Test
    public void testSetPrompt() {
        System.out.println("setPrompt");
        String prompt = "";
        Question instance = null;
        instance.setPrompt(prompt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHash method, of class Question.
     */
    @Test
    public void testGetHash() {
        System.out.println("getHash");
        Question instance = null;
        String expResult = "";
        String result = instance.getHash();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHash method, of class Question.
     */
    @Test
    public void testSetHash() {
        System.out.println("setHash");
        String hash = "";
        Question instance = null;
        instance.setHash(hash);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getType method, of class Question.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        Question instance = null;
        String expResult = "";
        String result = instance.getType();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setType method, of class Question.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        String type = "";
        Question instance = null;
        instance.setType(type);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPosAnswers method, of class Question.
     */
    @Test
    public void testGetPosAnswers() {
        System.out.println("getPosAnswers");
        Question instance = null;
        List expResult = null;
        List result = instance.getPosAnswers();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPosAnswers method, of class Question.
     */
    @Test
    public void testSetPosAnswers() {
        System.out.println("setPosAnswers");
        List<String> posAnswers = null;
        Question instance = null;
        instance.setPosAnswers(posAnswers);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Question.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Question instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
