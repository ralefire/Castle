/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 *
 * @author Alex
 */
public class UIMain extends Stage {
    Button loadButton = new Button("Load Template");
    Button startButton = new Button("Start");
    Button saveButton = new Button("Save");
    Button saveAsButton = new Button("Save As");
    Button editButton = new Button("Edit");
    Button quitButton = new Button("Quit");
    Text title = new Text("Castle PDF Generator");
    Text space = new Text("");
    HBox titleBox = new HBox();
    BorderPane border = new BorderPane();
    VBox centerVbox = new VBox();

    Boolean templateLoaded = false;
    
    public UIMain() {
        formatButton(loadButton);
        formatButton(startButton);
        formatButton(saveAsButton);
        formatButton(saveButton);
        formatButton(editButton);
        formatButton(quitButton);
        loadedCheck();
        formatVbox();
        formatBorder();
        formatTitle();
        
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                templateLoaded = true;
                loadedCheck();
            }
        });
        
        this.setScene(new Scene(border, 350, 400));
        this.show();
        
    }
    
    public void loadedCheck() {
        if (!templateLoaded) {
            startButton.setDisable(true);
            saveAsButton.setDisable(true);
            saveButton.setDisable(true);
            editButton.setDisable(true);
        }
        else {
            startButton.setDisable(false);
            saveAsButton.setDisable(false);
            saveButton.setDisable(false);
            editButton.setDisable(false);
        }
    }

    public void formatBorder() {
        border.setCenter(centerVbox);
        border.setStyle("-fx-background-color: #9bdaef");
    }
    
    public void formatTitle() {
        title.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));
        titleBox.getChildren().add(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20, 0, 40, 0));
        titleBox.setMinHeight(50);
    }
    public void formatVbox() {
        centerVbox.getChildren().add(titleBox);
        centerVbox.getChildren().add(loadButton);
        centerVbox.getChildren().add(startButton);
        centerVbox.getChildren().add(saveButton);
        centerVbox.getChildren().add(saveAsButton);
        centerVbox.getChildren().add(editButton);
        centerVbox.getChildren().add(quitButton);
        centerVbox.setSpacing(10);
        centerVbox.setMaxSize(325, 375);
        centerVbox.setStyle("-fx-background-color: white");
        centerVbox.setAlignment(Pos.TOP_CENTER);

    }
    public void formatButton(Button button) {
        button.setMaxWidth(125);
        button.setMinWidth(125);
    }
}
