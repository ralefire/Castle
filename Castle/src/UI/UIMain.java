/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    BorderPane border = new BorderPane();
    VBox centerVbox = new VBox();
    
    public UIMain() {
        formatButton(loadButton);
        formatButton(startButton);
        formatButton(saveAsButton);
        formatButton(saveButton);
        formatButton(editButton);
        formatButton(quitButton);
        formatVbox();
        formatBorder();
        
        
        centerVbox.setAlignment(Pos.CENTER);
        this.setScene(new Scene(border, 350, 400));
        this.show();
        
    }
    
    public void formatBorder() {
        border.setCenter(centerVbox);
    }
    
    public void formatVbox() {
        centerVbox.getChildren().add(loadButton);
        centerVbox.getChildren().add(startButton);
        centerVbox.getChildren().add(saveButton);
        centerVbox.getChildren().add(saveAsButton);
        centerVbox.getChildren().add(editButton);
        centerVbox.getChildren().add(quitButton);
        centerVbox.setSpacing(10);
    }
    public void formatButton(Button button) {
        button.setMaxWidth(125);
        button.setMinWidth(125);
    }
}
