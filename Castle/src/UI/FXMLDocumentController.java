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
 *
 * @author Alex
 */
public class FXMLDocumentController implements Initializable, ControlledScreen {
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
    private void quitPress(){
        System.exit(0);
    }
    
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
    
    @FXML
    private void savePress(){
        JSONArray jsonArray = new JSONArray();
        for (Question question : pdf.getQuestionMap().keySet()) {
            JSONObject json = new JSONObject();
            json.put("prompt", question.getPrompt());
            json.put("hash", question.getHash());
            json.put("type", question.getType());
            if (question.getType().equals("CheckBox") ||
                question.getType().equals("Radio")) {
                String completePosAnswer = "";
                for (String posAnswer : question.getPosAnswers()) {
                    completePosAnswer += posAnswer + " !@ ";
                }
                json.put("posAnswer", completePosAnswer);

            }
            jsonArray.add(json);
        }
        pdf.setKeywords(jsonArray);
        
        showWarning("Questions saved to PDF");
    }

    
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
                pdf.save(filename);
            } catch (Exception e) {
                System.out.println("Export PDF FAIL");
            }
        }
    }
    
    @FXML
    private void editPress(){
        myController.loadScreen(MainUI.builderPage, MainUI.builderFXML);
        myController.setScreen(MainUI.builderPage);
    }
    
    
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
        pdf.loadQuestions();
        startButton.setDisable(false);
        saveButton.setDisable(false);
        saveAsButton.setDisable(false);
        editButton.setDisable(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void setScreenParent(ScreensController  screenParent) {
        myController = screenParent;
        this.pdf = myController.pdf;
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
