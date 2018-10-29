package prj.coa.util.poi.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

import prj.coa.util.lang.FileUtil;

public class WordUtil {

	private static final String encoding = "UTF-8";

	public static List<String> readLine(Table tb, int rowNum, int cellsNum) {
		List<String> line = new ArrayList<String>();
		for (int i = 0; i < cellsNum; i++) {
			String valueAt = "";
			valueAt = getValueAt(tb, rowNum, i);
			line.add(valueAt);
		}
		return line;
	}

	public static List<String> readLine(Table tb, int rowNum, int[] colNums) {
		List<String> line = new ArrayList<String>();
		for (int colNum : colNums) {
			String valueAt = "";
			valueAt = getValueAt(tb, rowNum, colNum);
			line.add(valueAt);
		}
		return line;
	}

	public static List<String> readLine(XWPFTable tb, int rowNum, int[] colNums) {
		List<String> line = new ArrayList<String>();
		for (int colNum : colNums) {
			String valueAt = "";
			valueAt = getValueAt(tb, rowNum, colNum);
			line.add(valueAt);
		}
		return line;
	}

	public static String getValueAt(Table tb, int rowNum, int colNum) {
		String value = "";
		TableCell td = tb.getRow(rowNum).getCell(colNum);
		value = td.text().trim();
		return value;
	}

	public static List<String> readLine(XWPFTable tb, int rowNum, int cellsNum) {
		List<String> line = new ArrayList<String>();
		for (int i = 0; i < cellsNum; i++) {
			String valueAt = "";
			valueAt = getValueAt(tb, rowNum, i);
			line.add(valueAt);
		}
		return line;
	}

	public static String getValueAt(XWPFTable tb, int rowNum, int colNum) {
		String value = "";
		XWPFTableCell cell = tb.getRow(rowNum).getCell(colNum);
		value = cell.getText().trim();
		return value;
	}

	public static String convert2Html(String wordPath)
			throws FileNotFoundException, TransformerException, IOException,
			ParserConfigurationException {
		if (wordPath == null || "".equals(wordPath))
			return "";
		File file = new File(wordPath);
		if (file.exists() && file.isFile())
			return convert2Html(new FileInputStream(file));
		else
			return "";
	}

	public static String convert2Html(InputStream is)
			throws TransformerException, IOException,
			ParserConfigurationException {
		HWPFDocument wordDocument = new HWPFDocument(is);
		WordToHtmlConverter converter = new WordToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.newDocument());

		// 添加图片前缀，以防图片重复覆盖
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		final String prefix = sdf.format(new Date());

		converter.setPicturesManager(new PicturesManager() {
			@Override
			public String savePicture(byte[] content, PictureType pictureType,
					String suggestedName, float widthInches, float heightInches) {
				return prefix + "_" + suggestedName;
			}
		});
		converter.processDocument(wordDocument);

		List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
		if (pics != null) {
			for (Picture pic : pics) {
				try {
					pic.writeImageContent(new FileOutputStream("E:\\test\\"
							+ prefix + "_" + pic.suggestFullFileName()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		StringWriter writer = new StringWriter();

		Transformer serializer = TransformerFactory.newInstance()
				.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, encoding);
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(new DOMSource(converter.getDocument()),
				new StreamResult(writer));
		writer.close();
		return writer.toString();
	}

	public static void main(String[] args) {
		String filepath = "E:\\test\\V2-慧智图档系统业务文档.doc";
		String topath = "E:\\test\\test.html";

		try {
			String htmlStr = convert2Html(filepath);
			FileUtil.writeFile(htmlStr, topath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
}
