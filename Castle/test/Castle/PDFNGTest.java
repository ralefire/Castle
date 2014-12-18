/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Castle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
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
public class PDFNGTest {
    
    public PDFNGTest() {
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
     * Test of getQuestionsLoaded method, of class PDF.
     * @throws java.io.IOException
     */
    @Test
    public void testGetQuestionsLoaded() throws IOException, ParseException {
        System.out.println("getQuestionsLoaded");
        PDF instance = new PDF();
        String filePath = "";
        instance.load(filePath);
        boolean expResult = false;
        boolean result = instance.getQuestionsLoaded();
        assertEquals(result, expResult);
        /*
        filePath = "src\\resources\\6dot1.pdf";
        instance.load(filePath);
        expResult = true;
        result = instance.getQuestionsLoaded();
        assertEquals(result, expResult);
        */
    }

    /**
     * Test of setQuestionsLoaded method, of class PDF.
     */
    @Test
    public void testSetQuestionsLoaded() {
        System.out.println("setQuestionsLoaded");
        boolean bool = false;
        PDF instance = new PDF();
        instance.setQuestionsLoaded(bool);
    }

    /**
     * Test of getQuestionMap method, of class PDF.
     */
    @Test
    public void testGetQuestionMap() {
        System.out.println("getQuestionMap");
        PDF instance = new PDF();
        Map expResult = new HashMap();
        Map result = instance.getQuestionMap();
        assertEquals(result, expResult);
    }

    /**
     * Test of load method, of class PDF.
     */
    @Test
    public void testLoad(){
        System.out.println("load");
        String filePath = "src\\resources\\6dot1.pdf";
        PDF instance = new PDF();
        try {
            instance.load(filePath);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(PDFNGTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Test of save method, of class PDF.
     */
    @Test
    public void testSave_String() throws Exception {
        System.out.println("save");
        String filePath = "src\\resources\\6dot1.pdf";
        PDF instance = new PDF();
        instance.load(filePath);
        filePath = "src\\resources\\test.pdf";
        instance.save(filePath);
    }

    /**
     * Test of buildPDF method, of class PDF.
     */
    @Test
    public void testBuildPDF() throws Exception {
        System.out.println("buildPDF");
        PDF instance = new PDF();
        String filePath = "src\\resources\\6dot1.pdf";
        instance.load(filePath);
        filePath = "src\\resources\\test.pdf";
        instance.buildPDF(filePath);
    }

    /**
     * Test of setKeywords method, of class PDF.
     * @throws java.io.IOException
     */
    @Test
    public void testSetKeywords() throws IOException, ParseException {
        System.out.println("setKeywords");
        JSONArray data = new JSONArray();
        PDF instance = new PDF();
        
        String filePath = "src\\resources\\6dot1.pdf";
        instance.load(filePath);
        instance.setKeywords(data);
    }
}
