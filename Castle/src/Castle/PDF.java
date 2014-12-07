/*
 * Helpful documentation: http://www.printmyfolders.com/Home/PDFBox-Tutorial
 */

package Castle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
    Map<Question, String> answersMap;   // maps questions to answers
    private String textContent;         // represents the full PDF text content

    /**
     * default constructor
     */
    public PDF() {
       // this.answers = new HashMap<>();
        this.questions = new ArrayList();
        this.isLoaded = false;
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
    public void setAnswers(Map<Question, String> answersMap) {
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
        
        JSONObject keysJSON = getKeywordsAsJSON();
        String keys = "";
        for(Object key : keysJSON.keySet()) {
            Object value = keysJSON.get(key);
            
        }
        

    }
    
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

    /**
     * Inserts the answer strings into the context text
     * @throws IOException
     * @throws ParseException 
     */
    public void addAnswersToTextContent() throws IOException, ParseException {
        textContent = extractText();
        JSONObject keyWords = getKeywordsAsJSON();
        
        
        String keys = "";
        for(Object key : keyWords.keySet()) {
            Object value = keyWords.get(key);
                  
        }
        
        
    }
    
    
    /**
     * Generates a new PDF and saves it to a file
     * @throws java.io.IOException
     */
    public void buildPDF() throws IOException {
        //Document luceneDocument = LucenePDFDocument.getDocument();
        PDDocument doc = null;
        PDPage page = null;

        doc = new PDDocument();
        try{
           doc = new PDDocument();
           page = new PDPage();

           doc.addPage(page);
           PDFont font = PDType1Font.HELVETICA_BOLD;

           PDPageContentStream PDPContent = new PDPageContentStream(doc, page);
           PDPContent.beginText();
           PDPContent.setFont( font, 12 ); // set font (mandatory)
           PDPContent.moveTextPositionByAmount( 100, 100 ); // set text position (mandatory)
           PDPContent.drawString("This is an awesome PDF"); // enter text
           PDPContent.endText(); 
           PDPContent.close();
           doc.save("test.pdf"); // save
           
        } catch (IOException | COSVisitorException io){
            System.out.println(io);
        } finally {
            if (doc != null)
                doc.close(); // close
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
     * set keywords meta-data from JSON object
     * @param data 
     */
    public void setKeywords(JSONObject data) {
        PDDocumentInformation info = document.getDocumentInformation();
        info.setCustomMetadataValue("HashKeys", data.toJSONString());
    }
    
    /**
     * get keywords meta-data as a JSON object
     * @return
     * @throws ParseException 
     */
    public JSONObject getKeywordsAsJSON() throws ParseException {
        String info = document.getDocumentInformation().getCustomMetadataValue("HashKeys");     
        return (JSONObject)new JSONParser().parse(info);
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
