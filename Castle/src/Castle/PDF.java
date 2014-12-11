/*
 * Helpful documentation: http://www.printmyfolders.com/Home/PDFBox-Tutorial
 */

package Castle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.PDFTextStripper;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.COSObjectable;
import org.apache.pdfbox.pdmodel.common.PDNameTreeNode;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.util.TextPosition;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * This is a representation of our PDF.
 * @author Winners
 */
public class PDF {
    

    PDDocument document;                // root PDF document object
    //List<Question> questions;           // represents the PDF questions
    private Boolean isLoaded;           // true if pdf document is loaded, false otherwise
    private Map<Question, List<String>> answersMap;   // maps questions to answers
    private String textContent;         // represents the full PDF text content
    private List<String> posAnswers;
    private boolean questionsLoaded;

    /**
     * default constructor
     */
    public PDF() {
       // this.answers = new HashMap<>();
        this.isLoaded = false;
        this.posAnswers = new ArrayList();
        this.answersMap = new HashMap();
        this.questionsLoaded = false;
    }
    
    /**
     * 
     */
    public void loadQuestions() {
        List<String> radioAnswers = new ArrayList<>();
        List<String> checkBoxAnswers = new ArrayList<>();
        List<String> emptyList = new ArrayList<>();
        List<String> emptyList3 = new ArrayList<>();
        List<String> emptyList2 = new ArrayList<>();
        List<String> emptyList1 = new ArrayList<>();
        radioAnswers.add(">1000");
        radioAnswers.add("1000-2500");
        radioAnswers.add("2500<");
        checkBoxAnswers.add("This one");
        checkBoxAnswers.add("This one too");
        checkBoxAnswers.add("Don't forget this one");
        checkBoxAnswers.add("lastly, this one");
        answersMap.put(new Question("", "Age", ""), emptyList);
        answersMap.put(new Question("", "Damage", ""), emptyList);
        answersMap.put(new Question("", "House Size", ""), emptyList);
        answersMap.put(new Question("", "Check Box", ""), emptyList);
        answersMap.put(new Question("What is your number?", "Number", "TextField"), emptyList);
        answersMap.put(new Question("Describe the flowing locks", "Hair", "TextArea"), emptyList);
        answersMap.put(new Question("What is your couch size?", "Couch Size", "Radio", radioAnswers), emptyList);
        answersMap.put(new Question("Which other ones do you want?", "More Boxes", "CheckBox", checkBoxAnswers), emptyList);
    }
    
    /**
     * 
     * @return 
     */
    public boolean getQuestionsLoaded() {
        return questionsLoaded;
    }
    
    public void setQuestionsLoaded(boolean bool) {
        questionsLoaded = bool;
    }
    /**
     * setter method for answers map
     * @param answersMap 
     */
    public void setAnswers(Map<Question, List<String>> answersMap) {
        this.answersMap = answersMap;
    }
    
    public Map<Question, List<String>> getQuestionMap() {
        return answersMap;
    }
    
    /**
     * true if document already loaded, false otherwise
     * @return 
     */
    public Boolean isLoaded() {
        return isLoaded;
    }
    

    /**
     * This loads the filename into the PDF and parses the PDF
     * @param filePath
     * @throws java.io.IOException
     * @throws org.json.simple.parser.ParseException
     */
    public void load(String filePath) throws IOException, ParseException {
        if (filePath == null || filePath.equals(""))
            filePath = "src/resources/6dot1.pdf";

        File file = new File(filePath);
        document = PDDocument.load(file); //This will load a document from a file into PDDocument.
        isLoaded = true;
        
        JSONArray jsonKeysArray = getKeywordsAsJSONArray();
        
        if (jsonKeysArray != null) {

            Iterator<JSONObject> jsonKeysIterator = jsonKeysArray.iterator();
            while (jsonKeysIterator.hasNext()) {
                
                JSONObject keysJSON = jsonKeysIterator.next();
                
                keysJSON.keySet().stream().forEach((key) -> {
                    Object value = keysJSON.get(key);
                    String prompt = "";
                    String hash = "";
                    String type = "";
                    
                   // System.out.println(value);

                    if (key.equals("prompt")) {
                        prompt = (String)value;
                    }
                    else if (key.equals("hash")) {
                        hash = (String)value;
                    }
                     else if (key.equals("type"))
                    {
                        type = (String)value;
                    }                    
                    Question newQuestion;
                    if (type.equals("TextField")) {
                        newQuestion = new Question(prompt, hash, type); // String prompt, String hash, String type
                        System.out.println(keysJSON);
                    } else if (type.equals("RadioQuestion")) {
                        newQuestion = new Question(prompt, hash, type); // String prompt, String hash, String type
                        System.out.println(keysJSON);
                    } else if (type.equals("TextArea")) {
                        newQuestion = new Question(prompt, hash, type); // String prompt, String hash, String type
                        System.out.println(keysJSON);
                    }
                    
                });
            }
        }
    }
    
