package prj.coa.tools.db;

import java.sql.SQLException;

public class ConfigFactory {
	
	public static ConfigFactory cf =new ConfigFactory();
	public static PropUtil pUtil = new PropUtil();
	
	public static C3p0Config createConfig(String configName) throws SQLException{		 
		
			return cf.initDb(configName);
			
	}
	
	
	private C3p0Config initDb(String configName) throws SQLException{
		C3p0Config c3poConfig =new C3p0Config();
		c3poConfig.setDriverClass(pUtil.getProperties(configName + ".driver"));
		c3poConfig.setJdbcUrl(pUtil.getProperties(configName + ".url"));
		c3poConfig.setUserName(pUtil.getProperties(configName + ".user"));
		c3poConfig.setPassWord(pUtil.getProperties(configName + ".password"));
		return c3poConfig;
	}
		
}
