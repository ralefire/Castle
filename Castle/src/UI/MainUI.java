/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Castle.PDF;
import Castle.QuestionPrompter;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class that will launch the program
 * @author Alex
 */
public class MainUI extends Application {
    PDF pdf = new PDF();
    QuestionPrompter prompter = new QuestionPrompter();
    
    public static String mainPage = "main";
    public static String mainFXML = "MainPage.fxml";
    public static String prompterPage = "prompter";
    public static String prompterFXML = "BuilderPrompt.fxml";
    public static String builderPage = "builder";
    public static String builderFXML = "QuestionBuilder.fxml";
    
    /**
     * loads the main stage and show to to begin the program
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        ScreensController mainContainer = new ScreensController();
        
        mainContainer.setParams(pdf, prompter);
        mainContainer.loadScreen(mainPage, mainFXML);
        
        mainContainer.setScreen(mainPage);
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
