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
 * user interface for the answering of the questions
 * @author Alex
 */
public class BuilderPromptController implements Initializable, ControlledScreen {

    private ScreensController myController;
    
    PDF pdf;
    QuestionPrompter prompter; 
    
    private String selected = "";
    
    private final ToggleGroup radioButtons = new ToggleGroup();
    
    private final TextField textFieldAnswer = new TextField();

    private final TextArea textAreaAnswer = new TextArea();
    
    List<CheckBox> checkboxArray = new ArrayList<CheckBox>();
 
    private final VBox buttonBox = new VBox();
    
    @FXML
    private Pane answerPane;
    
    @FXML 
    private Label questionPromptLabel;
    
    @FXML
    private ListView<Question> questionListView;
    private final ObservableList<Question> questions = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class and handles saving when 
     * new selections are made in the list view
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
           if (newQuestion.getType().equals("TextField")) {
               formatTextField(newQuestion);
           }
           
           else if (newQuestion.getType().equals("Radio")) {
               formatRadioButton((Question)newQuestion);
           }
           
           else if (newQuestion.getType().equals("TextArea")) {
               formatTextArea(newQuestion);
           }
           else if (newQuestion.getType().equals("CheckBox")) {
               formatCheckBox((Question)newQuestion);
           }
        });
    }    
    
    /**
     * This will prepare the text area for display
     * @param question 
     */
    public void formatTextArea(Question question) {
        String answer = prompter.getAnswers().get(question);
        answerPane.getChildren().clear();
        textAreaAnswer.setWrapText(true);
        textAreaAnswer.setMinSize(300, 200);
        textAreaAnswer.setLayoutX(13);
        textAreaAnswer.setLayoutY(38);
        textAreaAnswer.setMaxSize(374, 218);
        if (!answer.isEmpty()) {
            textAreaAnswer.setText(answer);
        } else { 
            textAreaAnswer.clear();
        }
        answerPane.getChildren().add(textAreaAnswer);
    }
    
    /**
     * select the correct radio button depending on the users answer to in the 
     * question select none if need be
     * @param question
     */
    public void formatRadioButton(Question question) {
        String listAnswer = prompter.getAnswers().get(question);
        String currentAnswer = "";
        buttonBox.setLayoutX(150);
        buttonBox.setLayoutY(66);
        answerPane.getChildren().clear();
        if (!listAnswer.isEmpty()) {
            currentAnswer = listAnswer;
        } 
        
        // remove the current button box
        buttonBox.getChildren().clear();
        
        // find correct button and set it.
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
     * This will format the check box display depending on the answers already
     * selected by the user
     */
    private void formatCheckBox(Question question) {
        buttonBox.setLayoutX(150);
        buttonBox.setLayoutY(66);
        answerPane.getChildren().clear();
       
        // remove all existing buttons
        buttonBox.getChildren().clear();
        
        // get all possibles
        List<String> possibleAnswers = question.getPosAnswers();
        String selectedAnswers = prompter.getAnswers().get(question);
        
        // empty the array
        checkboxArray.clear();
        
        // loop through possible answers
        for (String answer : possibleAnswers) {
            
            CheckBox current = new CheckBox(answer);
            checkboxArray.add(current);
            
            // select the possible answers found in the answer string
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
     * This will format the text field and fill it with answer if the question
     * already has one saved.
     * @param question
     */
    public void formatTextField(Question question) {
        String answer = prompter.getAnswers().get(question);
        textFieldAnswer.setLayoutX(116);
        textFieldAnswer.setLayoutY(116);
        answerPane.getChildren().clear();
        if (!answer.isEmpty()) {
            textFieldAnswer.setText(answer);
        }
        else {
            textFieldAnswer.clear();
        }
        answerPane.getChildren().add(textFieldAnswer);
    }

     /**
     * simple function to revert to home page and save the current answer
     */
    @FXML
    public void goBack() {
        if (!questionListView.getSelectionModel().isEmpty()) {
            save(questionListView.getSelectionModel().getSelectedItem());
        }
        myController.setScreen(MainUI.mainPage);
    }
    
    /**
     * Will decide which save function to call depending
     * on the type of the question passed in to the function
     * @param question 
     */
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
    /**
     * gets the answer from the text area and saves it to the question
     * @param question 
     */
    public void getTextAreaAnswer(Question question) {
        String answer = textAreaAnswer.getText();
        prompter.addAnswer(question, answer);
    }
    
    /**
     * gets the answer from a text field and saves it to the question
     * @param question 
     */
    public void getTextFieldAnswer(Question question) {
        String answer = textFieldAnswer.getText();
        prompter.addAnswer(question, answer);
    }

    
    /**
     * Set the users selection to the radio question as the answer
     * @param question 
     */
    public void getRadioAnswer(Question question) {
        String answer;
        if (radioButtons.getSelectedToggle() != null) {
            answer = radioButtons.getSelectedToggle().getUserData().toString();
        } else {
            answer = "";
        }
        prompter.addAnswer(question, answer);
    }
    
    /**
     * This will extract the answers selected form the answer boxes and 
     * save them to the answer string of the question
     * @param question 
     */
    public void getCheckBoxAnswer(Question question) {
        String answer;
        String answerString = "";
        
        // get the number of answers selected
        int numAnswers = 0;
        for (CheckBox checkbox : checkboxArray)  {
            if (checkbox.isSelected()) {
                numAnswers++;
            }
        }
        
        // loop through and get string values of selected items
        int currentNumAnswers = 1;
        for (CheckBox checkbox : checkboxArray) {
            if (checkbox.isSelected()) {
                if (numAnswers == 1) {
                    // single answer form
                    answerString += checkbox.getText();
                    
                // formats string format for only two answers
                } else if (numAnswers == 2) {
                    if (currentNumAnswers == 1) {
                        answerString += checkbox.getText();
                        answerString += " and ";
                        currentNumAnswers++;
                    } else {
                        answerString += checkbox.getText();
                    }
                // formats string to three or more
                } else {
                    if (currentNumAnswers + 1 == numAnswers) {
                        answerString += checkbox.getText();
                        answerString += " and ";
                        currentNumAnswers++;
                    } else if (currentNumAnswers == numAnswers) {
                        answerString += checkbox.getText();
                        currentNumAnswers++;
                    } else {
                        answerString += checkbox.getText();
                        answerString += ", ";
                        currentNumAnswers++;
                    }
                }
            }
        }
        // adds answer to map
        prompter.addAnswer(question, answerString);
        
        
    }
    
    /**
     * Set this screen as parent and passes values of pdf and prompter
     * so they can be accessed. This function occurs on load of page
     * @param screenParent 
     */
    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
        this.pdf = myController.pdf;
        this.prompter = myController.prompter;
        setQuestions();
    }
    
    /**
     * Function passes the values from the prompters map 
     * into the map used by the controller
     */
    private void setQuestions() {
        Set<Question> questionSet = prompter.getAnswers().keySet();
        
        for (Question question : questionSet) {
            questions.add(question);
        }
        questionListView.setItems(questions);
    }
    
    /**
     * This function will create a dialog box that will display the warning 
     * that is passed into the function
     * @param warning 
     */
    private void showWarning(String warning) {
        // establish stage setup
        Stage popup = new Stage();
        VBox headsUp = new VBox();
        Text prompt = new Text(warning);
        prompt.setStyle("-fx-font-size: 11pt;");
        
        // adds children and displays
        headsUp.getChildren().add(prompt);
        headsUp.setAlignment(Pos.CENTER);
        popup.setScene(new Scene(headsUp, 300, 200));
        popup.setTitle("Warning");
        popup.show(); 
    }
}
