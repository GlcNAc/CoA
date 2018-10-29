package prj.coa.util.jacob;

import java.io.File;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import prj.coa.util.lang.FileUtil;

public class WordUtil {
	/**
	 * @author sxd1
	 * @param paths
	 * @param savepaths
	 * @see 完成从word文档向HTML的转换
	 */
	@SuppressWarnings("unused")
	public static void change(String paths, String savepaths) {
		File[] lists = FileUtil.getFileList(paths);
		String pathss = new String("");
		// 对当前目录下面所有文件进行检索
		for (int i = 0; i < lists.length; i++) {
			if (lists[i].isFile()) {
				String filename = lists[i].getName();
				// 判断是否为doc文件
				if (filename.endsWith("doc")) {
					ActiveXComponent app = new ActiveXComponent(
							"Word.Application");// Excel.Application
					// 启动word
					String docpath = paths + filename;
					String htmlpath = savepaths
							+ filename.substring(0, (filename.length() - 4));
					String inFile = docpath;
					// 要转换的word文件
					String tpFile = htmlpath;
					// HTML文件
					boolean flag = false;
					try {
						app.setProperty("Visible", new Variant(false));
						// 设置word不可见
						Dispatch docs = app.getProperty("Documents").toDispatch();
						Dispatch doc = Dispatch.invoke(docs, "Open",
								Dispatch.Method, new Object[] { inFile },
								new int[1]).toDispatch();
						// 打开word文件
						Dispatch.invoke(doc, "SaveAs",
								Dispatch.Method, new Object[] { tpFile,
										new Variant(8) }, new int[1]);
						// 作为html格式保存到临时文件
						Variant f = new Variant(false);
						Dispatch.invoke(doc, "Close",
								Dispatch.Method, new Object[] { new Variant(
										false) }, new int[1]);
						flag = true;
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						app.invoke("Quit", new Variant[] {});
					}
					System.out.println("转化完毕！");
				}
			} else {
				pathss = paths;
				// 进入下一级目录
				pathss = pathss + lists[i].getName() + "\\";
				// 递归遍历所有目录
				change(pathss, savepaths);
			}
		}
	}

	public static void main(String[] args) {
		String paths = "E:\\test";
		String savepaths = "E:\\result";
		change(paths, savepaths);
	}
}
