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
 *
 * @author Alex
 */
public class ScreensController extends StackPane {
    
    private HashMap<String, Node> screens = new HashMap<>();
    PDF pdf;
    QuestionPrompter prompter;
    String filename = "";
    
    
    public ScreensController() {
        super();
    }
    
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }
    
    public Node getScreen(String name) {
        return screens.get(name);
    }
    
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
    
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else { 
            return true;
        }
    }
    
    public void setParams(PDF pdf, QuestionPrompter prompter) {
        this.pdf = pdf;
        this.prompter = prompter;
    }
}
