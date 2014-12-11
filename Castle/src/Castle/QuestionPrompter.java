package Castle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The QuestionPrompter is a GUI to ask the user questions and store the answers.
 * @author Xandron
 */
public class QuestionPrompter {
    Map<Question, String> answers;

    /**
     * 
     * @return Map<Question, String> 
     */
    public Map<Question, String> getAnswers() {
        return answers;
    }

    /**
     * 
     * @param answers 
     */
    public void setAnswers(Map<Question, String> answers) {
        this.answers = answers;
    }

    /**
     * 
     */
    public QuestionPrompter(){
        this.answers = new HashMap<>();
    }
    
    /**
     * 
     * @param questions 
     */
    public void setQuestions(List<Question> questions) {
        String emptyString = "";
        for (Question currentQuestion : questions) {
            answers.put(currentQuestion, emptyString);
        }
    }
    
    public void addAnswer(Question question, String answer) {
        answers.put(question, answer);
    }
    
}
