package Castle;

import UI.UIMain;
import javafx.application.Application;
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
        new UIMain(pdf);
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
