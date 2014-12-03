/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Castle.PDF;
import Castle.QuestionBuilder;
import Castle.QuestionPrompter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Alex
 */
public class MainUI extends Application {
    PDF pdf = new PDF();
    QuestionBuilder builder = new QuestionBuilder();
    QuestionPrompter prompter = new QuestionPrompter();
    
    public static String mainPage = "main";
    public static String mainFXML = "FXMLDocument.fxml";
    public static String secondPage = "second";
    public static String secondFXML = "BuilderPrompt.fxml";
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        ScreensController mainContainer = new ScreensController();
        
        mainContainer.setParams(pdf, builder, prompter);
        mainContainer.loadScreen(mainPage, mainFXML);
        mainContainer.loadScreen(secondPage, secondFXML);
        
        mainContainer.setScreen(mainPage);
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        /*
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();*/
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
