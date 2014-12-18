
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
 * Text Stripper extends PDFTextStripper to obtain extra
 * information about each character in the stripped PDF
 * @author Admin
 */
public class TextStripper extends PDFTextStripper  {

    private List<TextPosition> characters = new ArrayList<>();
    private float previousX;
    private float previousY = 0.0f;
    private List<Object> indents = new ArrayList<>();
    private String content = "";
    private String fullContent = "";
    private float maxXWidth = 0.0f;
    private float minXWidth = 0.0f;
    private float maxYHeight = 0.0f;
    private float minYHeight = 0.0f;    
    private List<Object> lineSpaces = new ArrayList<>();

    /**
     * getter for characters
     * @return 
     */
    public List<TextPosition> getCharacters() {
        return characters;
    }

    /**
     * getter for minimum x value found on the PDF
     * @return 
     */
    public float getMinXWidth() {
        return minXWidth;
    }

    /**
     * getter for the maximum y height found on the PDF
     * @return 
     */
    public float getMaxYHeight() {
        return maxYHeight;
    }

    /**
     * getter for minimum y height found on the PDF
     * @return 
     */
    public float getMinYHeight() {
        return minYHeight;
    }

    /**
     * getter for maximum x value found on the PDF
     * @return 
     */
    public float getMaxXWidth() {
        return maxXWidth;
    }
    
    /**
     * get list of differences between line heights of each line
     * @return 
     */
    public List<Object> getLineSpaces() {
        return lineSpaces;
    }
    
    /**
     * get list of x-indent values
     * @return 
     */
    public List<Object> getIndents() {
        return indents;
    }

    /**
     * setter for indents
     * @param indents 
     */
    public void setIndents(List<Object> indents) {
        this.indents = indents;
    }

    /**
     * getter for content
     * @return 
     */
    public String getContent() {
        return content;
    }

    /**
     * setter for content
     * @param content 
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * getter for full text string
     * @return 
     */
    public String getFullContent() {
        return fullContent;
    }

    /**
     * setter for full text content string
     * @param fullContent 
     */
    public void setFullContent(String fullContent) {
        this.fullContent = fullContent;
    }
    
    /**
     * set the sort by position value of the parent class
     * @throws IOException 
     */
    public TextStripper() throws IOException {
        super.setSortByPosition(true);
    }
    
    /**
     * constructor for TextStripper
     * @param doc
     * @throws IOException 
     */
    public TextStripper(PDDocument doc) throws IOException {
        super.setSortByPosition(true);
        PDFTextStripper stripper = new PDFTextStripper();
        fullContent = stripper.getText(doc);
    }
    
    /**
     * processes the text locations
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
     * Overrides the process function to get extra text details
     * and build a character and text indent list
     * @param text The text to be processed
     */
    @Override 
    protected void processTextPosition(TextPosition text) {
        float fWidth = 12;//text.getWidth();
        float xAxis = text.getXDirAdj();
        float yAxis = text.getYDirAdj();

        // if y changes significantly then add a new line, indent, and character
        if ((yAxis - previousY) > 1) {
            indents.add(xAxis);
            lineSpaces.add(yAxis - previousY);
            content += "\n";
            characters.add(text);
        }
        previousY = yAxis;
        
        // if x changes significantly then add the correct number of spaces to the list
//        if ((xAxis - previousX) > fWidth) {
//            int numSpaces = (int) ((xAxis - previousX) / (fWidth));
//        }
        previousX = xAxis;
        content += text.getCharacter();
        
        // logic for finding the x and y boundaries
        if (xAxis > maxXWidth)
            maxXWidth = xAxis;
        
        if (xAxis < minXWidth)
            maxXWidth = xAxis;
        
        if (yAxis > maxYHeight)
            maxYHeight = yAxis;
        
        if (yAxis < minYHeight)
            minYHeight = yAxis;
    }
    
}
