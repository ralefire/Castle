package Castle;

import java.util.ArrayList;
import java.util.List;


public class RadioQuestion extends Question {
    List<String> posAnswers;

    public RadioQuestion() {
        this.posAnswers = new ArrayList<>();
    }

    public List<String> getPosAnswers() {
        return posAnswers;
    }

    public void setPosAnswers(List<String> posAnswers) {
        this.posAnswers = posAnswers;
    }
    
}
