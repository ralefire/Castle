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
public class QuestionPrompter implements Runnable{
    List<Question> questions;
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
        this.questions = new ArrayList<>();
    }

    /**
     * 
     * @param questions 
     */
    public void setQuestions(List<Question> questions) {
        for (Question currentQuestion : questions) {
            answers.put(currentQuestion, "");
        }
    }
    
    public void addAnswer(Question question, String answer) {
        answers.put(question, answer);
    }
    
    @Override
    public void run() {
        System.out.println("This will run the prompter");
        promptQuestions();
    }
    
    /**
     * Prompts the user to answer each question.
     */
    private void promptQuestions() {
        while(questions != null && !questions.isEmpty()) {
            System.out.println(questions.get(0).getPrompt());
            try (Scanner in = new Scanner(System.in)) {
                String answer = in.nextLine();
                answers.put(questions.get(0), answer);
            }
            questions.remove(0);
        }  
    }
}
