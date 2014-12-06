package Castle;

import java.util.ArrayList;
import java.util.List;


public class RadioQuestion extends Question {
    List<String> posAnswers;

    public RadioQuestion(String prompt, String hash, String type) {
        super(prompt, hash, type);
    }

   
    public List<String> getPosAnswers() {
        return posAnswers;
    }

    public void setPosAnswers(List<String> posAnswers) {
        this.posAnswers = posAnswers;
    }
    
}
