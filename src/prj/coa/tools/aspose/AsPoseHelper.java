package prj.coa.tools.aspose;

import java.io.InputStream;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

public class AsPoseHelper {
	public static boolean getLicense() {
		boolean result = false;
		try {
			InputStream is = License.class.getResourceAsStream("/com/aspose/license.xml");
			License aposeLic = new License();
			aposeLic.setLicense(is);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 将word文档转换成pdf
	public static Boolean GetPdfFromWord(String soursefilepath, String outpdfpath) {

		try {
			// 验证License
			if (!getLicense()) {
				return false;
			}
			// 读取word文档
			Document doc = new Document(soursefilepath);
			doc.save(outpdfpath, SaveFormat.PDF);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static void main(String[] args) {
		String sourceFile = "D:\\1.docx";
		String outFile = "D:\\2.pdf";
		GetPdfFromWord(sourceFile, outFile);
	}
}