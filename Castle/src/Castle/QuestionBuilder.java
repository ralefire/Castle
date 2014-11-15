package Castle;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is created to create the questions for the given hashes in the PDF.
 * @author Xandron
 */
class QuestionBuilder implements Runnable{
    /**
     * This is the map of hashes to questions that will be asked.
     */
    Map<String, Question> questions;

    /**
     * The Constructor of QuestionBuilder.
     */
    public QuestionBuilder(){
        this.questions = new HashMap<>();
    }
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Has the user build questions from the hashes. 
     */
    private void buildQuestions(){}
}
