
package Castle;

import java.util.ArrayList;
import java.util.List;

/**
 * Question object representing a single question
 * @author Admin
 */
public class Question {
    private String prompt;
    private String hash;
    private String type;
    private List<String> posAnswers;


    /**
     * Question constructor
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
     * Question constructor
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
     * Getter for prompt
     * @return 
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * setter for prompt
     * @param prompt 
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * getter for hash
     * @return 
     */
    public String getHash() {
        return hash;
    }

    /**
     * setter for hash
     * @param hash 
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * getter for type
     * @return 
     */
    public String getType() {
        return type;
    }

    /**
     * setter for type
     * @param type 
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * getter for posAnswers
     * @return 
     */
    public List<String> getPosAnswers() {
        return posAnswers;
    }

    /**
     * setter for posAnswers
     * @param posAnswers 
     */
    public void setPosAnswers(List<String> posAnswers) {
        this.posAnswers = posAnswers;
    }
    
    /**
     * Gets the hash value as a string
     * @return 
     */
    @Override
    public String toString() {
        return hash;
    }
}
