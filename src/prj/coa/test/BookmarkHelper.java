package prj.coa.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class BookmarkHelper {
	private static int line = -1;
	private List<Map> data = null;

	public void setOutlines(String oldFile, String newFile, String outlinesFile) {
		try {
			// create a reader for a certain document
			PdfReader reader = new PdfReader(oldFile);

			// we create a stamper that will copy the document to a new file
			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(newFile));

			// read the outlines structure file
			readOutlinesFile(outlinesFile);

			// set the outlines
			stamp.setOutlines(createOutlines());

			// closing PdfStamper will generate the new PDF file
			stamp.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	public List<Map<String, Object>> createOutlines() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = null;
		int lastLine = line + 1;

		do {
			line++;

			map = new HashMap<String, Object>();
			map.put("Title", (data.get(line)).get("Title"));
			map.put("Action", "GoTo");
			map.put("Page", (data.get(line)).get("Page"));

			if (hasChildren(line)) {
				map.put("Kids", createOutlines());
			}

			list.add(map);
		} while (hasSubling(lastLine));

		return list;
	}

	/*
	 * List item: String[] = {INDEX, PAGE, TITLE}
	 */
	private void readOutlinesFile(String file) {
		List<Map> lines = new ArrayList<Map>();
		Map<String, String> map = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));

			String line = null;
			String subStr = null;
			int tabIndex = 0;
			int spaceIndex = 0;

			while ((line = reader.readLine()) != null) {
				map = new HashMap<String, String>();

				tabIndex = line.indexOf("/t");
				tabIndex = (tabIndex >= 0) ? tabIndex : Integer.MAX_VALUE;
				spaceIndex = line.indexOf(" ");
				spaceIndex = (spaceIndex >= 0) ? spaceIndex : Integer.MAX_VALUE;
				subStr = line.substring(0, tabIndex < spaceIndex ? tabIndex : spaceIndex);
				map.put("Index", subStr);
				line = line.substring(subStr.length() + 1).trim();

				tabIndex = line.indexOf("/t");
				tabIndex = (tabIndex >= 0) ? tabIndex : Integer.MAX_VALUE;
				spaceIndex = line.indexOf(" ");
				spaceIndex = (spaceIndex >= 0) ? spaceIndex : Integer.MAX_VALUE;
				subStr = line.substring(0, tabIndex < spaceIndex ? tabIndex : spaceIndex);
				map.put("Page", subStr);

				map.put("Title", line.substring(subStr.length() + 1).trim());

				lines.add(map);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.data = lines;
	}

	private boolean hasSubling(int lastLine) {
		if (line >= data.size() - 1)
			return false;

		String lastIndexStr = (String) (data.get(lastLine)).get("Index");
		String currIndexStr = (String) (data.get(line + 1)).get("Index");

		return (lastIndexStr.length() == currIndexStr.length());
	}

	private boolean hasChildren(int line) {
		if (line >= data.size() - 1)
			return false;

		String currIndexStr = (String) (data.get(line)).get("Index");
		String nextIndexStr = (String) (data.get(line + 1)).get("Index");

		if (line + 1 < data.size() && currIndexStr.length() < nextIndexStr.length())
			return true;

		return false;
	}
}
