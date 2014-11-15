package Castle;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * This is the main class for the application.
 * @author Xandron
 */
public class CastleApp extends Application {   
    QuestionBuilder questionBuilder;
    QuestionPrompter questionPrompter;
    PDF pdf;
    XMLBuilder xmlBuilder;
    
    @Override
    public void start(Stage primaryStage) {
        
        GridPane mainGrid = new GridPane();
        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);
        mainGrid.setPadding(new Insets(25, 25, 25, 25));
        
        addButton(mainGrid, "Load Template", 0, 0);
        addButton(mainGrid, "Start", 0, 1);
        addButton(mainGrid, "Save", 0, 2);
        addButton(mainGrid, "Save As", 0, 3);
        addButton(mainGrid, "Edit", 0, 4);
        addButton(mainGrid, "Quit", 0, 5);
        
        Scene mainScene = new Scene(mainGrid, 300, 250);
        
        primaryStage.setTitle("Castle");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void addButton(GridPane mainGrid, String buttonText, int col, int row) {
        // The Load Button
        Button btn;
        btn = new Button();
        btn.setText(buttonText);
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().add(btn);
        mainGrid.add(hb, col, row);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    public CastleApp(){}
    
    /**
     * This runs the program.
     */
    public void run(){}
    /**
     * This loads a file into the app.
     */
    private void load(){}
    /**
     * Quits the program.
     */
    private void quit(){}
    /**
     * Saves the template to the same file loaded from.
     */
    private void save(){}
    /**
     * Prompts the user where to save the updated template and saves file there.
     */
    private void saveAs(){}
    /**
     * Starts the main sequence of events.
     */
    private void startQuestionare(){}
    /**
     * Edits the PDF by rebuilding the Questions.
     */
    private void edit(){}
}
