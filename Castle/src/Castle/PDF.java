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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.COSObjectable;
import org.apache.pdfbox.pdmodel.common.PDNameTreeNode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
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

    private PDDocument document;                // root PDF document object
    private Boolean isLoaded;           // true if pdf document is loaded, false otherwise
    private Map<Question, String> answersMap;   // maps questions to answers
    private Map<String, Question> hashQuestionMap;
    private String textContent;         // represents the full PDF text content
    private boolean questionsLoaded; // true if questions loaded, false otherwise

    /**
     * default constructor
     */
    public PDF() {
       // this.answers = new HashMap<>();
        this.isLoaded = false;
        this.answersMap = new HashMap();
        this.hashQuestionMap = new HashMap();
        this.questionsLoaded = false;
    }
    
    /**
     * Determines whether or not questions have been loaded yet
     * @return true if questions loaded, false otherwise
     */
    public boolean getQuestionsLoaded() {
        for (Question currentQuestion : answersMap.keySet()) {
            if (currentQuestion.getPrompt().equals("") || 
                currentQuestion.getHash().equals("") ||
                currentQuestion.getType().equals("")) {
                setQuestionsLoaded(false);
                break;
            } else if (currentQuestion.getType().equals("Radio") ||
                       currentQuestion.getType().equals("CheckBox")) {
                if (currentQuestion.getPosAnswers().isEmpty()) {
                    setQuestionsLoaded(false);
                }
            } else {
                setQuestionsLoaded(true);
            }
        }

        return questionsLoaded;
    }
    
    /**
     * setter method for questionsLoaded
     * @param bool 
     */
    public void setQuestionsLoaded(boolean bool) {
        questionsLoaded = bool;
    }
    
    /**
     * setter method for answers map
     * @param answersMap 
     */
    private void setAnswers(Map<Question, String> answersMap) {
        this.answersMap = answersMap;
    }
    
    /**
     * getter method for answers map
     * @return answersMap
     */
    public Map<Question, String> getQuestionMap() {
        return answersMap;
    }
    
    /**
     * true if document already loaded, false otherwise
     * @return true if document already loaded, false otherwise
     */
    private Boolean isLoaded() {
        return isLoaded;
    }
    

    /**
     * This loads the given file into the PDF and parses the PDF
     * to load the question/answer meta-data
     * @param filePath
     * @throws java.io.IOException
     * @throws org.json.simple.parser.ParseException
     */
    public void load(String filePath) throws IOException, ParseException {
        if (filePath == null || filePath.equals("")) // set default filePath if none specified
            filePath = "src/resources/6dot1.pdf";

        File file = new File(filePath); // create a file object from the filepath
        document = PDDocument.load(file); // load a PDDocument from the file
        isLoaded = true; // set isLoaded as file is now loaded!
        
        JSONArray jsonKeysArray = getKeywordsAsJSONArray(); // get keys from PDFMetadata
        
        // if keys found, load them!
        if (jsonKeysArray != null) {

            Iterator<JSONObject> jsonKeysIterator = jsonKeysArray.iterator();
            while (jsonKeysIterator.hasNext()) { // loop through keys
                
                JSONObject keysJSON = jsonKeysIterator.next();
                
                Question newQuestion = new Question("", "", "");
                List<String> posAnswerList = new ArrayList<>();
                
                keysJSON.keySet().stream().forEach((key) -> {
                    Object value = keysJSON.get(key);
                    System.out.println(value);
                    
                    if (key.equals("@!clear!@")) { // if keys are empty then try parsing for hashes
                        try {
                            parseHashes();
                        } catch (Exception e) {
                            System.out.println("Unable to parse PDF To find keywords");
                        }
                    } else if (key.equals("prompt")) {
                        newQuestion.setPrompt((String) value);
                    } else if (key.equals("hash")) {
                        newQuestion.setHash((String) value);
                    } else if (key.equals("type")) {
                        newQuestion.setType((String) value);
                    } else if (key.equals("posAnswer")) {
                        String newValue = ((String)value);
                        String answers[] = newValue.split("!@");
                        for (int i = 0; i < answers.length; i++) {
                            answers[i] = answers[i].trim();
                            if (!answers[i].isEmpty()) {
                                posAnswerList.add(answers[i]);
                            }
                        }
                    }
                });
                if (newQuestion.getHash().equals("")) {
                    break;
                }
                newQuestion.setPosAnswers(posAnswerList);
                answersMap.put(newQuestion, "");
            }
        } else {
            parseHashes();
        }

    }

    /**
     * Parses the PDF for special hash characters of the form @@hashname@@
     * @throws IOException 
     */
    private void parseHashes() throws IOException {
        // stip out the text
        PDFTextStripper stripMe = new PDFTextStripper(); 
        String content = stripMe.getText(document);
        
        // search for hashes of the form @@hashname@@ allowing for multiple words in the hash name
        Pattern hashPattern = Pattern.compile("@@\\w+(\\s\\w+)?@@"); 
        Matcher hashMatcher = hashPattern.matcher(content);
        
        // load the found hashes into the answers map
        while (hashMatcher.find()) {
            String hash = hashMatcher.group();
            hash = hash.replace("@@", "");
            if (!hashQuestionMap.containsKey(hash)) {
                Question question = new Question("", hash, "");
                hashQuestionMap.put(hash, question);
                answersMap.put(question, "");
            }
        }
        questionsLoaded = false;
    }
    
    /**
     * Strips the text from the PDDocument then inserts the answers from
     * answersMap into the corresponding question Hash and loads the result
     * into textContent
     * @throws IOException
     * @throws ParseException 
     */
    private void insertResponses() throws IOException, ParseException {
        String extractedContent = extractText();
        for (Question tempQuestion : answersMap.keySet()) {
           extractedContent = extractedContent.replaceAll(tempQuestion.getHash(), answersMap.get(tempQuestion)); // REGEX, Replacement
        }
        textContent = extractedContent;
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
    private void save(PDDocument doc, String filePath) throws IOException, COSVisitorException{
        if (doc == null)
            return;
        File file = new File(filePath);
        OutputStream out = new FileOutputStream(file);
        doc.save(out);
        out.close();
    }

    /**
     * Inserts the answer strings into the context text
     * @throws IOException
     * @throws ParseException 
     */
    private void addAnswersToTextContent() throws IOException, ParseException {
        textContent = extractText();
    } 
    
    /**
     * builds a map of hashes and questions from answers map.
     * @return hashQuestionMap1
     */
    private Map<String, Question> getHashQuestionMap() {
        Map<String, Question> hashQuestionMap = new HashMap();
        answersMap.keySet().stream().forEach((q) -> {
            hashQuestionMap.put(q.getHash(), q);
        });
        return hashQuestionMap;
    }
    
    /**
     * Takes a font name and size and maps it to a default font
     * @param fontName
     * @param fontSize
     * @return new font
     */
    private PDFont getFontFromName(String fontName, float fontSize) {
        PDFont font;
            switch(fontName) {
                case "org.apache.pdfbox.pdmodel.font.PDType0Font@7a46a697":
                case "org.apache.pdfbox.pdmodel.font.PDType0Font@3abfe836":
                    font = PDType1Font.TIMES_ITALIC;
                    if (fontSize > 18)
                        font = PDType1Font.TIMES_BOLD;
                    break;
                default:
                    font = PDType1Font.TIMES_ROMAN; //"org.apache.pdfbox.pdmodel.font.PDType0Font@3d04a311"
            }
        return font;
    }
    
    /**
     * Generates a new PDF and saves it to a file
     * This includes inserting the responses and re-flowing the rest of the PDF
     * @param filePath
     * @throws java.io.IOException
     */
    public void buildPDF(String filePath) throws IOException, Exception {
       PDDocument doc = new PDDocument();  // new document to be saved
        PDPage page = new PDPage(); // start a new page
        doc.setDocumentInformation(document.getDocumentInformation()); // set the metadata to document meta data
        Map<String, Question> hashQuestionMap1 = getHashQuestionMap();

        // get and set the valid text dimensions
        List<PDRectangle> pageRecs = new ArrayList();
        List<PDPage> allPages = document.getDocumentCatalog().getAllPages();
        for (PDPage p : allPages) {
            pageRecs.add(p.getArtBox());
        }
        
        // strip the text from document and load each character into a list
        TextStripper stripper = new TextStripper();
        stripper.processLocation(document);
        List<TextPosition> characters = stripper.getCharacters();
        List<Object> indents = stripper.getIndents();
        List<Object> lineSpaces = stripper.getLineSpaces();
        textContent = stripper.getContent();
        
        // get valid PDF dimensions
        float xLLBoundary = stripper.getMinXWidth();
        float yLLBoundary = stripper.getMinYHeight();
        float xURBoundary = stripper.getMaxXWidth();
        float yURBoundary = stripper.getMaxYHeight();
        // set default values for template PDF
        yURBoundary = 710;
        yLLBoundary = 100;
                
        PDFont font; // font for each character
        float fontSize; // font size
        PDPageContentStream content = new PDPageContentStream(doc, page); // content stream for writing characters
        float xAxis = xLLBoundary;    // stores the x-axis position of the character
        float yAxis = yURBoundary;    // stores the y-axis position of the character
        String fontName = "";
        float lineHeight = 15f;      
        
        addResponses(); // insert the responses from answers/questions map into textContent variable
        String lines[] = textContent.split("\\n"); // split textContent by newlines
            int index = 0; // index for indents and characters list
            String extra = "";

        // loop through lines
        for (int j = 1; j < lines.length; j++) {   
            String line = lines[j];
            
            TextPosition textPos = characters.get(index); // get character TextPosition object
            font = textPos.getFont(); // font
            fontSize = textPos.getFontSize(); // font size
            fontName = font.toString(); // font name
            font = getFontFromName(fontName, fontSize); // set default font and font mapping
            
            xAxis = (float)indents.get(index);  // set left indent of the line
            
            // reflow logic
            int width = (line.length());
            int xLimit = (int)((xURBoundary - xAxis) / textPos.getWidth());
            xLimit = 85;
            
            // insert the extra line width before the next line
            extra += line;
            line = extra;
            
            // line width goes past the limit then split the line and add the extra to extra
            if (width > (int)xLimit) {
                width = (line.length() - (int)(xLimit));
                extra = line.substring(xLimit); // get the substring
                line = line.substring(0, xLimit); // get the current line
                indents.add(index + 1, xAxis); // add extra indent to array
                characters.add(index + 1, textPos); // add a new character to the list for the extra line
                lineSpaces.add(index + 1, lineHeight); // add a new indent to the list for the extra line
            } else {
                extra = ""; // reset extra
            }
            
            index++;

            // Logic for starting a new page 
            if ( yAxis < yLLBoundary) {
                content.close();
                doc.addPage(page);
                page = new PDPage();
                content = new PDPageContentStream(doc, page);
                yAxis = yURBoundary;
            } 
            content.beginText();
            writeLine(content, line, font, fontSize, xAxis, yAxis -= lineHeight); // draw the line
            content.endText();
        }

        content.close(); // close content stream
        doc.addPage(page); // add remaining page
        save(doc, filePath); // save doc
        doc.close(); // close doc
    }
     
    /**
     * Use the character data and a string to draw a line of text on the PDF
     * @param content
     * @param character
     * @param font
     * @param fontSize
     * @param xAxis
     * @param yAxis
     * @throws IOException 
     */
    private void writeLine(PDPageContentStream content, String character , PDFont font, float fontSize, float xAxis, float yAxis ) throws IOException {
            content.setFont( font, fontSize ); // set font (mandatory)
            content.moveTextPositionByAmount( xAxis, yAxis ); // set text position (mandatory)
            content.drawString(character); // draw a character    
    }
    
    /**
     * Strips the text from the PDDocument then inserts the answers from
     * answersMap into the corresponding question Hash and loads the result
     * into textContent
     * @throws IOException
     * @throws ParseException 
     */
    private void addResponses() throws IOException, ParseException {
        for (Question tempQuestion : answersMap.keySet()) {
            String answer = answersMap.get(tempQuestion);
            String hash = "@@" + tempQuestion.getHash() + "@@";
            
            textContent = textContent.replaceAll(hash, answer); // REGEX, Replacement
        }
    }
    
  
    /**
     * This function copies all of the text from the PDF and returns it
     * 
     * @return 
     * @throws IOException 
     */
    private String extractText() throws IOException {
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
     * @return key words formatted as a JSONArray
     * @throws ParseException 
     */
    private JSONArray getKeywordsAsJSONArray() throws ParseException {
        String info = document.getDocumentInformation().getCustomMetadataValue("HashKeys");     
        return (JSONArray)new JSONParser().parse(info);
    }
    
    /**
     * get keywords meta-data as a String object
     * @return key words meta data as a string
     * @throws ParseException 
     */
    private String getKeywordsAsString() throws ParseException {
        String info = document.getDocumentInformation().getCustomMetadataValue("HashKeys");     
        return info;
    }
    
    /**
     * Attach the keys file to the PDF
     * @throws IOException 
     */
    private void attachKeysFile() throws IOException {
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
    
    /**
     * Loads content from attachment. This function is not used in the current implementation
     * @param filePath
     * @throws IOException 
     */
    private void loadPDFAttachment(String filePath) throws IOException {
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
    
    /**
     * Extracts multiple files from the PDF
     * This function is not used in the current implementation
     * @param names
     * @param filePath
     * @throws IOException 
     */
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

    /**
     * Extracts a single file from the PDF
     * This function is not used in the current implementation
     * @param filePath
     * @param filename
     * @param embeddedFile
     * @throws IOException
     * @throws FileNotFoundException 
     */
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
    
    /**
     * gets an embedded file object from the PDF
     * This function is not used in the current implementation
     * @param fileSpec
     * @return 
     */
    private static PDEmbeddedFile getEmbeddedFile(PDComplexFileSpecification fileSpec )
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
