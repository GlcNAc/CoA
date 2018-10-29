package prj.coa.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestUtil {
	public TestBean loadTestInfo(String commonid){
		TestBean tBean = new TestBean();
		try{
			DBAccess db = new DBAccess();
			String sql = "select * from test where commonid=" + commonid;
			Connection con = db.getConnection();
			PreparedStatement stmt=con.prepareStatement(sql);	    
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
	    	  tBean.setPROJECTNUMBER1(rs.getString("PROJECTNUMBER1"));
	    	  tBean.setPROJECTNAME(rs.getString("PROJECTNAME"));
	    	  tBean.setPROJECTNUMBER(rs.getString("PROJECTNUMBER"));
	    	  tBean.setITEMNAME(rs.getString("ITEMNAME"));
	    	  tBean.setVERSION(rs.getString("VERSION"));
	    	  tBean.setEDITOR(rs.getString("EDITOR"));
	    	  tBean.setDWFFILE(rs.getString("DWFFILE"));
	    	  tBean.setCREATOR(rs.getString("CREATOR"));
	    	  tBean.setCOLLATOR(rs.getString("COLLATOR"));
	    	  tBean.setAUDITOR(rs.getString("AUDITOR"));
	    	  tBean.setREADER(rs.getString("READER"));
	    	  tBean.setSPECIALPERSON(rs.getString("SPECIALPERSON"));
	    	  tBean.setITEMPERSON(rs.getString("ITEMPERSON"));
			}
			db.closeAll(rs, stmt, con);
	    }catch (Exception e){
	      e.printStackTrace();
	    } 
		return tBean;	
	}
}
