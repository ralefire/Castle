package Castle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The QuestionPrompter is a GUI to ask the user questions and store the answers.
 * @author Xandron
 */
public class QuestionPrompter {
    List<Question> questions;
    Map<Question, List<String>> answers;

    /**
     * 
     * @return Map<Question, String> 
     */
    public Map<Question, List<String>> getAnswers() {
        return answers;
    }

    /**
     * 
     * @param answers 
     */
    public void setAnswers(Map<Question, List<String>> answers) {
        this.answers = answers;
    }

    /**
     * 
     */
    public QuestionPrompter(){
        this.answers = new HashMap<>();
        this.questions = new ArrayList<>();
    }

    /**
     * 
     * @param questions 
     */
    public void setQuestions(List<Question> questions) {
        for (Question currentQuestion : questions) {
            answers.put(currentQuestion, new ArrayList<String>());
        }
    }
    
    public void addAnswer(Question question, List<String> answer) {
        answers.put(question, answer);
    }
    
}
