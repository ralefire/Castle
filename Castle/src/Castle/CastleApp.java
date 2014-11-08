/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Castle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Xandron
 */
public class CastleApp extends Application {    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    public CastleApp(){}
    
    /**
     * This runs the program.
     */
    public void run(){}
    /**
     * This loads a file into the app.
     */
    private void load(){}
    /**
     * Quits the program.
     */
    private void quit(){}
    /**
     * Saves the template to the same file loaded from.
     */
    private void save(){}
    /**
     * Prompts the user where to save the updated template and saves file there.
     */
    private void saveAs(){}
    /**
     * Starts the main sequence of events.
     */
    private void startQuestionare(){}
    /**
     * Edits the PDF by rebuilding the Questions.
     */
    private void edit(){}
}