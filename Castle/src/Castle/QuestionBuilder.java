package Castle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is created to create the questions for the given hashes in the PDF.
 * @author Xandron
 */
public class QuestionBuilder implements Runnable{

    private final Map<Question, String> answers;
    private boolean built = false;

    /**
     * The Constructor of QuestionBuilder.
     * @param questions
     */
    public QuestionBuilder(List<Question> questions){
        this.answers = new HashMap<>();
    }
    @Override
    public void run() {
    }
    /**
     * Has the user build questions from the hashes. 
     */
    private void buildQuestions(){}
    
    public boolean isBuilt() {
        return built;
    }

    /**
     * @return the questions
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * @param questions the questions to set
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * @param built the built to set
     */
    public void setBuilt(boolean built) {
        this.built = built;
    }
}
