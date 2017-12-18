package utilities;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

public class Util {

    public static int countOccurrences (String toFind, String array[]) {
        int counter = 0;
        for(String t: array) {
            counter +=  (t.equals(toFind) ? 1: 0);
        }
        return counter;
    }

    public static Boolean savePdfFromComponent(Component component, String fileName) {

        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());

        try {
            Document d = new Document();
            PdfWriter writer = PdfWriter.getInstance(d, new FileOutputStream(
                    fileName));
            d.open();

            Image iTextImage = Image.getInstance(writer, image, 1);
            iTextImage.setAbsolutePosition(80, 50);
            iTextImage.scalePercent(80);
            d.add(iTextImage);

            d.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
