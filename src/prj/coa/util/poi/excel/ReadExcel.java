package prj.coa.util.poi.excel;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ReadExcel {
	
	private static List<String> headNameList = new ArrayList<String>();
	private static List<String> record = new ArrayList<String>();
	private static List<List<String>> recordList = new ArrayList<List<String>>();
	
	public static void load(String path, int sheetNum, int headNum){
		Workbook wb = ExcelUtil.readExcel(path);
		Sheet st = wb.getSheetAt(sheetNum);
		int rowsNum = st.getPhysicalNumberOfRows(); // 行数
		int cellsNum = st.getRow(0).getPhysicalNumberOfCells(); // 列数
		if(headNum < -1){
			headNum = -1;
		}
		if(headNum >=0 ){
			headNameList = ExcelUtil.readLine(st, headNum, cellsNum);
		}
		for (int i = headNum + 1; i < rowsNum; i++) { // 获取记录行
			record = ExcelUtil.readLine(st, i, cellsNum);
			recordList.add(record);
		}
	}
	
	public static void load(String path){
		load(path, 0, 0);
	}
	
	public static void main(String[] args) {
		String path = "E:\\test\\111.xlsx";
		load(path);
		System.out.println(headNameList);
		System.out.println(headNameList.size());
		System.out.println(recordList.size());
	}
	
}
