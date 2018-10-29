/**
 * 
 */
package prj.coa.tools.word;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * @author Administrator
 * 
 */
public class LuceneWordTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String inputFile = "D:\\wgd\\test.doc";
		String outputFile = "D:\\wgd\\test.txt";
		LuceneWordTest.extraDoc(inputFile, outputFile);
	}

	/**
	 * 功能：把word文档转化为txt(html)格式的文档
	 * 
	 * @param inputFile
	 *            word文件的绝对路径
	 * @param outputFile
	 *            转化为txt文件后的绝对路径
	 */
	public static void extraDoc(String inputFile, String outputFile) {
		boolean flag = false;

		// 打开word的应用程序
		ActiveXComponent app = new ActiveXComponent("Word.Application");
		try {
			// 设置word不可见
			app.setProperty("Visible", new Variant(false));
			Dispatch doc1 = app.getProperty("Documents").toDispatch();
			Dispatch doc2 = Dispatch.invoke(
					doc1,
					"Open",
					Dispatch.Method,
					new Object[] { inputFile, new Variant(false),
							new Variant(true) }, new int[1]).toDispatch();
			// 作为txt格式，保存
			// 7为txt格式， 8为html格式
			Dispatch.invoke(doc2, "SaveAs", Dispatch.Method, new Object[] {
					outputFile, new Variant(7) }, new int[1]);
			// 关闭Word
			Variant f = new Variant(false);
			Dispatch.call(doc2, "Close", f);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			app.invoke("Quit", new Variant[] {});
		}
		if (flag == true) {
			System.out.println("Transformed Successfully");
		} else {
			System.out.println("Transformed failed");
		}
	}

}
