package prj.coa.util.poi.word;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class ReadWord {
	private static boolean isDoc;
	private static Object tableObject;
	private static int rowsNum;
	private static int cellsNum;
	private static int startNum;
	private static List<String> record = new ArrayList<String>();

	public static void load(String path) {
		load(path, "序号");
	}

	public static void load(String path, String headXh) {
		File file = new File(path);
		load(file, headXh);
	}

	public static void load(File file) {
		load(file, "序号");
	}

	public static void load(File file, String headXh) {
		String path = file.getPath();
		if (path.toLowerCase().endsWith(".doc")) {
			isDoc = true;
		}
		try {
			if (isDoc) {
				FileInputStream input = null;
				input = new FileInputStream(file);
				POIFSFileSystem pfs = new POIFSFileSystem(input);
				HWPFDocument hwpf = new HWPFDocument(pfs);
				Range range = hwpf.getRange();// 得到文档的读取范围
				TableIterator it = new TableIterator(range);
				// 迭代文档中的表格
				boolean getTheRightTable = false;

				while (it.hasNext()) {
					Table tb = it.next();
					for (int i = 0; i < 10; i++) {
						TableRow tr = tb.getRow(i);
						TableCell td0 = tr.getCell(0);
						String value = td0.text().trim();
						value = value.replaceAll("[\\t\\n\\r]", "");
						if (value.equals(headXh)) {
							tableObject = tb;
							rowsNum = tb.numRows();
							cellsNum = tr.numCells();
							setStartNum(i);
							getTheRightTable = true;
							break;
						}
					}
					if (getTheRightTable) {
						break;
					}
				}
			} else {
				String filePath = file.getPath();
				OPCPackage pack = POIXMLDocument.openPackage(filePath);
				XWPFDocument doc = new XWPFDocument(pack);
				Iterator<XWPFTable> it = doc.getTablesIterator();
				// 迭代文档中的表格
				boolean getTheRightTable = false;

				while (it.hasNext()) {
					XWPFTable tb = (XWPFTable) it.next();
					for (int i = 0; i < 10; i++) {
						XWPFTableRow tr = tb.getRow(i);

						XWPFTableCell td0 = tr.getCell(0);
						String value = td0.getText().trim();

						if (value.equals(headXh)) {
							tableObject = tb;
							rowsNum = tb.getNumberOfRows();
							cellsNum = tr.getTableCells().size();
							setStartNum(i);
							getTheRightTable = true;
							break;
						}
					}
					if (getTheRightTable)
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static List<String> readHead(int headNum) {
		List<String> headNameList = new ArrayList<String>();
		if (headNum < -1) {
			headNum = -1;
		}
		if (headNum >= 0) {
			if (isDoc) {
				headNameList = WordUtil.readLine((Table) tableObject, headNum,
						cellsNum);
			} else {
				headNameList = WordUtil.readLine((XWPFTable) tableObject,
						headNum, cellsNum);
			}
		}
		return headNameList;
	}

	public static List<List<String>> readLine(int rowNumStart, int rowNumEnd) {
		List<List<String>> recordList = new ArrayList<List<String>>();
		for (int i = rowNumStart - 1; i < rowNumEnd; i++) { // 获取记录行
			if (isDoc) {
				record = WordUtil.readLine((Table) tableObject, i, cellsNum);
			} else {
				record = WordUtil
						.readLine((XWPFTable) tableObject, i, cellsNum);
			}

			recordList.add(record);
		}
		return recordList;
	}

	public static List<List<String>> readLineBreak(int rowNumStart,
			int rowNumEnd) {
		List<List<String>> recordList = new ArrayList<List<String>>();
		for (int i = rowNumStart - 1; i < rowNumEnd; i++) {
			if (isDoc) {
				if ((WordUtil.getValueAt((Table) tableObject, i, 0) == null)
						|| ((WordUtil.getValueAt((Table) tableObject, i, 0)
								.length() == 0) && (WordUtil.getValueAt((Table) tableObject, i, 1).length() == 0))) {
					break;
				}
				record = WordUtil.readLine((Table) tableObject, i, cellsNum);
			} else {
				if ((WordUtil.getValueAt((XWPFTable) tableObject, i, 0) == null)
						|| ((WordUtil.getValueAt((XWPFTable) tableObject, i, 0)
								.length() == 0) && (WordUtil.getValueAt((XWPFTable) tableObject, i, 1).length() == 0))) {
					break;
				}
				record = WordUtil
						.readLine((XWPFTable) tableObject, i, cellsNum);
			}

			recordList.add(record);
		}
		return recordList;
	}

	public static void main(String[] args) {
		String path = "E:\\jn.doc";
		File file = new File(path);
		load(file);
		//readHead(0);
		//List<List<String>> recordList = readLine(1, 3);
		//System.out.println(recordList);
		List<String> headNameList = null;
        for (int i = 0; i < 100; i++) {
          if ((headNameList != null) && (headNameList.size() > 0)) break;
          headNameList = ReadWord.readHead(i);
        }
	}

	public static boolean isDoc() {
		return isDoc;
	}

	public static void setDoc(boolean isDoc) {
		ReadWord.isDoc = isDoc;
	}

	public static Object getTableObject() {
		return tableObject;
	}

	public static void setTableObject(Object tableObject) {
		ReadWord.tableObject = tableObject;
	}

	public static int getRowsNum() {
		return rowsNum;
	}

	public static void setRowsNum(int rowsNum) {
		ReadWord.rowsNum = rowsNum;
	}

	public static int getCellsNum() {
		return cellsNum;
	}

	public static void setCellsNum(int cellsNum) {
		ReadWord.cellsNum = cellsNum;
	}

	public static int getStartNum() {
		return startNum;
	}

	public static void setStartNum(int startNum) {
		ReadWord.startNum = startNum;
	}

}
