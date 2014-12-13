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

    PDDocument document;                // root PDF document object
    //List<Question> questions;           // represents the PDF questions
    private Boolean isLoaded;           // true if pdf document is loaded, false otherwise
    private Map<Question, String> answersMap;   // maps questions to answers
    private Map<String, Question> hashQuestionMap;
    private String textContent;         // represents the full PDF text content
    private boolean questionsLoaded;

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
     * 
     */
    public void loadQuestions() {
        String emptyList = "";
        answersMap.put(new Question("how old", "Age", "TextField"), emptyList);
        answersMap.put(new Question("describe it", "Damage", "TextArea"), emptyList);
        answersMap.put(new Question("how big", "House Size", "Radio"), emptyList);
        answersMap.put(new Question("check boxes", "Check Box", "CheckBox"), emptyList);
        //answersMap.put(new Question("What is your number?", "Number", "TextField"), emptyList);
        answersMap.put(new Question("Describe the flowing locks", "Hair", "TextArea"), emptyList);
        answersMap.put(new Question("What is your couch size?", "Couch Size", "Radio"), emptyList);
        answersMap.put(new Question("Which other ones do you want?", "More Boxes", "CheckBox"), emptyList);
    }
    
    /**
     * 
     * @return 
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
    
    public void setQuestionsLoaded(boolean bool) {
        questionsLoaded = bool;
    }
    /**
     * setter method for answers map
     * @param answersMap 
     */
    public void setAnswers(Map<Question, String> answersMap) {
        this.answersMap = answersMap;
    }
    
    public Map<Question, String> getQuestionMap() {
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
                
                Question newQuestion = new Question("", "", "");
                List<String> posAnswerList = new ArrayList<>();
                
                keysJSON.keySet().stream().forEach((key) -> {
                    Object value = keysJSON.get(key);
                    System.out.println(value);
                    
                    if (key.equals("prompt")) {
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
                newQuestion.setPosAnswers(posAnswerList);
                answersMap.put(newQuestion, "");
            }
        } else {

            PDFTextStripper stripMe = new PDFTextStripper();
            String content = stripMe.getText(document);

            Pattern hashPattern = Pattern.compile("@@\\w+@@");
            Matcher hashMatcher = hashPattern.matcher(content);

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

    }
    
    /**
     * Strips the text from the PDDocument then inserts the answers from
     * answersMap into the corresponding question Hash and loads the result
     * into textContent
     * @throws IOException
     * @throws ParseException 
     */
    public void insertResponses() throws IOException, ParseException {
        String extractedContent = extractText();
        for (Question tempQuestion : answersMap.keySet()) {
           extractedContent = extractedContent.replaceAll(tempQuestion.getHash(), answersMap.get(tempQuestion)); // REGEX, Replacement
        }
        textContent = extractedContent;
        System.out.println(textContent);
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
     * builds a map of hashes and questions from answers map.
     * @return hashQuestionMap
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
     * @param savePath
     * @throws java.io.IOException
     */
    public void buildPDF(String savePath) throws IOException, Exception {
        PDDocument doc = new PDDocument();  // new document to be saved
        PDPage page = new PDPage(); // start a new page
        doc.setDocumentInformation(document.getDocumentInformation()); // set the metadata to document meta data
        Map<String, Question> hashQuestionMap = getHashQuestionMap();

        // get and set the valid text dimensions
        List<PDRectangle> pageRecs = new ArrayList();
        List<PDPage> allPages = document.getDocumentCatalog().getAllPages();
        for (PDPage p : allPages) {
            pageRecs.add(p.getArtBox());
        }
        PDRectangle pageRec = pageRecs.get(0);
        float xLLBoundary = pageRec.getLowerLeftX();
        float yLLBoundary = pageRec.getLowerLeftY();
        float xURBoundary = pageRec.getUpperRightX();
        float yURBoundary = pageRec.getUpperRightY();
        
        System.out.println("LLX: " + xLLBoundary + ", URX: " + xURBoundary + 
                ", LLY: " + yLLBoundary + ", URY: " + yURBoundary);
        
        PDFTextStripper stripMe = new PDFTextStripper();
         System.out.println(stripMe.getText(document));
        
        // strip the text from document and load each character into a list
        TextStripper stripper = new TextStripper();
        stripper.processLocation(document);
        List<TextPosition> characters = stripper.getCharacters();
        
        
       
        
        float pIndex = 800; // the top index of each page
        PDFont font; // font for each character
        float fontSize; // font size
        PDPageContentStream content = new PDPageContentStream(doc, page); // content stream for writing characters
        float xAxis = 0;    // stores the x-axis position of the character
        float yAxis = 0;    // stores the y-axis position of the character
        float lastX = 0;    // stores the previous x-axis position
        float lastY = 0;    // stores the previous y-axis position
        float yOffset = 0;
        float xOffset = 0;
        String textCharacter = "";
        String fontName = "";
        byte[] eM = {(byte)'M'};
        float lineHeight = 0.0f; 
        Boolean shiftY = false; // true if y reflow needed
        Boolean shiftX = false; // true if x reflow needed
        Boolean newPage = false;
        float prevXAxisHash = 0.0f;
        float prevYAxisHash = 0.0f;        
        
        // character list iterators
        Iterator<TextPosition> it = characters.iterator();  // iterate through the character list
        // iterate through the next value of the character list (used for Hash detection)
        Iterator<TextPosition> it1 = characters.iterator(); 
        if (it1.hasNext())
            it1.next();
        
        content.beginText(); // begin writing a character
        // loop through each character of the character list
        while (it.hasNext()) {
            
            // Set current and next textPosition objects from character list
            TextPosition textPos = it.next(); // textPos representing current position
            TextPosition textPos1;            // textPos representing next position
            if (it1.hasNext()) {
                textPos1 = it1.next();
            } else {
                textPos1 = null;
            }
            
            textCharacter = textPos.getCharacter(); // get text character to be written
            font = textPos.getFont(); // font
            fontSize = textPos.getFontSize(); // font size
            fontName = font.toString(); // font name
            font = getFontFromName(fontName, fontSize); // set default font and font mapping
            lastY = yAxis;  // set previous y-axis value
            xAxis = textPos.getXDirAdj();   // get current x-axis position
            yAxis = textPos.getYDirAdj();   // get current y-axis position
            //lineHeight = font.getFontHeight(eM, 0, 1); // get line height
            lineHeight = 10;
            

                       
            // Answers replacement algorithm
            if (textCharacter.equals("@") && textPos1 != null && textPos1.getCharacter().equals("@")) {
                textPos = it.next(); // move to second "@"
                textCharacter = textPos.getCharacter();
                String hashCharacters = "";
                
                if (it1.hasNext())
                    textPos1 = it1.next();
                
                // Get the entire hash and load it into a string
                while (it.hasNext()) {
                    textPos = it.next();
                    textCharacter = textPos.getCharacter();
                    if (it1.hasNext())
                        textPos1 = it1.next();
                    
                    if (textCharacter.equals("@") && textPos1 != null && textPos1.getCharacter().equals("@")) {
                        while (it.hasNext() && textCharacter.equals("@") ) {
                            textPos = it.next();
                            textCharacter = textPos.getCharacter();
                            if (it1.hasNext())
                               textPos1 = it1.next();
                        }
                        break;
                    }
                    hashCharacters += textPos; // entire hash gets loaded here
                }
                
                // check if the hash is valid
                if (hashQuestionMap.containsKey(hashCharacters)) {                   
                    float xAxisHash = xAxis;    // xAxis position of hash answer character
                    float yAxisHash = yAxis;    // y-axis position of hash answer character
                    hashCharacters = answersMap.get(hashQuestionMap.get(hashCharacters)); // get the answer from the hash value                 
                    
                    // loop to replace the hash with an answer
                    for (int i = 0; i < hashCharacters.length(); i++) {
                        String answerCharacter = "" + hashCharacters.charAt(i); // load answer character from hash answers
                        // get font width
                        float fWidth = 11;
//                        font.getFontWidth(hashCharacters.charAt(i)); // get font width of answer character
//                        PDRectangle fontDim = font.getFontBoundingBox();
//                        fWidth = fontDim.getWidth();
//                        fWidth = (fWidth / 1000) * fontSize;
//                        fWidth = 11;
//                        System.out.println("Font Width: " + fWidth);
                            
                        xAxisHash += fWidth; // adjust x-axis position based on font width
                        
                        // if new line is needed
                        if (xAxisHash >= xURBoundary) {
                            System.out.println("X > Boundary");
                            xAxisHash = xLLBoundary; // move X position to lower left boundary
                            yAxisHash += lineHeight; // move Y position down 1 line
                        }

                        // if new page is needed
                        if (yAxisHash < yLLBoundary) {
                            content.endText();
                            content.close();
                            doc.addPage(page);
                            System.out.println("LastY: " + lastY + ", yAxis: " + yAxisHash + ", y-Boundary: " + yLLBoundary);
                            page = new PDPage();
                            content = new PDPageContentStream(doc, page);
                            // pIndex = 800;
                            yAxisHash = yURBoundary;
                            xAxisHash = xLLBoundary;
                            content.beginText();
                            newPage = true;
                        }
                        
                        writeCharacter(content, answerCharacter , font, fontSize, xAxisHash, yAxisHash );
                        
                        // restart writing for each line
                        if (xAxisHash >= 90) {
                            content.endText();
                            content.beginText();
                        }
                    } // END answers replacement loop
                    
                    xOffset = (xAxisHash - xAxis);
                    yOffset = (yAxisHash - yAxis);  
                    lastY = yAxisHash;
                    xAxis = textPos.getXDirAdj();   // get current x-axis position
                    yAxis = textPos.getYDirAdj();   // get current y-axis position
                    
                    
                    if (Math.abs(yAxis - yAxisHash ) <= 1) { //if y didn't change
                        yOffset = 0;
                    } else if (Math.abs(xAxis - xLLBoundary) < 5) { // if x is near the llboundary
                        xOffset = 0;
                        System.out.println("HAR ");
                    } else {
                        xOffset = 0;
                    }
                    
                    
                }
            }

                        
            // reset xOffset when a newline is reached
            if (textCharacter.equals("\n")) {
                xOffset = 0;
                System.out.println("Newline Found!");
            }
            
            xAxis += (xOffset);
            
            if ((yAxis -= (yOffset)) < yLLBoundary)
                yAxis += 800;
            
            // if offset requires a new line 
            if (xAxis > xURBoundary) { 
                System.out.println("Bound: " + xAxis + " " + xURBoundary);
                xAxis -= xURBoundary;
//                xAxis = xLLBoundary;
                xOffset = 0;
                System.out.println("X > URBoundary");
                
                
            }
            if (xAxis < xLLBoundary) { 
                System.out.println("Bound: " + xAxis + " " + xURBoundary);
                xAxis += xURBoundary;
                System.out.println("X > URBoundary");
            }
          

            
            // Logic for starting a new page 
            if (/*yAxis > (800 - yLLBoundary) || */(lastY - yAxis) > 1) {
                content.endText();
                content.close();
                doc.addPage(page);
                System.out.println("UPPER LastY: " + lastY + ", yAxis: " + yAxis + ", y-Boundary: " + yLLBoundary);
                page = new PDPage();
                content = new PDPageContentStream(doc, page);
                lastY = 0;
                content.beginText();
            }
            
            
            content.setFont( font, fontSize ); // set font (mandatory)
            content.moveTextPositionByAmount(0, 0); // Reset the reference point
            content.moveTextPositionByAmount( xAxis, pIndex - yAxis ); // set text position (mandatory)
            content.drawString(textCharacter); // draw a character


            
            // restart writing for each line
            if (xAxis >= 90) {
                content.endText();
                content.beginText();
            }
            
        }
        content.endText(); // end remaining text
        content.close(); // close content stream
        doc.addPage(page); // add remaining page
        save(doc, savePath); // save doc
        doc.close(); // close doc
    }
     
    
    private void writeCharacter(PDPageContentStream content, String character , PDFont font, float fontSize, float xAxis, float yAxis ) throws IOException {
            content.setFont( font, fontSize ); // set font (mandatory)
                // System.out.println("X: " + xAxis + ", Y: " + yAxis);
            content.moveTextPositionByAmount(0, 0); // Reset the reference point
            content.moveTextPositionByAmount( xAxis, 800 - yAxis ); // set text position (mandatory)
            content.drawString(character); // draw a character    
            System.out.println("CHAR: " + character);
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
