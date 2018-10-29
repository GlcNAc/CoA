package prj.coa.tools.db;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
 
import java.util.HashMap;
import java.util.Map;
 
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	
	
	private ComboPooledDataSource cpds = null;
	
	private static Map<String,ConnectionFactory> bufConfigFactory = new  HashMap<String, ConnectionFactory>();
	
	private static Object lock =new Object();
	private final C3p0Config c3p0Config ;
	private void init(C3p0Config config) throws java.sql.SQLException {
		cpds = new ComboPooledDataSource();
		InputStream inStream = null;
		try {
			cpds.setDriverClass(config.getDriverClass());
			cpds.setJdbcUrl(config.getJdbcUrl());
			cpds.setPassword(config.getPassWord());
			cpds.setUser(config.getUserName());
			cpds.setLoginTimeout(1800);
			cpds.setMaxStatements(50);
			cpds.setMaxPoolSize(50);
			cpds.setMinPoolSize(5);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			throw new SQLException("sys.properties not found or "+e.getMessage());
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (java.io.IOException ioe) {
				}
			}
		}
	}

	

	private ConnectionFactory(C3p0Config c3p0Config) {
		this.c3p0Config = c3p0Config;
	}

	
	public Connection getConnection() throws SQLException {
		if (cpds == null) {
			synchronized (this) {
				if (cpds == null)
					this.init(c3p0Config);
			}
		}
		Connection con =  cpds.getConnection();
		return con;
	}

	public static ConnectionFactory getInstance(String configName) throws SQLException {
		ConnectionFactory factory = bufConfigFactory.get(configName);
		if (factory == null) {
			synchronized (lock) {
				if (factory == null){
					factory = new ConnectionFactory(ConfigFactory.createConfig(configName));
					bufConfigFactory.put(configName, factory);
				}
			}
		}
		return factory;
	}
}
