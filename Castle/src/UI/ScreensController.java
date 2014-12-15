/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Castle.PDF;
import Castle.QuestionPrompter;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/**
 * The main controller that allows for multiple screens in the program
 * 
 * @author Alex
 */
public class ScreensController extends StackPane {
    
    private HashMap<String, Node> screens = new HashMap<>();
    PDF pdf;
    QuestionPrompter prompter;
    String filename = "";
    
    /**
     * empty constructor 
     */
    public ScreensController() {
        super();
    }
    
    /**
     * Adds a screen to the stack pane
     * @param name
     * @param screen 
     */
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }
    
    /**
     * Returns the screen stored under the given name
     * @param name
     * @return 
     */
    public Node getScreen(String name) {
        return screens.get(name);
    }
    
    /**
     * Loads the corresponding resource and name into the stack pane to be
     * accessed
     * @param name
     * @param resource
     * @return 
     */
    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load(); 
            ControlledScreen myScreenController = ((ControlledScreen) myLoader.getController());
            myScreenController.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    /**
     * Sets the new screen based of screen name
     * @param name
     * @return 
     */
    public boolean setScreen(final String name) {
        if (screens.get(name) != null) {
            if (!getChildren().isEmpty()) {
                getChildren().remove(0);
            }
            getChildren().add(screens.get(name));
            return true;
        }
        System.out.println("No Screen Loaded");  
        return false;
    }
    
    /**
     * Remove screen from stack based on name
     * @param name
     * @return 
     */
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else { 
            return true;
        }
    }
    
    /**
     * allows for the transfer of data between screens
     * @param pdf
     * @param prompter 
     */
    public void setParams(PDF pdf, QuestionPrompter prompter) {
        this.pdf = pdf;
        this.prompter = prompter;
        prompter.setAnswers(pdf.getQuestionMap());
    }
}
