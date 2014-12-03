package Castle;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is created to create the questions for the given hashes in the PDF.
 * @author Xandron
 */
public class QuestionBuilder implements Runnable{

    List<Question> questions;
    boolean isBuilt = false;

    /**
     * The Constructor of QuestionBuilder.
     */
    public QuestionBuilder(){
        this.questions = new ArrayList<>();
    }
    @Override
    public void run() {
    }
    /**
     * Has the user build questions from the hashes. 
     */
    private void buildQuestions(){}
    
    public boolean isBuilt() {
        return isBuilt;
    }
}
