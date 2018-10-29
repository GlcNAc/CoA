package prj.coa.tools.mutidown;

public class DownloadTest {
	public static void main(String[] args) {
		QSEngine engine = new QSEngine();
		String url = "http://ninehz:8785/edc/servlet/file.BeforeDownload?checkbox_file=4928436";
        engine.createDLTask(10, url, "d:", "4928436.pdf");
	}
}
