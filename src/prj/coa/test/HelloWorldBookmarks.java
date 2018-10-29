package prj.coa.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.SimpleBookmark;

public class HelloWorldBookmarks {
	public static void main ( String [] args ) throws Exception {
	     PdfReader reader = new PdfReader ( "D:\\pdf\\504$30404$4238321$1497607903865.pdf" ) ;
	     List<?> list = SimpleBookmark.getBookmark ( reader ) ;
	     //System.out.println(list);
	     //[{Action=GoTo, Page=1 FitH 860, Title=地质工点 1-A3_HZ}, {Action=GoTo, Page=2 FitH 860, Title=地质工点 2-A3_HZ}, {Action=GoTo, Page=3 FitH 860, Title=路线纵断面图 1-A3_HZ}, {Action=GoTo, Page=4 FitH 860, Title=路线纵断面图 2-A3_HZ}, {Action=GoTo, Page=2 XYZ 0 856 null, Kids=[{Action=GoTo, Page=2 XYZ 0 856 null, Title=批注页面:第2页, Color=1 0 0}, {Action=GoTo, Page=3 XYZ 0 856 null, Title=批注页面:第3页, Color=1 0 0}], Title=批注页面, Color=1 0 0}]

	     for ( Iterator<?> i = list.iterator () ; i.hasNext () ; ) {
	       showBookmark (( Map<?, ?> ) i.next ()) ;
	     }
	   }

	   private static void showBookmark ( Map<?, ?> bookmark ) {
	     System.out.println ( bookmark.get ( "Title" )) ;
	     ArrayList<?> kids = ( ArrayList<?> ) bookmark.get ( "Kids" ) ;
	     if ( kids == null )
	       return ;
	     for ( Iterator<?> i = kids.iterator () ; i.hasNext () ; ) {
	       showBookmark (( Map<?, ?> ) i.next ()) ;
	     }
	   }
}
