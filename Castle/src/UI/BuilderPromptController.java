/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Castle.PDF;
import Castle.Question;
import Castle.QuestionPrompter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;


/**
 * FXML Controller class
 *
 * @author Alex
 */
public class BuilderPromptController implements Initializable, ControlledScreen {

    ScreensController myController;
    
    PDF pdf;
    QuestionPrompter prompter; 
    String filename = "";
    
    @FXML
    ListView<String> questionListView;
    ObservableList<String> questions = FXCollections.observableArrayList();
    
    
    /**
     * Initializes the controller class. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void goBack() {
        myController.setScreen(MainUI.mainPage);
    }
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
        this.pdf = myController.pdf;
        this.prompter = myController.prompter;
        this.filename = myController.filename;
        setQuestions();
    }
    
    private void setQuestions() {
        System.out.println("ENTERED");
        Set<Question> questionSet = prompter.getAnswers().keySet();
        
        for (Question question : questionSet) {
            System.out.println("HERE: " + question.getPrompt());
            questions.add(question.getPrompt());
        }
        questionListView.setItems(questions);
    }
}
