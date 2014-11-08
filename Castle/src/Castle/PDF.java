package Castle;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a representation of our PDF.
 * @author Xandron
 */
class PDF {
    /**
     * This is a map of the hashes to the questions.
     */
    Map<String, String> questions;
    /**
     * This is a map of the hashes to the answers.
     */
    Map<String, String> answers;
    /**
     * This is simply the content of the PDF.
     */
    String content;

    PDF() {
        this.answers = new HashMap<>();
        this.questions = new HashMap<>();
    }
    /**
     * This loads the filename into the PDF.
     * @param filename 
     */
    public void load(String filename){}
    /**
     * This saves the PDF to the filename specified.
     * @param filename 
     */
    public void save(String filename){}
    /**
     * This exports the PDF in the final format to the location specified.
     * @param filename 
     */
    public void export(String filename){}
}