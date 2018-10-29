
package prj.coa.util.poi.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	protected static final String ENCODE_WHEN_READING = "GBK";

	public static Workbook readExcel(File file) {
		try {
			InputStream is = new FileInputStream(file);
			return readExcel(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("文件找不到");
		}
		return null;
	}

	/**
	 * 功能：通过输入流读取Excel文件对象,返回一个Workbook对象
	 * 
	 * @param is
	 * @return
	 */
	public static Workbook readExcel(InputStream is) {
		if (!is.markSupported()) {
			is = new PushbackInputStream(is, 8);
        }
        try {
			if (POIFSFileSystem.hasPOIFSHeader(is)) {
			    return new HSSFWorkbook(is);
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
        try {
			if (POIXMLDocument.hasOOXMLHeader(is)) {
			    return new XSSFWorkbook(OPCPackage.open(is));
			}
		} catch (InvalidFormatException | IOException e2) {
			e2.printStackTrace();
		}

		return null;
	}


	/**
	 * 功能：直接通过文件路径获取Excel文件对象，返回一个Workbook对象
	 * 
	 * @param path
	 * @return
	 */
	public static Workbook readExcel(String path) {
		try {
			File file = new File(path);
			return readExcel(file);
		} catch (Exception e) {
			// log4gm.error(e.toString());
		}
		return null;
	}
	

	
	/**
	 * 功能：获取表标题某字段的位置,即所在列号
	 * 
	 * @param st
	 * @param strHeadColContents
	 * @return
	 */
	public static int findColIndex(Sheet st, String strHeadColContents, int headRowNum) {
		int intIndex = 0;
		int cellsNum = st.getRow(0).getPhysicalNumberOfCells();
		for (int i = 0; i < cellsNum; i++) {
			String strCon = getValueAt(st, headRowNum, i);
			if (strHeadColContents.equals(strCon)) {
				intIndex = i;
			}
		}
		return intIndex;
	}
	
	public static int findColIndex(Sheet st, String strHeadColContents) {
		return findColIndex(st, strHeadColContents, 0);
	}
	
	/**
	 * 功能：获取某单元格的内容
	 * 
	 * @param st
	 * @param rowIndex
	 *            行号数
	 * @param colIndex
	 *            列号数
	 * @return
	 */
	public static String getValueAt(Sheet st, int rowIndex, int colIndex) {
		String strValueAt = "";
		Cell cell = st.getRow(rowIndex).getCell(colIndex);
		if (null != cell) {
			strValueAt = getCellValueStr(cell);
		}

		return strValueAt;
	}
	
	public static String getCellValueStr(Cell cell) {
		String valueStr = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // 数字
			valueStr = String.valueOf(cell.getNumericCellValue()).trim();
			if (DateUtil.isCellDateFormatted(cell)) {
				double d = cell.getNumericCellValue();
				Date date = DateUtil.getJavaDate(d);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				valueStr = sdf.format(date);
			} else {
				if (valueStr.endsWith(".0")) {
					valueStr = valueStr.substring(0, valueStr.length() - 2);
				}
			}
			break;
		case Cell.CELL_TYPE_STRING: // 字符串
			valueStr = cell.getStringCellValue().trim();
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			valueStr = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			valueStr = String.valueOf(cell.getCellFormula()).trim();
			break;
		case Cell.CELL_TYPE_BLANK: // 空值
			break;
		case Cell.CELL_TYPE_ERROR: // 故障
			break;
		default:
			System.out.print("未知类型   ");
			break;
		}
		return valueStr;
	}
	
	/**
	 * 获取某一行 的 值
	 * 
	 * @param st
	 * @param rowNum
	 * @param colNum
	 *            列数 0~colNum
	 * @return
	 */
	public static List<String> readLine(Sheet st, int rowNum, int colNum){
		List<String> line = new ArrayList<String>();		
		for (int i = 0; i < colNum; i++) {  
			String valueAt = "";
			valueAt = ExcelUtil.getValueAt(st, rowNum, i);			
			line.add(valueAt);
		}
		return line;
	}
	
	/**
	 * 获取某一行 的 值
	 * 
	 * @param st
	 * @param rowNum
	 * @param colNums
	 *            列数
	 * @return
	 */
	public static List<String> readLine(Sheet st, int rowNum, int[] colNums){
		List<String> line = new ArrayList<String>();
		for (int colNum : colNums) {
			String valueAt = "";
			valueAt = ExcelUtil.getValueAt(st, rowNum, colNum);			
			line.add(valueAt);
		}
		return line;
	}
	
	
	public static synchronized byte[] fill(File templateFile,
			Map<String, Object> dta) {
		if (templateFile == null) {
			throw new NullPointerException("excel 模板文件不能为空!");
		}

		if (!templateFile.isFile()) {
			throw new IllegalArgumentException("模板文件不能为目录，必须为文件!");
		}

		if (dta == null) {
			throw new IllegalArgumentException("填充数据不能为空!");
		}

		ByteArrayOutputStream output = null;
		FileInputStream input = null;
		Workbook workbook = null;
		try {
			input = new FileInputStream(templateFile);

			if (templateFile.getName().toLowerCase().endsWith(".xls"))
				workbook = new HSSFWorkbook(input);
			else {
				workbook = new XSSFWorkbook(input);
			}

			output = new ByteArrayOutputStream();
			Sheet sheet = workbook.getSheetAt(0);
			sheet.setForceFormulaRecalculation(true);
			
			int startRow = 0;
			Map<String, Object> map = getTemplateRow(sheet);
			List<?> templateCols = (List<?>) map.get("templateCols");
			try {
				startRow = (Integer) map.get("startRow");
			} catch (Exception e) {
			}
			fill(sheet, dta, startRow, templateCols);
			workbook.write(output);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException localIOException1) {
				}
		}
		return output.toByteArray();

	}

	private static void fill(Sheet sheet, Map<String, Object> dta,
			int startRow, List<?> templateCols) {
		List<?> dataList = (List<?>) dta.get("d");
		int size = dataList.size();
		int colSize = templateCols.size();
		for (int i = 0; i < size; i++) {
			int rowIndex = startRow + i;
			Row row = sheet.getRow(rowIndex);
			if (row==null) {
				row = sheet.createRow(rowIndex);
			}
			for (int j = 0; j < colSize; j++) {
				Cell cell = row.getCell(j);
				if(cell == null ){
					cell = row.createCell(j);
				}
				String col = String.valueOf(templateCols.get(j));
				Object value = new Object();
				if (col.startsWith("$d.")) {
					int index = col.indexOf(".");
					String key = col.substring(index + 1);
					Map<?, ?> data = (Map<?, ?>) dataList.get(i);
					value = data.get(key.toUpperCase());
				} else {
					String key = col.substring(1);
					value = dta.get(key.toUpperCase());
				}
				write(cell, value);
			}
		}
	}

	private static void write(Cell cell, Object value) {
		if (cell == null) {
			return;
		}
		if (value == null) {
			cell.setCellValue("");
			return;
		}
		if (value instanceof Number)
			cell.setCellValue(((Number) value).doubleValue());
		else if (value instanceof Date)
			cell.setCellValue((Date) value);
		else if (value instanceof Boolean)
			cell.setCellValue(((Boolean) value).booleanValue());
		else
			cell.setCellValue(value.toString());
	}

	private static Map<String, Object> getTemplateRow(Sheet sheet) {
		List<String> templateCols = new ArrayList<String>();
		int startRow = 0;
		int rowCount = sheet.getLastRowNum();
		for (int i = 0; i <= rowCount; i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			int cellCount = row.getLastCellNum();
			for (int j = 0; j < cellCount; j++) {
				Cell cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				String content = cell.toString();
				if (content == null) {
					continue;
				}
				if (content.startsWith("$")) {
					templateCols.add(j, content);
					startRow = i;
				}
			}
			if (startRow > 0) {
				break;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startRow", startRow);
		map.put("templateCols", templateCols);
		
		return map;
	}
	
	
	public static void main(String[] args) {
		//String path = "E:\\test\\111.xlsx";
		//Workbook wb = readExcel(path);
		//Sheet st = wb.getSheetAt(0);
		//int[] colNums = {0,1,3};
		//System.out.println(readLine(st, 2, colNums));
		
		List<String[]> datas = new ArrayList<String[]>();
		
		String[] dta1 = { "cl1","te21","234"};
		datas.add(dta1);
		String[] dta2 = { "vl1","ve21","2v4"};
		datas.add(dta2);
		
		String file = "E:\\12.xls";
		ExcelUtil eu = new ExcelUtil();
		eu.expExcel(datas, file);
	}
	
	
	public void expExcel(List<String[]> datas, String file){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		int rownum = datas.size();
		for (int i = 0; i < rownum; i++) {
			String[] dta = datas.get(i);
			HSSFRow row = sheet.createRow(i);
			for (int j = 0; j < dta.length; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(dta[j]);
			}
		}
		
		try {
			FileOutputStream fOut = new FileOutputStream(file);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
