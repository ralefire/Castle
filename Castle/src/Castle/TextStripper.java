/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Castle;

import org.apache.pdfbox.util.PDFTextStripper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.TextPosition;

/**
 *
 * @author Admin
 */
public class TextStripper extends PDFTextStripper  {
    
    // List of TextPosition objects for each character stripped
    private List<TextPosition> characters = new ArrayList(); 
    
    /**
     * Default constructor, sets the sort position to true
     * @throws IOException 
     */
    public TextStripper() throws IOException {
        super.setSortByPosition(true);
    }

    /**
     * Strips the text from a PDDocument character by character
     * @param doc
     * @throws Exception 
     */
    public void processLocation(PDDocument doc) throws Exception {
        try {
            List allPages = doc.getDocumentCatalog().getAllPages();
            for (int i = 0; i < allPages.size(); i++) {
                PDPage page = (PDPage) allPages.get(i);
                
                System.out.println("Processing page: " + i);
                PDStream contents = page.getContents();
                if (contents != null) {
                    this.processStream(page, page.findResources(), page.getContents().getStream());
                }
            }
        } finally {
            if (doc != null) {
                doc.close();
            }
        }
    }
    
    /**
     * Getter method for characters
     * @return 
     */
    public List<TextPosition> getCharacters() {
        return characters;
    }

    /**
     * Builds a list of text position objects for each character parsed
     * @param text The text to be processed
     */
    @Override 
    protected void processTextPosition(TextPosition text) {
        characters.add(text);
    }
    
}
