/*
 * Helpful documentation: http://www.printmyfolders.com/Home/PDFBox-Tutorial
 */

package Castle;

import java.io.File;
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
    
    PDDocument document;
    List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    private Boolean isLoaded;
    
    Map<Question, String> answers;
    /**
     * This is simply the content of the PDF.
     */
    String content;

    /**
     * default constructor
     */
    public PDF() {
       // this.answers = new HashMap<>();
       // this.questions = new ArrayList();
        this.isLoaded = false;
    }
    
    public Boolean isLoaded() {
        return isLoaded;
    }
    
    
    //        document.getNumberOfPages(); //Returns the number of pages in the PDF file.
    //        document.isEncrypted(); //Lets you know if the PDF file is encrypted or not.
    //        document.void print(); //Sends the PDF document to a printer.
    //        document.removePage(int pageNumber); //Removes the page referred to by the page number.
    //        document.save(String fileName); //Saves the PDF file under the file name.
    //        document.silentPrint(); //This will send the PDF to the default printer without prompting the user for any printer settings.
    //        document.close(); //This will close the file.
    /**
     * This loads the filename into the PDF.
     * @param filename 
     */
    public void load(String filePath) throws IOException {
        if (filePath == null || filePath.equals(""))
            filePath = "src/resources/6dot1.pdf";

        File file = new File(filePath);
        document = PDDocument.load(file); //This will load a document from a file into PDDocument.
        isLoaded = true;         
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
     * Generates a new PDF and saves it to a file
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

           PDPageContentStream content = new PDPageContentStream(doc, page);
           content.beginText();
           content.setFont( font, 12 ); // set font (mandatory)
           content.moveTextPositionByAmount( 100, 100 ); // set text position (mandatory)
           content.drawString("This is an awesome PDF"); // enter text
           content.endText(); 
           content.close();
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
    
    
}
