/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Castle.PDF;
import Castle.Question;
import Castle.QuestionPrompter;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * This is the controller for the main page the user will see upon loading
 * the program
 * @author Alex
 */
public class MainPageController implements Initializable, ControlledScreen {
    ScreensController myController;
    
    PDF pdf;
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
    private Button clearQuestions;
    
    /**
     * Close the program upon a button push
     */
    @FXML
    private void quitPress(){
        System.exit(0);
    }
    
    /**
     * Open the correct screen if the questions are loaded or not
     */
    @FXML
    private void startPress(){
        if (!pdf.getQuestionsLoaded()) {
            myController.loadScreen(MainUI.builderPage, MainUI.builderFXML);
            myController.setScreen(MainUI.builderPage);
        } else {
            myController.loadScreen(MainUI.prompterPage, MainUI.prompterFXML);
            myController.setScreen(MainUI.prompterPage);
        }
    }
    
    /**
     * Save the current status of the question and answers into json objects and
     * save them to the pdf
     */
    @FXML
    private void savePress(){
        JSONArray jsonArray = new JSONArray();

        for (Question question : pdf.getQuestionMap().keySet()) {
            JSONObject json = new JSONObject();
            json.put("prompt", question.getPrompt());
            json.put("hash", question.getHash());
            json.put("type", question.getType());
            
            // extra logic for more complicated cases
            if (question.getType().equals("CheckBox") ||
                question.getType().equals("Radio")) {
                String completePosAnswer = "";
                
                // save the possible answers in proper string format
                for (String posAnswer : question.getPosAnswers()) {
                    completePosAnswer += posAnswer + " !@ ";
                }
                json.put("posAnswer", completePosAnswer);

            }
            jsonArray.add(json);
        }
        
        // save the array
        pdf.setKeywords(jsonArray);
        try {
            pdf.save(filename);
        } catch (Exception e) {
            showWarning("PDF not saved;");
        }
        showWarning("Questions saved to PDF");
    }

    /**
     * This will select the file to say to and then call the pdf's build function
     * to export the updated pdf
     */
    @FXML
    private void exportPDFPress(){
        FileChooser chooser = new FileChooser();
        File file = chooser.showSaveDialog(new Stage());
        
        if(file == null) {
                showWarning("No file selected or created");
                filename = "";
        } else {
            try {
                if (!file.exists()) {
                    file.createNewFile();
                    }
                }
            catch (Exception e) {
                ;
            }
            filename = file.getPath();
            try {
                pdf.buildPDF(filename);
            } catch (Exception e) {
                System.out.println("Export PDF FAIL");
            }
        }
    }
    
    /**
     * This will call the question builder to allow question editing
     */
    @FXML
    private void editPress(){
        myController.loadScreen(MainUI.builderPage, MainUI.builderFXML);
        myController.setScreen(MainUI.builderPage);
    }
    
    /**
     * This will remove the saved metadata to allow for a fresh
     * start upon loading
     */
    @FXML
    public void resetMetaData() {
        JSONArray nullJSON = new JSONArray();
        JSONObject clear = new JSONObject();
        clear.put("@!clear!@", "");
        nullJSON.add(clear);
        pdf.setKeywords(nullJSON);
        try {
        pdf.save(filename);
        } catch (Exception e) {
            showWarning("Unable to clear questions");
        }
    }
    
    /**
     * This will select a file and call PDF's load file function
     */
    @FXML
    private void loadPress() {
        System.out.println("Loading...");
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(new Stage());
        if (file == null) {
            showWarning("No file selected or created");
            filename = "";
            return;
        } else {

            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (Exception e) {
                ;
            }

            filename = file.getPath();
        }

        try {
        pdf.load(filename);
        } catch (Exception e) {
            ;
        }
        
        // allow buttons if PDF was loaded properly
        pdf.loadQuestions();
        startButton.setDisable(false);
        saveButton.setDisable(false);
        saveAsButton.setDisable(false);
        editButton.setDisable(false);
        clearQuestions.setDisable(false);
    }
    
    /**
     * empty function called when scene loaded
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    /**
     * Set parent screen and set values appropriately
     * @param screenParent 
     */
    public void setScreenParent(ScreensController  screenParent) {
        myController = screenParent;
        this.pdf = myController.pdf;
        this.prompter = myController.prompter;
        this.filename = myController.filename;
    }
    
    /**
     * Popup dialog box that display the string passed in
     * @param warning 
     */
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
