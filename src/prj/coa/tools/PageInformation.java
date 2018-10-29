package prj.coa.tools;

import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfReader;

public class PageInformation {
	/** The resulting text file with info about a PDF. */
    public static final String RESULT  = "results/part2/chapter06/page_info.txt";
 
    /**
     * Main method.
     * @param args no arguments needed
     * @throws DocumentException 
     * @throws IOException
     */
    public static void main(String[] args)
        throws DocumentException, IOException {
    	String filePath = "D:\\temp.pdf";
        inspect(filePath);
    }
 
    /**
     * Inspect a PDF file and write the info to a txt file
     * @param writer Writer to a text file
     * @param filename Path to the PDF file
     * @throws IOException
     */
    public static void inspect(String filename)
        throws IOException {
        PdfReader reader = new PdfReader(filename);
        System.out.println(filename);
        System.out.print("Number of pages: ");
        System.out.println(reader.getNumberOfPages());
        Rectangle mediabox = reader.getPageSize(1);
        System.out.print("Size of page 1: [");
        System.out.print(mediabox.getLeft());
        System.out.print(',');
        System.out.print(mediabox.getBottom());
        System.out.print(',');
        System.out.print(mediabox.getRight());
        System.out.print(',');
        System.out.print(mediabox.getTop());
        System.out.println("]");
        System.out.print("Rotation of page 1: ");
        System.out.println(reader.getPageRotation(1));
        System.out.print("Page size with rotation of page 1: ");
        System.out.println(reader.getPageSizeWithRotation(1));
        System.out.print("Is rebuilt? ");
        System.out.println(reader.isRebuilt());
        System.out.print("Is encrypted? ");
        System.out.println(reader.isEncrypted());
        System.out.println();
    }
}