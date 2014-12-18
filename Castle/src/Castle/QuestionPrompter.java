package Castle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The QuestionPrompter is a class to ask the user questions and store the answers.
 * @author Xandron
 */
public class QuestionPrompter {
    Map<Question, String> answers;

    /**
     * Simple getter
     * @return Map<Question, String> 
     */
    public Map<Question, String> getAnswers() {
        return answers;
    }

    /**
     * Simple Setter
     * @param answers 
     */
    public void setAnswers(Map<Question, String> answers) {
        this.answers = answers;
    }

    /**
     * Simple constructor
     */
    public QuestionPrompter(){
        this.answers = new HashMap<>();
    }
    
    /**
     * simple setter
     * @param questions 
     */
    public void setQuestions(List<Question> questions) {
        String emptyString = "";
        for (Question currentQuestion : questions) {
            answers.put(currentQuestion, emptyString);
        }
    }
    
    /**
     * Simple function to add a question and answer to the list
     * @param question
     * @param answer 
     */
    public void addAnswer(Question question, String answer) {
        answers.put(question, answer);
    }
    
}
