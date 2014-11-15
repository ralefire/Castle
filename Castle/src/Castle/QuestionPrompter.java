package Castle;

import java.util.HashMap;
import java.util.Map;

/**
 * The QuestionPrompter is a GUI to ask the user questions and store the answers.
 * @author Xandron
 */
class QuestionPrompter implements Runnable{
    Map<String, Question> questions;
    Map<String, Question> answers;

    QuestionPrompter() {
        this.answers = new HashMap<>();
        this.questions = new HashMap<>();
    }

    @Override
    public void run() {
        // Build the scene for the questions
        // Check if something was clicked.
            // If a browse button was hit, commit the answer and switch to that scene
            // If the done button was pressed, check if unanswered questions
                // If there are unanswered questions prompt if they really want to leave them blank.
                    // If no, continue, else return to CastleApp.
        
        // Unknowns:
            // Done button? (This may be the back button as well)
                // How do we want to deal with half-answered pdfs?
                    // Context: Currently we do not know if an answer has been left blank intentionally or not.
            // Scene is dynamic to question?
                // Do we have a radio scene and a text scene and a dropdown scene?
    }
    
    /**
     * Prompts the user to answer each question.
     */
    private void prompt(){}
    
}
