/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Castle.CheckBoxQuestion;
import Castle.PDF;
import Castle.Question;
import Castle.QuestionPrompter;
import Castle.RadioQuestion;
import Castle.TextQuestion;
import java.awt.CheckboxGroup;
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
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


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
    
    String selected = "";
    
    ToggleGroup radioButtons = new ToggleGroup();
    
    TextField textFieldAnswer = new TextField();

    TextArea textAreaAnswer = new TextArea();
    
    CheckBox[] checkbox = null;

 
    VBox buttonBox = new VBox();
    
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
               formatTextField(newQuestion);
           }
           
           else if (newQuestion.getType().equals("Radio")) {
               formatRadioButton((RadioQuestion)newQuestion);
           }
           
           else if (newQuestion.getType().equals("TextArea")) {
               formatTextArea(newQuestion);
           }
           else if (newQuestion.getType().equals("CheckBox")) {
               formatCheckBox((CheckBoxQuestion)newQuestion);
           }
        });
    }    
    
    /**
     * 
     * @param question 
     */
    public void formatTextArea(Question question) {
        String answer = prompter.getAnswers().get(question);
        answerPane.getChildren().clear();
        textAreaAnswer.setMinSize(300, 200);
        textAreaAnswer.setLayoutX(13);
        textAreaAnswer.setLayoutY(38);
        textAreaAnswer.setMaxSize(374, 218);
        setTextFieldValue(answer);
         answerPane.getChildren().add(textAreaAnswer);
    }
    
    /**
     * 
     */
    public void formatRadioButton(RadioQuestion question) {
        String currentAnswer = prompter.getAnswers().get(question);
        buttonBox.setLayoutX(150);
        buttonBox.setLayoutY(66);
        answerPane.getChildren().clear();
        
        buttonBox.getChildren().clear();
        for (String posAnswer : question.getPosAnswers()) {
            RadioButton button = new RadioButton(posAnswer);
            button.setUserData(posAnswer);
            if (posAnswer.equals(currentAnswer)) {
                button.setSelected(true);
            }
            button.setToggleGroup(radioButtons);
            buttonBox.getChildren().add(button);
        }

        answerPane.getChildren().add(buttonBox);
    }
    
    /**
     * 
     */
    private void formatCheckBox(CheckBoxQuestion question) {
        buttonBox.setLayoutX(150);
        buttonBox.setLayoutY(66);
        answerPane.getChildren().clear();
        
       
        buttonBox.getChildren().clear();
        
        
        if (checkbox != null) {
            checkbox = null;
        }
        
        List<String> possibleAnswers = question.getPosAnswers();
        
        checkbox = new CheckBox[possibleAnswers.size()];
        
        int i = 0;
        for (String answer : possibleAnswers) {
            CheckBox current = checkbox[i] = new CheckBox(answer);
            buttonBox.getChildren().add(current);
        }
        
        answerPane.getChildren().add(buttonBox);
    }
    
    /**
     * 
     */
    public void formatTextField(Question question) {
        String answer = prompter.getAnswers().get(question);
        textFieldAnswer.setLayoutX(116);
        textFieldAnswer.setLayoutY(116);
        answerPane.getChildren().clear();
        
        setTextFieldValue(answer);
         answerPane.getChildren().add(textFieldAnswer);
    }

    private void setTextFieldValue(String answer) {
        if (answer.equals("")) {
        }
        else {
            textAreaAnswer.setText(answer);
        }
    }
    
    /**
     * 
     */
    @FXML
    public void goBack() {
        myController.setScreen(MainUI.mainPage);
    }
    
    @FXML
    public void save() {
        selected = questionListView.getSelectionModel().getSelectedItem().getType();
        Question question = questionListView.getSelectionModel().getSelectedItem();
        if (selected.equals("TextArea")) {
            getTextAreaAnswer(question);
        } else if (selected.equals("TextField")) {
            getTextFieldAnswer(question);
        } else if (selected.equals("Radio")) {
            getRadioAnswer(question);
        } else if (selected.equals("CheckBox")) {
            getCheckBoxAnswer(question);
        }
        
    }
    
    public void getTextAreaAnswer(Question question) {
        String answer = textAreaAnswer.getText();
       
        saveTextAnswer(answer, question);
    }
    
    public void getTextFieldAnswer(Question question) {
        String answer = textFieldAnswer.getText();
        
        saveTextAnswer(answer, question);
        
    }

    private void saveTextAnswer(String answer, Question question) {
        if (answer.equals("")) {
            showWarning("Please give answer before saving");
        }
        else {
            prompter.addAnswer(question, answer);
            System.out.println(prompter.getAnswers().get(question));
        }
    }
    
    public void getRadioAnswer(Question question) {
        if (radioButtons.getSelectedToggle() != null) {
            String answer = radioButtons.getSelectedToggle().getUserData().toString();
            prompter.addAnswer(question, answer);
        } else {
            showWarning("No answer selected to save");
        }
    }
    
    public void getCheckBoxAnswer(Question question) {
        
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
