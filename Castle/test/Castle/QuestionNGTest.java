package Castle;

import java.util.ArrayList;
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
        Question instance = new Question("1", "2", "Radio");
        String expResult = "1";
        String result = instance.getPrompt();
        assertEquals(result, expResult);
    }

    /**
     * Test of setPrompt method, of class Question.
     */
    @Test
    public void testSetPrompt() {
        System.out.println("setPrompt");
        String prompt = "What is your name?";
        Question instance = new Question("prompt", "name", "Text");
        instance.setPrompt(prompt);
        String result = instance.getPrompt();
        String expResult = "What is your name?";
        assertEquals(result, expResult);
    }

    /**
     * Test of getHash method, of class Question.
     */
    @Test
    public void testGetHash() {
        System.out.println("getHash");
        Question instance = new Question("Name", "NAME", "Text");
        String expResult = "NAME";
        String result = instance.getHash();
        assertEquals(result, expResult);
    }

    /**
     * Test of setHash method, of class Question.
     */
    @Test
    public void testSetHash() {
        System.out.println("setHash");
        String hash = "dog";
        Question instance = new Question("prompt", "name", "Radio");
        instance.setHash(hash);
        String result = instance.getHash();
        String expResult = hash;
        assertEquals(result, expResult);
    }

    /**
     * Test of getType method, of class Question.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        Question instance = new Question("prompt", "name", "Radio");
        String expResult = "Radio";
        String result = instance.getType();
        assertEquals(result, expResult);
    }

    /**
     * Test of setType method, of class Question.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        String type = "Text";
        Question instance = new Question("prompt", "name", "Radio");
        instance.setType(type);
        String result = instance.getType();
        String expResult = type;
        assertEquals(result, expResult);
    }

    /**
     * Test of getPosAnswers method, of class Question.
     */
    @Test
    public void testGetPosAnswers() {
        System.out.println("getPosAnswers");
        Question instance = new Question("prompt", "name", "Radio");
        List expResult = new ArrayList();
        List result = instance.getPosAnswers();
        assertEquals(result, expResult);
    }

    /**
     * Test of setPosAnswers method, of class Question.
     */
    @Test
    public void testSetPosAnswers() {
        System.out.println("setPosAnswers");
        List<String> posAnswers = new ArrayList<>();
        posAnswers.add("spot");
        Question instance = new Question("prompt", "dog", "Radio");
        instance.setPosAnswers(posAnswers);
        List<String> result = instance.getPosAnswers();
        List<String> expResult = posAnswers;
        assertEquals(result, expResult);
    }

    /**
     * Test of toString method, of class Question.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Question instance = new Question("prompt", "name", "Radio");
        String expResult = "name";
        String result = instance.toString();
        assertEquals(result, expResult);
    }
    
}
