/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Castle;

import java.util.ArrayList;
import java.util.List;

/**
 *Basic question classs
 * @author Admin
 */
public class Question {
    private String prompt;
    private String hash;
    private String type;
    private List<String> posAnswers;


    /**
     * Basic Constructor
     * @param prompt
     * @param hash
     * @param type 
     */
    public Question(String prompt, String hash, String type) {
        this.prompt = prompt;
        this.hash = hash;
        this.type = type;
        this.posAnswers = new ArrayList<>();
        posAnswers.clear();
    }

    /**
     * Constructor that includes possible answers
     * @param prompt
     * @param hash
     * @param type
     * @param posAnswers 
     */
    public Question(String prompt, String hash, String type, List<String> posAnswers) {
        this.prompt = prompt;
        this.hash = hash;
        this.type = type;
        this.posAnswers = posAnswers;
    }
    
    /**
     * Simple getter
     * @return 
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Simple Setter
     * @param prompt 
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * Simple getter
     * @return 
     */
    public String getHash() {
        return hash;
    }

    /**
     * Simple Setter
     * @param prompt 
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Simple getter
     * @return 
     */
    public String getType() {
        return type;
    }

    /**
     * Simple Setter
     * @param prompt 
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * Simple getter
     * @return 
     */
    public List<String> getPosAnswers() {
        return posAnswers;
    }

    /**
     * Simple Setter
     * @param prompt 
     */
    public void setPosAnswers(List<String> posAnswers) {
        this.posAnswers = posAnswers;
    }
    
    /**
     * Allows for a simple display of the question by the hash value
     * @return 
     */
    @Override
    public String toString() {
        return hash;
    }
}
