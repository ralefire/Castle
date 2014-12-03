/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Castle.PDF;
import Castle.QuestionBuilder;
import Castle.QuestionPrompter;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Alex
 */
public class FXMLDocumentController implements Initializable, ControlledScreen {
    ScreensController myController;
    
    PDF pdf;
    QuestionBuilder builder; 
    QuestionPrompter prompter; 
    String filename = "";

    @FXML
    private Button loadButton;

    @FXML
    private Button startButton;
    
    @FXML
    private Button editButton;
    
    @FXML
    private Button saveAsButton;
    
    @FXML
    private Button saveButton;
    
    @FXML
    private Button quitButton;
    
    
    @FXML
    private void quitPress(){
        System.exit(0);
    }
    
    @FXML
    private void startPress(){
        if (builder.isBuilt()) {
            prompter.run();
        }
        else {
            showWarning("Please complete question builder first");
            builder.run();
            prompter.run();
            myController.setScreen(MainUI.secondPage);
        }
    }
    
    @FXML
    private void savePress(){
        
        if (filename == "") {
            FileChooser chooser = new FileChooser();
            File file = chooser.showSaveDialog(new Stage());
           try {
                if (!file.exists()) {
                    file.createNewFile();
                    }
                }
                catch (Exception e) {
                    ;
                }

                filename = file.getPath();
        }
        pdf.save(filename);
    }
    
    @FXML
    private void saveAsPress(){
        FileChooser chooser = new FileChooser();
        File file = chooser.showSaveDialog(new Stage());
        try {
            if (!file.exists()) {
                file.createNewFile();
                }
            }
        catch (Exception e) {
            ;
        }
        filename = file.getPath();
        pdf.save(filename);
    }
    
    @FXML
    private void editPress(){
        builder.run();
    }
    
    
    @FXML
    private void loadPress() {
        System.out.println("Loading...");
        startButton.setDisable(false);
        saveButton.setDisable(false);
        saveAsButton.setDisable(false);
        editButton.setDisable(false);
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("RUN!!");
    }    
    
    public void setScreenParent(ScreensController  screenParent) {
        myController = screenParent;
        this.pdf = myController.pdf;
        this.builder = myController.builder;
        this.prompter = myController.prompter;
        this.filename = myController.filename;
    }
    
    private void showWarning(String warning) {
        Stage popup = new Stage();
        VBox headsUp = new VBox();
        Text prompt = new Text(warning);
        prompt.setStyle("-fx-font-size: 11pt;");
        headsUp.getChildren().add(prompt);
        headsUp.setAlignment(Pos.CENTER);
        popup.setScene(new Scene(headsUp, 300, 200));
        popup.setTitle("Warning");
        popup.show(); 
    }
}
