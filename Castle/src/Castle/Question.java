/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Castle;

/**
 *
 * @author Admin
 */
abstract public class Question {
    private String prompt;
    private String hash;
    private String type;

    public Question(String prompt, String hash, String type) {
        this.prompt = prompt;
        this.hash = hash;
        this.type = type;
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
    
    @Override
    public String toString() {
        return hash;
    }
}
