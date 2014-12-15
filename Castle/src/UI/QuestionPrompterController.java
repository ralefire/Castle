/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Castle.PDF;
import Castle.Question;
import java.awt.Panel;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Question builder class that will allow the user to set questions for the 
 * hashes parsed out of the PDF document
 *
 * @author Alex
 */
public class QuestionPrompterController implements Initializable, ControlledScreen {

    ScreensController myController;
    
    PDF pdf;
    
    ToggleGroup typeButtons = new ToggleGroup();
    
    RadioButton textFieldButton;

    RadioButton textAreaButton;

    RadioButton radioButton;

    RadioButton checkBoxButton;
    
    List<TextField> posAnswerList = new ArrayList<>();
    
    TextField field1 = new TextField();
    TextField field2 = new TextField();
    TextField field3 = new TextField();
    TextField field4 = new TextField();
    TextField field5 = new TextField();
    
    Label optionsLabel = new Label();

    @FXML
    TextArea questionArea = new TextArea();
    
    @FXML
    VBox questionBox = new VBox();
    
    VBox rightBox = new VBox();
    
    @FXML
    HBox posAnswer = new HBox();
    
    @FXML 
    Panel displayPanel = new Panel();
    
    @FXML
    Label hashNameLabel;
    
    ObservableList<String> numberPosAnswers = FXCollections.observableArrayList("1", "2", "3", "4", "5");
    ComboBox numberPosAnswerList = new ComboBox(numberPosAnswers);
    
