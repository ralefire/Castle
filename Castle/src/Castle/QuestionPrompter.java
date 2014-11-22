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

    QuestionPrompter() {
        this.answers = new HashMap<>();
        this.questions = new ArrayList<>();
    }

    public QuestionPrompter(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public void run() {
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
