package Castle;

import java.util.ArrayList;
import java.util.List;


public class CheckBoxQuestion extends Question {
    List<String> posAnswers;

    public CheckBoxQuestion(String prompt, String hash, String type, List<String> posAnswers) {
        super(prompt, hash, type);
        this.posAnswers = posAnswers;
    }

   
    public List<String> getPosAnswers() {
        return posAnswers;
    }

    public void setPosAnswers(List<String> posAnswers) {
        this.posAnswers = posAnswers;
    }
}
