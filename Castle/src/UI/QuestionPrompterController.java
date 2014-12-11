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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
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
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        questionListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newQuestion) -> {
            hashNameLabel.setText(newQuestion.getHash());
            if (oldValue != (null)) {
                save(oldValue);
            }
            clearPosAnswers();
            numberPosAnswerList.setValue("");
            rightBox.getChildren().clear();
            load(newQuestion);
            setRadioButtons(newQuestion);
        });

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
                selectedQuestion.setPosAnswers(posAnswerGiven);
                posAnswer.getChildren().remove(numberPosAnswerList);
                posAnswer.getChildren().remove(rightBox);
                }
            }
            if (newValue == null) {
                if (posAnswer.getChildren().contains(numberPosAnswerList)) {
                    posAnswer.getChildren().remove(numberPosAnswerList);
                } 
                if (posAnswer.getChildren().contains(numberPosAnswerList)) {
                    posAnswer.getChildren().remove(numberPosAnswerList);
                }
            } else if (newValue.getUserData().toString().equals("Radio")
                    || newValue.getUserData().toString().equals("CheckBox")) {
 
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

        numberPosAnswerList.getSelectionModel().selectedItemProperty().addListener((observable, oldIndex, newIndex) -> {
            populateRightBox();
        });
    }

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
    
    public void save(Question question) {
        String prompt = questionArea.getText();
        if (!prompt.equals("")) {
            question.setPrompt(prompt);
        }
        if (typeButtons.getSelectedToggle() != null) {
            String currentType = question.getType();
            String nextType = typeButtons.getSelectedToggle().getUserData().toString();
            if (nextType.equals("TextArea") || nextType.equals("TextField")) {
                question.setType(nextType);
            } else if (nextType.equals("Radio") || nextType.equals("CheckBox")) {
                List<String> newPosAnswers = new ArrayList<>();
                for (TextField fieldToSave : posAnswerList) {
                    if (!fieldToSave.getText().equals("")) {
                        newPosAnswers.add(fieldToSave.getText());
                    }
                }
                question.setType(nextType);
                question.setPosAnswers(newPosAnswers);
            }
        }
    }

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
    }

    public void fillPosAnswers(List<String> posAnswers) {
        int index = 0;
        for (String posAnswer : posAnswers) {
            TextField fieldToEdit = posAnswerList.get(index++);
            fieldToEdit.setText(posAnswer);
        }
    }

    public void clearPosAnswers() {
        for (TextField toClear : posAnswerList) {
            toClear.setText("");
        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
        this.pdf = myController.pdf;
        setList(); 
        setTypeDisplay();
    }
    
    private void setRadioButtons(Question question) {
        String type = question.getType();
        
        if (type.equals("Radio") || type.equals("CheckBox")) {
            if (!question.getPosAnswers().isEmpty()) {
                numberPosAnswerList.setValue(Integer.toString(question.getPosAnswers().size()));
            } else {
                numberPosAnswerList.setValue(null);
            }
            if (type.equals("Radio")) {
                typeButtons.selectToggle(radioButton);
            } else {
                typeButtons.selectToggle(checkBoxButton);
            }
        } else if (type.equals("TextArea")) {
            typeButtons.selectToggle(textAreaButton);
        } else if (type.equals("TextField")) {
            typeButtons.selectToggle(textFieldButton);
        } else {
            typeButtons.selectToggle(null);
        }
    }
    
    @FXML
    public void finish() {
        Question lastQuestion = questionListView.getSelectionModel().getSelectedItem();
        save(lastQuestion);
        boolean isFinished = true;
        for (Question curQuestion : questions) {
            if (curQuestion.getPrompt().equals("")) {
                System.out.println("Question with hash: "
                        + curQuestion.getHash()
                        + " is missing a question!");
                isFinished = false;
            } 
            if (curQuestion.getType().equals("")) {
                System.out.println("Question with hash: "
                        + curQuestion.getHash()
                        + " is missing a type!");
                isFinished = false;
            }
            if (curQuestion.getType().equals("Radio") || curQuestion.getType().equals("CheckBox")) {
                if (curQuestion.getPosAnswers().size() == 0) {
                    System.out.println("Question with hash: "
                            +     curQuestion.getHash() + 
                                    " is missing possible answers!");
                    isFinished = false;
                }
            } 
        }
        if (isFinished) {
            myController.setScreen(MainUI.mainPage);
            pdf.setQuestionsLoaded(isFinished);
        }
    }
    private void setList() {
        Set<Question> questionSet = pdf.getQuestionMap().keySet();
        
        for (Question question : questionSet) {
            questions.add(question);
        }
        questionListView.setItems(questions);
    }
    
}
