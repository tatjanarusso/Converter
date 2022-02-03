package com.example.converter.util.converter;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class TxtToPdf extends Converter{
    public TxtToPdf(String original, String newFile){super(original, newFile);}

    public boolean convert() throws IOException {
        Document pdfDoc = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(pdfDoc, new FileOutputStream (pathToNew))
                    .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
        } catch (DocumentException e) {
            return false;
        }
        pdfDoc.open();

        Font myfont = new Font();
        myfont.setStyle(Font.NORMAL);
        myfont.setSize(11);
        try {
            pdfDoc.add(new Paragraph ("\n"));

            BufferedReader buffer = new BufferedReader(new FileReader (newFile));
            String line;
            while ((line = buffer.readLine()) != null) {
                Paragraph paragraph = new Paragraph(line + "\n", myfont);
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                pdfDoc.add(paragraph);
            }
            pdfDoc.close();
            buffer.close();
        } catch (DocumentException e) {
            return false;
        }

        return newFile.exists();
    }

}