    @FXML
    ListView<Question> questionListView;
    ObservableList<Question> questions = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     * Prepares the on change functions for the selected question, 
     * questions type, and save and load
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // handle switching between questions in the list view
        questionListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newQuestion) -> {
            hashNameLabel.setText(newQuestion.getHash());
            if (oldValue != (null)) {
                save(oldValue);
            }
            clearPosAnswers();
            numberPosAnswerList.setValue("");
            rightBox.getChildren().clear();
            load(newQuestion);
        });

        // changing the question type upon click
        typeButtons.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            Question selectedQuestion
                    = (Question) questionListView.getSelectionModel().getSelectedItem();
            if (oldValue != null) {
                if (oldValue.getUserData().toString().equals("Radio")
                    || oldValue.getUserData().toString().equals("CheckBox")) {
                List<String> posAnswerGiven = new ArrayList<>();
                for (TextField posAnswersFilled : posAnswerList) {
                    if (!posAnswersFilled.getText().equals("")) {
                        posAnswerGiven.add(posAnswersFilled.getText());
                    }
                }
                // reposition right box 
                selectedQuestion.setPosAnswers(posAnswerGiven);
                posAnswer.getChildren().remove(numberPosAnswerList);
                posAnswer.getChildren().remove(rightBox);
                }
            }
            
            // error avoiding
            if (newValue == null) {
                if (posAnswer.getChildren().contains(numberPosAnswerList)) {
                    posAnswer.getChildren().remove(numberPosAnswerList);
                } 
                if (posAnswer.getChildren().contains(numberPosAnswerList)) {
                    posAnswer.getChildren().remove(numberPosAnswerList);
                }
                
            // reformat for radio and check box questions
            } else if (newValue.getUserData().toString().equals("Radio")
                    || newValue.getUserData().toString().equals("CheckBox")) {
                
                // Ensure right box is positioned correctly
                if (selectedQuestion.getPosAnswers().size() > 0) {
                    numberPosAnswerList.setValue(Integer.toString(selectedQuestion.getPosAnswers().size()));
                    if (!posAnswer.getChildren().contains(numberPosAnswerList)) {
                        posAnswer.getChildren().add(numberPosAnswerList);
                        if (posAnswer.getChildren().contains(rightBox)) {
                            posAnswer.getChildren().remove(rightBox);
                        }
                        posAnswer.getChildren().add(rightBox);
                    }
                } else {
                    selectedQuestion.setType(newValue.getUserData().toString());
                    numberPosAnswerList.setValue("");
                    if (posAnswer.getChildren().contains(rightBox)) {
                        posAnswer.getChildren().remove(rightBox);
                    }
                    if (posAnswer.getChildren().contains(numberPosAnswerList)) {
                        posAnswer.getChildren().remove(numberPosAnswerList);
                    }
                    posAnswer.getChildren().add(numberPosAnswerList);
                }
            } 
        });

        // if the number of possible answers changes alter the view
        numberPosAnswerList.getSelectionModel().selectedItemProperty().addListener((observable, oldIndex, newIndex) -> {
            populateRightBox();
        });
    }

    /**
     * Format right box to display clean formatting for each question
     */
    private void populateRightBox() {
        if (!rightBox.getChildren().isEmpty()) {
            rightBox.getChildren().clear();
        }
        rightBox.getChildren().add(optionsLabel);
        
        if (posAnswer.getChildren().contains(rightBox)) {
            posAnswer.getChildren().remove(rightBox);
        }
        if (!(numberPosAnswerList.getValue() == null)) {
            if (!numberPosAnswerList.getValue().equals("")) {
                int selectedNumber = Integer.parseInt((String) numberPosAnswerList.getSelectionModel().getSelectedItem());
                for (int i = 0; i < selectedNumber; i++) {
                    TextField toAdd = posAnswerList.get(i);
                    rightBox.getChildren().add(toAdd);
                }
            }
            posAnswer.getChildren().add(rightBox);
        }
    }

    /**
     * Prepare the entire screen for formatting and editting
     */
    public void setTypeDisplay() {
        textFieldButton = new RadioButton("Text Field");
        textAreaButton = new RadioButton("Text Area");
        radioButton = new RadioButton("Radio List Select");
        checkBoxButton = new RadioButton("Check Box Select");
        
        textAreaButton.setUserData("TextArea");
        textFieldButton.setUserData("TextField");
        radioButton.setUserData("Radio");
        checkBoxButton.setUserData("CheckBox");
        
        textAreaButton.setToggleGroup(typeButtons);
        textFieldButton.setToggleGroup(typeButtons);
        radioButton.setToggleGroup(typeButtons);
        checkBoxButton.setToggleGroup(typeButtons);
        
        questionBox.getChildren().add(textAreaButton);
        questionBox.getChildren().add(checkBoxButton);
        questionBox.getChildren().add(radioButton);
        questionBox.getChildren().add(textFieldButton);
        
        posAnswerList.add(field1);
        posAnswerList.add(field2);
        posAnswerList.add(field3);
        posAnswerList.add(field4);
        posAnswerList.add(field5);
        
        optionsLabel.setText("Answer Options");
        
        rightBox.setPadding(new Insets(0, 0, 0, 50));
        
       
    }
    
    /**
     * Save the current fields to the question selected 
     * @param question 
     */
    public void save(Question question) {
        
        // save question
        String prompt = questionArea.getText();
        if (!prompt.equals("")) {
            question.setPrompt(prompt);
        }
        
        // save type and possible answers
        if (typeButtons.getSelectedToggle() != null) {
            String currentType = question.getType();
            String nextType = typeButtons.getSelectedToggle().getUserData().toString();
            switch (nextType) {
                case "TextArea":
                case "TextField":
                    question.setType(nextType);
                    break;
                case "Radio":
                case "CheckBox":
                    List<String> newPosAnswers = new ArrayList<>();
                    for (TextField fieldToSave : posAnswerList) {
                        if (!fieldToSave.getText().equals("")) {
                            newPosAnswers.add(fieldToSave.getText().trim());
                        }
                    }   question.setType(nextType);
                    question.setPosAnswers(newPosAnswers);
                    break;
            }
        }
    }

    /**
     * Fill question and possible answer for selected question
     * @param question 
     */
    public void load(Question question) {
        String prompt = question.getPrompt();
        String type = question.getType();
        numberPosAnswerList.setValue("");
        
        questionArea.setText(prompt);
        if (type.equals("Radio") || type.equals("CheckBox")) {
            if (question.getPosAnswers().size() > 0) {
                fillPosAnswers(question.getPosAnswers());
            }
        }
        setRadioButtons(question);

    }

    /**
     * populate the possible answers with existing answers
     * @param posAnswers 
     */
    public void fillPosAnswers(List<String> posAnswers) {
        int index = 0;
        for (String currentAnswer : posAnswers) {
            TextField fieldToEdit = posAnswerList.get(index++);
            fieldToEdit.setText(currentAnswer);
        }
    }

    /**
     * clear values in the possible answers boxes
     */
    public void clearPosAnswers() {
        for (TextField toClear : posAnswerList) {
            toClear.setText("");
        }
    }

    /**
     * set screen parent and prepare controller for use
     * @param screenPage 
     */
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
        this.pdf = myController.pdf;
        setList(); 
        setTypeDisplay();
    }
    
    /**
     * This function will display the radio buttons and number of possible 
     * answers depending on the existing data
     * @param question 
     */
    private void setRadioButtons(Question question) {
        String type = question.getType();
        
        // select proper button depending on type and possible answers
        switch (type) {
            case "Radio":
            case "CheckBox":
                if (!question.getPosAnswers().isEmpty()) {
                    numberPosAnswerList.setValue(Integer.toString(question.getPosAnswers().size()));
                } else {
                    numberPosAnswerList.setValue(null);
                }   if (type.equals("Radio")) {
                    typeButtons.selectToggle(radioButton);
                } else {
                    typeButtons.selectToggle(checkBoxButton);
            }   break;
            case "TextArea":
                typeButtons.selectToggle(textAreaButton);
                break;
            case "TextField":
                typeButtons.selectToggle(textFieldButton);
                break;
            default:
                typeButtons.selectToggle(null);
                break;
        }
    }
    
    /**
     * Return to main screen after verifying all questions are answered
     */
    @FXML
    public void finish() {
        Question lastQuestion = questionListView.getSelectionModel().getSelectedItem();
        save(lastQuestion);
        boolean isFinished = true;
        for (Question curQuestion : questions) {
            if (curQuestion.getPrompt().equals("")) {
                showWarning("Question with hash: "
                        + curQuestion.getHash()
                        + " is missing a question!");
                isFinished = false;
                break;
            } 
            if (curQuestion.getType().equals("")) {
                showWarning("Question with hash: "
                        + curQuestion.getHash()
                        + " is missing a type!");
                isFinished = false;
                break;
            }
            if (curQuestion.getType().equals("Radio") || curQuestion.getType().equals("CheckBox")) {
                if (curQuestion.getPosAnswers().isEmpty()) {
                    showWarning("Question with hash: "
                            +     curQuestion.getHash() + 
                                    " is missing possible answers!");
                    isFinished = false;
                    break;
                }
            } 
        }
        if (isFinished) {
            myController.setScreen(MainUI.mainPage);
            pdf.setQuestionsLoaded(isFinished);
        }
    }
    
    /**
     * Prepare the list with the values from the list of questions
     */
    private void setList() {
        Set<Question> questionSet = pdf.getQuestionMap().keySet();
        
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
