/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Castle;

import UI.MainUI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
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
public class CastleAppNGTest {
    
    public CastleAppNGTest() {
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
     * Test of start method, of class CastleApp.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        Stage primaryStage = null;
        MainUI instance = new MainUI();
        try {
            instance.start(primaryStage);
        } catch (Exception ex) {
            Logger.getLogger(CastleAppNGTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class CastleApp.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        MainUI.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of run method, of class CastleApp.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        MainUI instance = new MainUI();
        //instance.start();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
