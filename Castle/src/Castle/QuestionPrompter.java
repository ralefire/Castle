package Castle;

import java.util.HashMap;
import java.util.Map;

/**
 * The QuestionPrompter is a GUI to ask the user questions and store the answers.
 * @author Xandron
 */
class QuestionPrompter implements Runnable{
    Map<String, String> questions;
    Map<String, String> answers;

    QuestionPrompter() {
        this.answers = new HashMap<>();
        this.questions = new HashMap<>();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Prompts the user to answer each question.
     */
    private void prompt(){}
    
}
