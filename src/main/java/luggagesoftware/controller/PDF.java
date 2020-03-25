/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware.controller;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author User
 */
public class PDF {
    
    //creer variabelen
    private PDDocument doc;
    private PDPage page;
    private PDPageContentStream content;
    private String docName;

    public PDF(String docName) throws IOException {
        doc = new PDDocument();
        page = new PDPage();
        content = new PDPageContentStream(doc, page);
        this.docName = docName;
        doc.addPage(page);
        
        //DHL Logo
        BufferedImage awtImage = ImageIO.read(new File("src/images/dhl-logo.jpg"));
        PDImageXObject pdImageXObject = LosslessFactory.createFromImage(doc, awtImage);
        
        //voeg DHL LOGO toe verklein size met 4 (delen door 4), stel posities ook in
        content.drawImage(pdImageXObject, 190, 730, awtImage.getWidth() / 4, awtImage.getHeight() / 4);
    }

    //Snelle manier om text in te verwerken, zet tekst met posities
    public void setText(String cont, int size, int[] pos) throws IOException {
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, size);
        content.moveTextPositionByAmount(pos[0], pos[1]);
        content.drawString(cont);
        content.endText();
    }

    //sla pdf bestand op..
    public void saveFile() {
        try{
            content.close();
            doc.save("pdf/" + docName);
            doc.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }

    //Open file direct na het opslaan word dit aangeroepen
    public void openFile() {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFiles = new File("pdf/" + docName);
                Desktop.getDesktop().open(myFiles);
            } catch (IOException ex) {
                // no application registered for PDFss
                System.out.println(ex.getMessage());
            }
        }
    }

    //Deze 2 methodes zijn niet meer van toepassing omdat we geen tabellen hebben gebruikt..
    /*
    public void drawTable(String[][] contentx, int[] pos) throws IOException {
        drawTable(page, content, pos[0], pos[1], contentx);
    }

    public static void drawTable(PDPage page, PDPageContentStream contentStream,
            float y, float margin,
            String[][] content) throws IOException {
        final int rows = content.length;
        final int cols = content[0].length;
        final float rowHeight = 20f;
        final float tableWidth = page.getCropBox().getWidth() - margin - margin;
        final float tableHeight = rowHeight * rows;
        final float colWidth = tableWidth / (float) cols;
        final float cellMargin = 5f;

        //draw the rows
        float nexty = y;
        for (int i = 0; i <= rows; i++) {
            contentStream.drawLine(margin, nexty, margin + tableWidth, nexty);
            nexty -= rowHeight;
        }

        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {
            contentStream.drawLine(nextx, y, nextx, y - tableHeight);
            nextx += colWidth;
        }

        //now add the text
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

        float textx = margin + cellMargin;
        float texty = y - 15;
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[i].length; j++) {
                String text = content[i][j];
                contentStream.beginText();
                contentStream.moveTextPositionByAmount(textx, texty);
                contentStream.drawString(text);
                contentStream.endText();
                textx += colWidth;
            }
            texty -= rowHeight;
            textx = margin + cellMargin;
        }
    
    }*/

}
