/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Castle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.TextPosition;
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
public class TextStripperNGTest {
    
    public TextStripperNGTest() {
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
     * Test of getLineSpaces method, of class TextStripper.
     */
    @Test
    public void testGetLineSpaces() throws IOException {
        System.out.println("getLineSpaces");
        TextStripper instance = new TextStripper();
        List expResult = new ArrayList();
        List result = instance.getLineSpaces();
        assertEquals(result, expResult);
    }

    /**
     * Test of getIndents method, of class TextStripper.
     */
    @Test
    public void testGetIndents() throws IOException {
        System.out.println("getIndents");
        TextStripper instance = new TextStripper();
        List expResult = new ArrayList();
        List result = instance.getIndents();
        assertEquals(result, expResult);
    }

    /**
     * Test of setIndents method, of class TextStripper.
     */
    @Test
    public void testSetIndents() throws IOException {
        System.out.println("setIndents");
        List<Object> indents = null;
        TextStripper instance = new TextStripper();
        instance.setIndents(indents);
    }

    /**
     * Test of getContent method, of class TextStripper.
     */
    @Test
    public void testGetContent() throws IOException {
        System.out.println("getContent");
        TextStripper instance = new TextStripper();
        String expResult = "";
        String result = instance.getContent();
        assertEquals(result, expResult);
    }

    /**
     * Test of setContent method, of class TextStripper.
     */
    @Test
    public void testSetContent() throws IOException {
        System.out.println("setContent");
        String content = "";
        TextStripper instance = new TextStripper();
        instance.setContent(content);
    }

    /**
     * Test of getFullContent method, of class TextStripper.
     */
    @Test
    public void testGetFullContent() throws IOException {
        System.out.println("getFullContent");
        TextStripper instance = new TextStripper();
        String expResult = "";
        String result = instance.getFullContent();
        assertEquals(result, expResult);
    }

    /**
     * Test of setFullContent method, of class TextStripper.
     */
    @Test
    public void testSetFullContent() throws IOException {
        System.out.println("setFullContent");
        String fullContent = "";
        TextStripper instance = new TextStripper();
        instance.setFullContent(fullContent);
    }

    /**
     * Test of processLocation method, of class TextStripper.
     */
    @Test
    public void testProcessLocation() throws Exception {
        System.out.println("processLocation");
        PDDocument doc = new PDDocument();
        TextStripper instance = new TextStripper();
        instance.processLocation(doc);
    }

    /**
     * Test of getCharacters method, of class TextStripper.
     */
    @Test
    public void testGetCharacters() throws IOException {
        System.out.println("getCharacters");
        TextStripper instance = new TextStripper();
        List expResult = new ArrayList();
        List result = instance.getCharacters();
        assertEquals(result, expResult);
    }

    /**
     * Test of getMinXWidth method, of class TextStripper.
     */
    @Test
    public void testGetMinXWidth() throws IOException {
        System.out.println("getMinXWidth");
        TextStripper instance = new TextStripper();
        float expResult = 0.0F;
        float result = instance.getMinXWidth();
        assertEquals(result, expResult, 0.0);
    }

    /**
     * Test of getMaxYHeight method, of class TextStripper.
     */
    @Test
    public void testGetMaxYHeight() throws IOException {
        System.out.println("getMaxYHeight");
        TextStripper instance = new TextStripper();
        float expResult = 0.0F;
        float result = instance.getMaxYHeight();
        assertEquals(result, expResult, 0.0);
    }

    /**
     * Test of getMinYHeight method, of class TextStripper.
     */
    @Test
    public void testGetMinYHeight() throws IOException {
        System.out.println("getMinYHeight");
        TextStripper instance = new TextStripper();
        float expResult = 0.0F;
        float result = instance.getMinYHeight();
        assertEquals(result, expResult, 0.0);
    }

    /**
     * Test of getMaxXWidth method, of class TextStripper.
     */
    @Test
    public void testGetMaxXWidth() throws IOException {
        System.out.println("getMaxXWidth");
        TextStripper instance = new TextStripper();
        float expResult = 0.0F;
        float result = instance.getMaxXWidth();
        assertEquals(result, expResult, 0.0);
    }

    /**
     * Test of processTextPosition method, of class TextStripper.
     */
    @Test
    public void testProcessTextPosition() throws IOException {
        System.out.println("processTextPosition");
        TextStripper instance = new TextStripper();
        List<TextPosition> characters = instance.getCharacters();
        characters.stream().forEach((character) -> {
            instance.processTextPosition(character);
        });
    }
    
}
