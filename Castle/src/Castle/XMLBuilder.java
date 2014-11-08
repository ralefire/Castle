package Castle;

import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;

/**
 *
 * @author Xandron
 */
class XMLBuilder implements Runnable{
    String XML;
    Map<String, String> questions;
    Map<String, String> answers;
    Document doc;

    XMLBuilder() {
        this.answers = new HashMap<>();
        this.questions = new HashMap<>();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Parses the XML file for hashes, questions, and answers.
     */
    private void parse(){}
    /**
     * Parses the hashes from the XML file.
     */
    private void parseHashes(){}
    /**
     * Parses the questions from the XML file.
     */
    private void parseQuestions(){}
    /**
     * Parses the answers from the XML file.
     */
    private void parseAnswers(){}
    /**
     * Saves the XML to the file specified.
     * @param filename 
     */
    public void save(String filename){}
}