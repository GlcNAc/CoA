package prj.coa.tools.prop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SysPropConfig {
	protected static Properties sysProp;
	private static SysPropConfig sysConfig = null;
	protected static boolean sysProploaded;
	protected InputStream in;

	public SysPropConfig() {
		init();
	}

	private void init() {
		sysProp = new Properties();
		sysProploaded = false;
		sysConfig = this;
	}

	public static synchronized SysPropConfig getInstance() {
		if (sysConfig == null)
			sysConfig = new SysPropConfig();
		return sysConfig;
	}

	public String getSysPropConfig(String key, String defaultvalue) {
		return getSysProperties().getProperty(key, defaultvalue);
	}

	public Properties getSysProperties() {
		if (!(sysProploaded))
			loadSysProperties();
		return sysProp;
	}

	public void loadSysProperties() {
		this.in = getClass().getResourceAsStream("/sys.properties");
		try {
			sysProp.load(this.in);
			sysProploaded = true;
		} catch (IOException e) {
			System.err.println("找不到文件 sysConfig.properties");
			return;
		}
	}

	public static void main(String[] args) {
		System.out.println(SysPropConfig.getInstance().getSysPropConfig("test", "XXXX"));
	}
}
