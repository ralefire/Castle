/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testpdfaction;

import org.apache.pdfbox.util.PDFTextStripper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.TextPosition;

/**
 *
 * @author Admin
 */
public class TextStripper extends PDFTextStripper  {

    private List<TextPosition> characters = new ArrayList();
    private float previousX;
    private float previousY = 0.0f;
    private TextPosition previousT;
    private List<Object> indents = new ArrayList();
    private String content = "";
    private String fullContent = "";
    private float maxXWidth = 0.0f;
    private float minXWidth = 0.0f;
    private float maxYHeight = 0.0f;
    private float minYHeight = 0.0f;    
    private List<Object> lineSpaces = new ArrayList();

    public List<Object> getLineSpaces() {
        return lineSpaces;
    }
    
    public List<Object> getIndents() {
        return indents;
    }

    public void setIndents(List<Object> indents) {
        this.indents = indents;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public String getFullContent() {
        return fullContent;
    }

    public void setFullContent(String fullContent) {
        this.fullContent = fullContent;
    }
    
    public TextStripper() throws IOException {
        super.setSortByPosition(true);
    }
    public TextStripper(PDDocument doc) throws IOException {
        super.setSortByPosition(true);
        PDFTextStripper stripper = new PDFTextStripper();
        fullContent = stripper.getText(doc);
    }
    
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
    
    
    public List<TextPosition> getCharacters() {
        return characters;
    }

    public float getMinXWidth() {
        return minXWidth;
    }

    public float getMaxYHeight() {
        return maxYHeight;
    }

    public float getMinYHeight() {
        return minYHeight;
    }

    
    public float getMaxXWidth() {
        return maxXWidth;
    }

    
    /**
     * @param text The text to be processed
     */
    @Override 
    protected void processTextPosition(TextPosition text) {
        // characters.add(text);
        
        float fWidth = 12;//text.getWidth();
        //System.out.println(fWidth);
        float xAxis = text.getXDirAdj();
        float yAxis = text.getYDirAdj();
        
       
        
        if ((yAxis - previousY) > 1) {
            indents.add(xAxis);
            lineSpaces.add(yAxis - previousY);
            content += "\n";
            characters.add(text);
            
          //  System.out.println("newline: " + previousY + " " + yAxis );
        }
        previousY = yAxis;
        
        
        if ((xAxis - previousX) > fWidth) {
            int numSpaces = (int) ((xAxis - previousX) / (fWidth));
//            for (int i = 0; i < numSpaces; i++) {
//                content += " ";
//            }
            //System.out.println("INDENT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        previousX = xAxis;
        content += text.getCharacter();
        
        if (xAxis > maxXWidth)
            maxXWidth = xAxis;
        
        if (xAxis < minXWidth)
            maxXWidth = xAxis;
        
        if (yAxis > maxYHeight)
            maxYHeight = yAxis;
        
        if (yAxis < minYHeight)
            minYHeight = yAxis;
         //System.out.println("PREV: " +  previousX + ", Current: " + xAxis);
    }
    
}
