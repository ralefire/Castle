/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Castle.PDF;
import Castle.Question;
import Castle.QuestionPrompter;
import Castle.TextQuestion;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


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
    
    ToggleGroup radioButtons = new ToggleGroup();
    
    TextField textFieldAnswer = new TextField();

    TextArea textAreaAnswer = new TextArea();

 
    HBox buttonBox = new HBox();
    
    @FXML
    Pane answerPane;
    
    @FXML 
    Label questionPromptLabel;
    
    @FXML
    ListView<Question> questionListView;
    ObservableList<Question> questions = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Handle ListView selection changes.
        questionListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newQuestion) -> {
           questionPromptLabel.setText(newQuestion.getPrompt());
           if (newQuestion.getType().equals("TextField")) {
               formatTextField();
           }
           
           else if (newQuestion.getType().equals("Radio")) {
               formatRadioButton();
           }
           
           else if (newQuestion.getType().equals("TextArea")) {
               formatTextArea(newQuestion);
           }
        });
    }    
    
    /**
     * 
     * @param question 
     */
    public void formatTextArea(Question question) {
       answerPane.getChildren().clear();
       textAreaAnswer.setMinSize(300, 200);
       textAreaAnswer.setLayoutX(13);
       textAreaAnswer.setLayoutY(38);
       textAreaAnswer.setMaxSize(374, 218);
       answerPane.getChildren().add(textAreaAnswer);
    }
    
    /**
     * 
     */
    public void formatRadioButton() {
        buttonBox.setLayoutX(116);
        buttonBox.setLayoutY(116);
        answerPane.getChildren().clear();
        
        List<String> answers = new ArrayList<>();
        answers.add("<1000");
        answers.add("1000-2500");
        answers.add("2500<");
               
        buttonBox.getChildren().clear();
        for (String answer : answers) {
            RadioButton button = new RadioButton(answer);
            button.setToggleGroup(radioButtons);
            buttonBox.getChildren().add(button);
        }

        answerPane.getChildren().add(buttonBox);
    }
    
    /**
     * 
     */
    public void formatTextField() {
        textFieldAnswer.setLayoutX(116);
        textFieldAnswer.setLayoutY(116);
        answerPane.getChildren().clear();
        answerPane.getChildren().add(textFieldAnswer);
    }
    
    /**
     * 
     */
    @FXML
    public void goBack() {
        myController.setScreen(MainUI.mainPage);
    }
    
    /**
     * 
     * @param screenParent 
     */
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
        this.pdf = myController.pdf;
        this.prompter = myController.prompter;
        this.filename = myController.filename;
        setQuestions();
    }
    
    /**
     * 
     */
    private void setQuestions() {
        Set<Question> questionSet = prompter.getAnswers().keySet();
        
        for (Question question : questionSet) {
            questions.add(question);
            /*String hash = question.getHash();
            questions.add(hash);*/
        }
        questionListView.setItems(questions);
    }
}
