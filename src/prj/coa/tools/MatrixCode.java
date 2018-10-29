package prj.coa.tools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class MatrixCode {

	public static void main(String[] args) {
		try {
            
		     String content = "测试. http://fivehz:8080/";
		     String path = "D:/testImage";
		     
		     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		     
		     Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		     BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
		     File file1 = new File(path,"test.jpg");
		     MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
		     
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
	}
}
