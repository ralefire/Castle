/*
 * Helpful documentation: http://www.printmyfolders.com/Home/PDFBox-Tutorial
 */

package Castle;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * This is a representation of our PDF.
 * @author Winners
 */
class PDF {
    
    PDDocument document;
    
    /**
     * This is a map of the hashes to the questions.
     */
    Map<String, Question> questions;
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
    public void load(String filePath) throws IOException {
        if (filePath == null || filePath.equals(""))
            filePath = "C:/Users/Admin/Documents/NetBeansProjects/TestPDFAction/src/resources/6dot1.pdf";

        File file = new File(filePath);
        document = PDDocument.load(file); //This will load a document from a file into PDDocument.

//        document.getNumberOfPages(); //Returns the number of pages in the PDF file.
//        document.isEncrypted(); //Lets you know if the PDF file is encrypted or not.
//        document.void print(); //Sends the PDF document to a printer.
//        document.removePage(int pageNumber); //Removes the page referred to by the page number.
//        document.save(String fileName); //Saves the PDF file under the file name.
//        document.silentPrint(); //This will send the PDF to the default printer without prompting the user for any printer settings.
//        document.close(); //This will close the file.
        
        
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
    public void buildPDF() {
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
           content.moveTextPositionByAmount( 100, 700 ); // set text position (mandatory)
           content.drawString("This is an awesome PDF"); // enter text
           content.endText(); 
           content.close();
           doc.save("test.pdf"); // save
           doc.close(); // close
        } catch (IOException | COSVisitorException io){
            System.out.println(io);
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
        
}