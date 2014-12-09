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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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

    private ScreensController myController;
    
    private PDF pdf;
    private QuestionPrompter prompter; 
    private String filename = "";
    
    private String selected = "";
    
    private final ToggleGroup radioButtons = new ToggleGroup();
    
    private final TextField textFieldAnswer = new TextField();

    private final TextArea textAreaAnswer = new TextArea();
    
    private final List<CheckBox> checkboxArray = new ArrayList<>();

 
    private final VBox buttonBox = new VBox();
    
    @FXML
    private Pane answerPane;
    
    @FXML 
    private Label questionPromptLabel;
    
    @FXML
    private ListView<Question> questionListView;
    private final ObservableList<Question> questions = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class. 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Handle ListView selection changes.
        questionListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newQuestion) -> {
           questionPromptLabel.setText(newQuestion.getPrompt());
           if (oldValue != (null)) {
               save(oldValue);
           }
            switch (newQuestion.getType()) {
                case "TextField":
                    formatTextField(newQuestion);
                    break;
                case "Radio":
                    formatRadioButton((RadioQuestion)newQuestion);
                    break;
                case "TextArea":
                    formatTextArea(newQuestion);
                    break;
                case "CheckBox":
                    formatCheckBox((CheckBoxQuestion)newQuestion);
                    break;
            }
        });
    }    
    
    /**
     * 
     * @param question 
     */
    public void formatTextArea(Question question) {
        List<String> answer = prompter.getAnswers().get(question);
        answerPane.getChildren().clear();
        textAreaAnswer.setMinSize(300, 200);
        textAreaAnswer.setLayoutX(13);
        textAreaAnswer.setLayoutY(38);
        textAreaAnswer.setMaxSize(374, 218);
        if (!answer.isEmpty()) {
            textAreaAnswer.setText(answer.get(0));
        } else { 
            textAreaAnswer.clear();
        }
        answerPane.getChildren().add(textAreaAnswer);
    }
    
    /**
     * 
     * @param question
     */
    public void formatRadioButton(RadioQuestion question) {
        List<String> listAnswer = prompter.getAnswers().get(question);
        String currentAnswer = "";
        buttonBox.setLayoutX(150);
        buttonBox.setLayoutY(66);
        answerPane.getChildren().clear();
        if (!listAnswer.isEmpty()) {
            currentAnswer = listAnswer.get(0);
        } else {
            buttonBox.getChildren().clear();
        }
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
        
        
        List<String> possibleAnswers = question.getPosAnswers();
        List<String> selectedAnswers = prompter.getAnswers().get(question);
        
        checkboxArray.clear();
        for (String answer : possibleAnswers) {
            
            CheckBox current = new CheckBox(answer);
            checkboxArray.add(current);
            if (selectedAnswers.contains(answer)) {
                current.setSelected(true);
            }
            else {
                current.setSelected(false);
            }
            buttonBox.getChildren().add(current);
        }
        
        answerPane.getChildren().add(buttonBox);
    }
    
    /**
     * 
     * @param question
     */
    public void formatTextField(Question question) {
        List<String> answer = prompter.getAnswers().get(question);
        textFieldAnswer.setLayoutX(116);
        textFieldAnswer.setLayoutY(116);
        answerPane.getChildren().clear();
        if (!answer.isEmpty()) {
            textFieldAnswer.setText(answer.get(0));
        }
        else {
            textFieldAnswer.clear();
        }
        answerPane.getChildren().add(textFieldAnswer);
    }

     /**
     * 
     */
    @FXML
    public void goBack() {
        pdf.setAnswers(prompter.getAnswers());
        myController.setScreen(MainUI.mainPage);
    }
    
    @FXML
    public void save(Question question) {
        selected = question.getType();
        switch (selected) {
            case "TextArea":
                getTextAreaAnswer(question);
                break;
            case "TextField":
                getTextFieldAnswer(question);
                break;
            case "Radio":
                getRadioAnswer(question);
                break;
            case "CheckBox":
                getCheckBoxAnswer(question);
                break;
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
        List<String> answerList = new ArrayList<>();
        answerList.add(answer);
        prompter.addAnswer(question, answerList);
    }

    public void getRadioAnswer(Question question) {
        String answer;
        if (radioButtons.getSelectedToggle() != null) {
            answer = radioButtons.getSelectedToggle().getUserData().toString();
        } else {
            answer = null;
        }
        List<String> answerList = new ArrayList<>();
        answerList.add(answer);
        prompter.addAnswer(question, answerList);
    }
    
    public void getCheckBoxAnswer(Question question) {
        String answer;
        List<String> answerList = new ArrayList();
        for (CheckBox checkbox : checkboxArray) {
            if (checkbox.isSelected()) {
                answer = checkbox.getText();
                answerList.add(answer);
            }
        }
        prompter.addAnswer(question, answerList);
        
    }
    
    /**
     * 
     * @param screenParent 
     */
    @Override
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