    /**
     * Strips the text from the PDDocument then inserts the answers from
     * answersMap into the corresponding question Hash and loads the result
     * into textContent
     * @throws IOException
     * @throws ParseException 
     */
    public void insertResponses() throws IOException, ParseException {
        textContent = extractText();

        answersMap.keySet().stream().forEach((tempQuestion) -> {
            String answer = "";
            answer = answersMap.get(tempQuestion).stream().map((tempAnswer) -> (tempAnswer + " ")).reduce(answer, String::concat);
            
            textContent = textContent.replaceAll(tempQuestion.getHash(), answer); // REGEX, Replacement
        });
    }
    
    /**
     * This saves the PDF to the filename specified. 
     * @param filePath
     * @throws java.io.IOException
     * @throws org.apache.pdfbox.exceptions.COSVisitorException
     */
    public void save(String filePath) throws IOException, COSVisitorException{
        File file = new File(filePath);
        try (OutputStream out = new FileOutputStream(file)) {
            document.save(out);
        }
    }
    
    /**
     * This saves the PDF to the filename specified.
     * @param doc
     * @param filePath
     * @throws java.io.IOException 
     * @throws org.apache.pdfbox.exceptions.COSVisitorException 
     */
    public void save(PDDocument doc, String filePath) throws IOException, COSVisitorException{
        if (doc == null)
            return;
        File file = new File(filePath);
        OutputStream out = new FileOutputStream(file);
        doc.save(out);
        out.close();
    }
    
    /**
     * This exports the PDF in the final format to the location specified.
     * @param filename 
     */
    public void export(String filename){}

    /**
     * Inserts the answer strings into the context text
     * @throws IOException
     * @throws ParseException 
     */
    public void addAnswersToTextContent() throws IOException, ParseException {
        textContent = extractText();
        

    } 
    
    /**
     * Generates a new PDF and saves it to a file
     * @param savePath
     * @throws java.io.IOException
     */
        public void buildPDF(String savePath) throws IOException, Exception {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.setDocumentInformation(document.getDocumentInformation());
        
        String tContent = "";
        TextStripper stripper = new TextStripper();
        stripper.processLocation(document);
        List<TextPosition> characters = stripper.getCharacters();
        
        PDFont font = PDType1Font.HELVETICA_BOLD;
        float fontSize;
        PDPageContentStream content = new PDPageContentStream(doc, page);
        float xAxis;
        float yAxis;
        
        doc.addPage(page);
        Iterator<TextPosition> it = characters.iterator();
        Iterator<TextPosition> it1 = characters.iterator();
        if (it1.hasNext())
            it1.next();
        
        content.beginText();
        while (it.hasNext()) {
            TextPosition textPos = it.next();
            TextPosition textPos1;
            if (it1.hasNext()) {
                textPos1 = it1.next();
            } else {
                textPos1 = null;
            }
            
            String textCharacter = textPos.getCharacter();
            
            font = textPos.getFont();
            fontSize = textPos.getFontSize();
            String fontName = font.toString();

            switch(fontName) {
                case "org.apache.pdfbox.pdmodel.font.PDType0Font@7a46a697":
                    font = PDType1Font.TIMES_ROMAN;
                    if (fontSize > 18)
                        font = PDType1Font.TIMES_BOLD;
                    break;
                default:
                    font = PDType1Font.TIMES_ROMAN; //"org.apache.pdfbox.pdmodel.font.PDType0Font@3d04a311"
            }
            
            xAxis = textPos.getXDirAdj();
            yAxis = textPos.getYDirAdj();
            
            if (textCharacter.equals("@") && textPos1 != null && textPos1.getCharacter().equals("@")) {
                
                //TODO Add the answers replacement algorithm
//                while (it.hasNext()) {
//                    if 
//                }
                
            }
            //TODO build the PDF character by character
            
            content.setFont( font, fontSize ); // set font (mandatory)
        // System.out.println("X: " + xAxis + ", Y: " + yAxis);
            content.moveTextPositionByAmount(0, 0); // Reset the reference point
            content.moveTextPositionByAmount( xAxis, 800 - yAxis ); // set text position (mandatory)
           
            
            content.drawString(textCharacter); // enter text

            if (xAxis >= 90) {
                content.endText();
                content.beginText();
            }
            
        }
        content.endText();
        
//        String lines[] = textContent.split("\\n");
//        int xAxis = 30;
//        int yAxis = 700;
//
//        for (String line : lines) {
//            content.beginText();
//            content.setFont( font, 12 ); // set font (mandatory)
//            content.moveTextPositionByAmount( xAxis, yAxis -= 20); // set text position (mandatory)
//            content.drawString(line); // enter text
//            content.endText();
//        }

            content.close();
            save(doc, savePath); // save
            doc.close(); // close
    }
  
