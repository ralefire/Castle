/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Castle;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
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
     */
    @Test
    public void testGetQuestionsLoaded() {
        System.out.println("getQuestionsLoaded");
        PDF instance = new PDF();
        boolean expResult = false;
        boolean result = instance.getQuestionsLoaded();
        assertEquals(result, expResult);
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
     * Test of setAnswers method, of class PDF.
     */
    @Test
    public void testSetAnswers() {
        System.out.println("setAnswers");
        Map<Question, String> answersMap = null;
        PDF instance = new PDF();
        instance.setAnswers(answersMap);
    }

    /**
     * Test of getQuestionMap method, of class PDF.
     */
    @Test
    public void testGetQuestionMap() {
        System.out.println("getQuestionMap");
        PDF instance = new PDF();
        Map expResult = null;
        Map result = instance.getQuestionMap();
        assertEquals(result, expResult);
    }

    /**
     * Test of isLoaded method, of class PDF.
     */
    @Test
    public void testIsLoaded() {
        System.out.println("isLoaded");
        String filePath = "src\\resources\\6dot1.pdf";
        PDF instance = new PDF();
        try {
            instance.load(filePath);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(PDFNGTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Boolean expResult = true;
        Boolean result = instance.isLoaded();
        assertEquals(result, expResult);
        
        filePath = "xyz\\src\\resources\\6dot1.pdf";
        instance = new PDF();
        try {
            instance.load(filePath);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(PDFNGTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        expResult = false;
        result = instance.isLoaded();
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
        boolean result = instance.isLoaded();
        boolean expResult = true;
        assertEquals(result, expResult);
        
        filePath = "xy\\src\\resources\\6dot1.pdf";
        instance = new PDF();
        try {
            instance.load(filePath);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(PDFNGTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        result = instance.isLoaded();
        expResult = false;
        
        assertEquals(result, expResult);
        
    }

    /**
     * Test of insertResponses method, of class PDF.
     */
    @Test
    public void testInsertResponses() throws Exception {
        System.out.println("insertResponses");
        PDF instance = new PDF();
        instance.insertResponses();
    }

    /**
     * Test of save method, of class PDF.
     */
    @Test
    public void testSave_String() throws Exception {
        System.out.println("save");
        String filePath = "";
        PDF instance = new PDF();
        instance.save(filePath);
    }

    /**
     * Test of save method, of class PDF.
     */
    @Test
    public void testSave_PDDocument_String() throws Exception {
        System.out.println("save");
        PDDocument doc = null;
        String filePath = "";
        PDF instance = new PDF();
        instance.save(doc, filePath);
    }

    /**
     * Test of export method, of class PDF.
     */
    @Test
    public void testExport() {
        System.out.println("export");
        String filename = "";
        PDF instance = new PDF();
        instance.export(filename);
    }

    /**
     * Test of addAnswersToTextContent method, of class PDF.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddAnswersToTextContent() throws Exception {
        System.out.println("addAnswersToTextContent");
        PDF instance = new PDF();
        instance.addAnswersToTextContent();
    }

    /**
     * Test of buildPDF method, of class PDF.
     */
    @Test
    public void testBuildPDF() throws Exception {
        System.out.println("buildPDF");
        String filePath = "";
        PDF instance = new PDF();
        instance.buildPDF(filePath);
    }

    /**
     * Test of addResponses method, of class PDF.
     */
    @Test
    public void testAddResponses() throws Exception {
        System.out.println("addResponses");
        PDF instance = new PDF();
        instance.addResponses();
    }

    /**
     * Test of extractText method, of class PDF.
     */
    @Test
    public void testExtractText() throws Exception {
        System.out.println("extractText");
        PDF instance = new PDF();
        String expResult = "";
        String result = instance.extractText();
        assertEquals(result, expResult);
    }

    /**
     * Test of setKeywords method, of class PDF.
     */
    @Test
    public void testSetKeywords() {
        System.out.println("setKeywords");
        JSONArray data = null;
        PDF instance = new PDF();
        instance.setKeywords(data);
    }

    /**
     * Test of getKeywordsAsJSONArray method, of class PDF.
     */
    @Test
    public void testGetKeywordsAsJSONArray() throws Exception {
        System.out.println("getKeywordsAsJSONArray");
        PDF instance = new PDF();
        JSONArray expResult = null;
        JSONArray result = instance.getKeywordsAsJSONArray();
        assertEquals(result, expResult);
    }

    /**
     * Test of getKeywordsAsString method, of class PDF.
     */
    @Test
    public void testGetKeywordsAsString() throws Exception {
        System.out.println("getKeywordsAsString");
        PDF instance = new PDF();
        String expResult = "";
        String result = instance.getKeywordsAsString();
        assertEquals(result, expResult);
    }

    /**
     * Test of attachKeysFile method, of class PDF.
     */
    @Test
    public void testAttachKeysFile() throws Exception {
        System.out.println("attachKeysFile");
        PDF instance = new PDF();
        instance.attachKeysFile();
    }

    /**
     * Test of loadPDFAttachment method, of class PDF.
     */
    @Test
    public void testLoadPDFAttachment() throws Exception {
        System.out.println("loadPDFAttachment");
        String filePath = "";
        PDF instance = new PDF();
        instance.loadPDFAttachment(filePath);
    }

    /**
     * Test of getEmbeddedFile method, of class PDF.
     */
    @Test
    public void testGetEmbeddedFile() {
        System.out.println("getEmbeddedFile");
        PDComplexFileSpecification fileSpec = null;
        PDEmbeddedFile expResult = null;
        PDEmbeddedFile result = PDF.getEmbeddedFile(fileSpec);
        assertEquals(result, expResult);
    }
    
}
