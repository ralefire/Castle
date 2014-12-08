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
    List<Question> questions;           // represents the PDF questions
    private Boolean isLoaded;           // true if pdf document is loaded, false otherwise
    Map<Question, List<String>> answersMap;   // maps questions to answers
    private String textContent;         // represents the full PDF text content
    private List<String> posAnswers;

    /**
     * default constructor
     */
    public PDF() {
       // this.answers = new HashMap<>();
        this.questions = new ArrayList();
        this.isLoaded = false;
        this.posAnswers = new ArrayList();
    }
    
    /**
     * 
     */
    public void loadQuestions() {
        List<String> radioAnswers = new ArrayList<>();
        List<String> checkBoxAnswers = new ArrayList<>();
        radioAnswers.add(">1000");
        radioAnswers.add("1000-2500");
        radioAnswers.add("2500<");
        checkBoxAnswers.add("This one");
        checkBoxAnswers.add("This one too");
        checkBoxAnswers.add("Don't forget this one");
        checkBoxAnswers.add("lastly, this one");
        questions.add(new TextQuestion("What is your age?", "Age", "TextField"));
        questions.add(new TextQuestion("Describe the damage", "Damage", "TextArea"));
        questions.add(new RadioQuestion("What is your house size?", "House Size", "Radio", radioAnswers));
        questions.add(new CheckBoxQuestion("Which ones do you want?", "Check Box", "CheckBox", checkBoxAnswers));
        questions.add(new TextQuestion("What is your number?", "Number", "TextField"));
        questions.add(new TextQuestion("Describe the flowing locks", "Hair", "TextArea"));
        questions.add(new RadioQuestion("What is your couch size?", "Couch Size", "Radio", radioAnswers));
        questions.add(new CheckBoxQuestion("Which other ones do you want?", "More Boxes", "CheckBox", checkBoxAnswers));
    }
    
    /**
     * getter method for questions list
     * @return questions
     */
    public List<Question> getQuestions() {
        return questions;
    }
    
    /**
     * setter method for questions list
     * @param questions 
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    
    /**
     * setter method for answers map
     * @param answersMap 
     */
    public void setAnswers(Map<Question, List<String>> answersMap) {
        this.answersMap = answersMap;
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

            String keys = "";
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
                        newQuestion = new TextQuestion(prompt, hash, type); // String prompt, String hash, String type
                        System.out.println(keysJSON);
                    } else if (type.equals("RadioQuestion")) {
                        newQuestion = new RadioQuestion(prompt, hash, type, posAnswers); // String prompt, String hash, String type
                        System.out.println(keysJSON);
                    } else if (type.equals("TextArea")) {
                        newQuestion = new RadioQuestion(prompt, hash, type, posAnswers); // String prompt, String hash, String type
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
        for (Question tempQuestion : answersMap.keySet()) {
           textContent = textContent.replaceAll(tempQuestion.getHash(), answersMap.get(tempQuestion)); // REGEX, Replacement
        }
    }  
    
    /**
     * This saves the PDF to the filename specified. 
     * @param filePath
     * @throws java.io.IOException
     * @throws org.apache.pdfbox.exceptions.COSVisitorException
     */
    public void save(String filePath) throws IOException, COSVisitorException{
        File file = new File(filePath);
        OutputStream out = new FileOutputStream(file);
        document.save(out);
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
     * @throws java.io.IOException
     */
    public void buildPDF(String savePath) throws IOException {
        //Document luceneDocument = LucenePDFDocument.getDocument();
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.setDocumentInformation(document.getDocumentInformation());
        
        try {
            doc.addPage(page);
            PDFont font = PDType1Font.HELVETICA_BOLD;

            PDPageContentStream content = new PDPageContentStream(doc, page);

            String lines[] = textContent.split("\\n");
            int xAxis = 30;
            int yAxis = 700;
            for (String line : lines) {
                content.beginText();
                content.setFont( font, 12 ); // set font (mandatory)
                content.moveTextPositionByAmount( xAxis, yAxis -= 20); // set text position (mandatory)
                content.drawString(line); // enter text
                content.endText();
           }

            content.close();
            doc.save(savePath); // save
            doc.close(); // close
        } catch (IOException | COSVisitorException io){
            System.out.println(io);
        } finally {
            if (doc != null)
                doc.close();
        }
    }
  
    /**
     * This function copies all of the text from the PDF and returns it
     * 
     * @throws IOException 
     */
    public String extractText() throws IOException {
        String content = "";
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
        Map efMap = new HashMap();
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
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(embeddedFile.getByteArray());
        fos.close();
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
