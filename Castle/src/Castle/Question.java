/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Castle;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Question {
    private String prompt;
    private String hash;
    private String type;
    private List<String> posAnswers;


    public Question(String prompt, String hash, String type) {
        this.prompt = prompt;
        this.hash = hash;
        this.type = type;
        this.posAnswers = new ArrayList<>();
    }

    public Question(String prompt, String hash, String type, List<String> posAnswers) {
        this.prompt = prompt;
        this.hash = hash;
        this.type = type;
        this.posAnswers = posAnswers;
    }
    
    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public List<String> getPosAnswers() {
        return posAnswers;
    }

    public void setPosAnswers(List<String> posAnswers) {
        this.posAnswers = posAnswers;
    }
    
    @Override
    public String toString() {
        return hash;
    }
}
