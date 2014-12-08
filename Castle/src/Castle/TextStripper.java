/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Castle;

import java.io.File;
import org.apache.pdfbox.util.PDFTextStripper;
import java.io.IOException;
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
    
       public TextStripper() throws IOException {
        super.setSortByPosition(true);
    }

    public void processLocation(PDDocument doc) throws Exception {

        try {
            TextStripper printer = new TextStripper();
            List allPages = doc.getDocumentCatalog().getAllPages();
            for (int i = 0; i < allPages.size(); i++) {
                PDPage page = (PDPage) allPages.get(i);
                System.out.println("Processing page: " + i);
                PDStream contents = page.getContents();
                if (contents != null) {
                    printer.processStream(page, page.findResources(), page.getContents().getStream());
                }
            }
        } finally {
            if (doc != null) {
                doc.close();
            }
        }
    }

    /**
     * @param text The text to be processed
     */
    @Override /* this is questionable, not sure if needed... */
    protected void processTextPosition(TextPosition text) {
        System.out.println("String[" + text.getXDirAdj() + ","
                + text.getYDirAdj() + " fs=" + text.getFontSize() + " xscale="
                + text.getXScale() + " height=" + text.getHeightDir() + " space="
                + text.getWidthOfSpace() + " width="
                + text.getWidthDirAdj() + "]" + text.getCharacter());
    }
    
}
