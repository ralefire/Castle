/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Castle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class QuestionPrompterNGTest {
    
    public QuestionPrompterNGTest() {
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
     * Test of getAnswers method, of class QuestionPrompter.
     */
    @Test
    public void testGetAnswers() {
        System.out.println("getAnswers");
        QuestionPrompter instance = new QuestionPrompter();
        Map expResult = new HashMap();
        Map result = instance.getAnswers();
        assertEquals(result, expResult);
    }

    /**
     * Test of setAnswers method, of class QuestionPrompter.
     */
    @Test
    public void testSetAnswers() {
        System.out.println("setAnswers");
        Map<Question, String> answers = new HashMap<>();
        QuestionPrompter instance = new QuestionPrompter();
        instance.setAnswers(answers);
    }

    /**
     * Test of setQuestions method, of class QuestionPrompter.
     */
    @Test
    public void testSetQuestions() {
        System.out.println("setQuestions");
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("1", "2", "Text"));
        QuestionPrompter instance = new QuestionPrompter();
        instance.setQuestions(questions);
    }

    /**
     * Test of addAnswer method, of class QuestionPrompter.
     */
    @Test
    public void testAddAnswer() {
        System.out.println("addAnswer");
        Question question = new Question("1", "2", "Text");
        String answer = "This is an answer";
        QuestionPrompter instance = new QuestionPrompter();
        instance.addAnswer(question, answer);
        Map<Question, String> result = instance.getAnswers();
        Map<Question, String> expResult = new HashMap<>();
        expResult.put(question, answer);
        assertEquals(result, expResult);
    }
    
}