    /**
     * This function copies all of the text from the PDF and returns it
     * 
     * @return 
     * @throws IOException 
     */
    public String extractText() throws IOException {
        String content;
        PDFTextStripper stripper = new PDFTextStripper();
        content = stripper.getText(document);
        return content;
    }
       
    /**
     * set keywords meta-data from JSONArray
     * @param data 
     */
    public void setKeywords(JSONArray data) {
        PDDocumentInformation info = document.getDocumentInformation();
        info.setCustomMetadataValue("HashKeys", data.toJSONString());
    }
    
    /**
     * get keywords and return them as a JSONArray
     * @return
     * @throws ParseException 
     */
    public JSONArray getKeywordsAsJSONArray() throws ParseException {
        String info = document.getDocumentInformation().getCustomMetadataValue("HashKeys");     
        return (JSONArray)new JSONParser().parse(info);
    }
    
    /**
     * get keywords meta-data as a String object
     * @return
     * @throws ParseException 
     */
    public String getKeywordsAsString() throws ParseException {
        String info = document.getDocumentInformation().getCustomMetadataValue("HashKeys");     
        return info;
    }
    
    /**
     * Attach the keys file to the PDF
     * @throws IOException 
     */
    public void attachKeysFile() throws IOException {
        PDEmbeddedFilesNameTreeNode efTree = new PDEmbeddedFilesNameTreeNode();

        //first create the file specification, which holds the embedded file
        PDComplexFileSpecification fileSpec = new PDComplexFileSpecification();
        fileSpec.setFile( "Keys.txt" );
        InputStream is = getClass().getResourceAsStream("/resources/" + "Keys.txt");
        PDEmbeddedFile pdEmbedFile = new PDEmbeddedFile(document, is );
        

        //set some of the attributes of the embedded file
        pdEmbedFile.setSubtype( "test/plain" );
        //pdEmbedFile.setSize( data.length );
        pdEmbedFile.setCreationDate( new GregorianCalendar() );
        fileSpec.setEmbeddedFile(pdEmbedFile );

        //add the entry to the embedded file tree and set in the document.
        Map<String, PDComplexFileSpecification> efMap = new HashMap<>();
        efMap.put("My first attachment", fileSpec );
        efTree.setNames( efMap );
        
        //attachments are stored as part of the "names" dictionary in the document catalog
        PDDocumentNameDictionary names = new PDDocumentNameDictionary( document.getDocumentCatalog() );
        names.setEmbeddedFiles( efTree );
        document.getDocumentCatalog().setNames( names );
    }
    
    
    public void loadPDFAttachment(String filePath) throws IOException {
        PDDocumentNameDictionary namesDictionary = new PDDocumentNameDictionary(document.getDocumentCatalog());
        PDEmbeddedFilesNameTreeNode efTree = namesDictionary.getEmbeddedFiles();
        if (efTree != null)
        {
            Map<String,COSObjectable> names = efTree.getNames();
            if (names != null)
            {
                extractFiles(names, filePath);
            }
            else
            {
                List<PDNameTreeNode> kids = efTree.getKids();
                for (PDNameTreeNode node : kids)
                {
                    names = node.getNames();
                    extractFiles(names, filePath);
                }
            }
        }
    }
    
    
    private static void extractFiles(Map<String,COSObjectable> names, String filePath) 
            throws IOException
    {
        for (String filename : names.keySet())
        {
            PDComplexFileSpecification fileSpec = (PDComplexFileSpecification)names.get(filename);
            PDEmbeddedFile embeddedFile = getEmbeddedFile(fileSpec);
            extractFile(filePath, filename, embeddedFile);
        }
    }

    private static void extractFile(String filePath, String filename, PDEmbeddedFile embeddedFile)
            throws IOException, FileNotFoundException
    {
        String embeddedFilename = filePath + filename;
        File file = new File(filePath + filename);
        System.out.println("Writing " + embeddedFilename);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(embeddedFile.getByteArray());
        }
    }
    
    public static PDEmbeddedFile getEmbeddedFile(PDComplexFileSpecification fileSpec )
    {
        // search for the first available alternative of the embedded file
        PDEmbeddedFile embeddedFile = null;
        if (fileSpec != null)
        {
            embeddedFile = fileSpec.getEmbeddedFileUnicode(); 
            if (embeddedFile == null)
            {
                embeddedFile = fileSpec.getEmbeddedFileDos();
            }
            if (embeddedFile == null)
            {
                embeddedFile = fileSpec.getEmbeddedFileMac();
            }
            if (embeddedFile == null)
            {
                embeddedFile = fileSpec.getEmbeddedFileUnix();
            }
            if (embeddedFile == null)
            {
                embeddedFile = fileSpec.getEmbeddedFile();
            }
        }
        return embeddedFile;
    }
    
    
}
