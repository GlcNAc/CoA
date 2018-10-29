package prj.coa.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHandleTest {
	public boolean isInited = false;
	private static String FILE_NAME = "data2.obj";
	private String FILE_PATH;
	
	public FileHandleTest() {
		FILE_PATH = this.getClass().getResource("").getPath() + File.separator + FILE_NAME;
		System.out.println(FILE_PATH);
	}
	
	public Map<Integer, FileDataTest> getDataMap() throws SQLException{
		Map<Integer, FileDataTest> rMap = new HashMap<Integer, FileDataTest>();
		List<Map<String, Object>> rList = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("common_id", 10201);
		dataMap.put("pic_name", "pic_name10201c");
		rList.add(dataMap);
		dataMap = new HashMap<String, Object>();
		dataMap.put("common_id", 10202);
		dataMap.put("pic_name", "pic_name10202b");
		rList.add(dataMap);
		for (Map<String, Object> row : rList) {
			FileDataTest fd = new FileDataTest();
			fd.setRow(row);
			int common_id = 0;
			try {
				common_id = Integer.parseInt(String.valueOf(row.get("common_id")));
			} catch (Exception e) {
			}
			rMap.put(common_id, fd);
		}
		return rMap;
	}
	
	public void saveData(Map<Integer, FileDataTest> rows){		
		try {			
			ObjectOutputStream out = new ObjectOutputStream(new java.util.zip.GZIPOutputStream(new FileOutputStream(FILE_PATH)));
			out.writeObject(rows);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public Object readData(){
		try {
			return new ObjectInputStream(new java.util.zip.GZIPInputStream(new FileInputStream(FILE_PATH))).readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public FileDataTest readDataById(int cid){
		Object obj = readData();
		if(obj!=null){
			Map<Integer, FileDataTest> rows = (Map<Integer, FileDataTest>)obj;
			return rows.get(cid);
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	public void initDataFile(){
		if(isInited){
			return;
		}
		try {
			Map<Integer, FileDataTest> rows = getDataMap();
			//saveData(rows);
			isInited = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		FileHandleTest ft = new FileHandleTest();
		ft.initDataFile();
		int cid = 10201;
		FileDataTest fd = ft.readDataById(cid);
		if(fd!=null){
			System.out.println(String.valueOf(fd.getRow().get("pic_name")));
		}else{
			System.out.println("no Data");
		}
	}
	
}
