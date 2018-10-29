package prj.coa.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Test3 {
	@SuppressWarnings("unused")
	private static void downloadFile(String huizhifiletemp)
	  {
	    String s = "http://td.scivic.com.cn:80/servlet/file.BeforeDownload?downloadType=backup&fileId=22417152";

	    BufferedInputStream bf = null;
	    FileOutputStream fos = null;
	    try
	    {
	      File sfile = createNewFile(huizhifiletemp);
	      URL url = new URL(s);
	      bf = new BufferedInputStream(url.openStream());
	      byte[] by = new byte[1048576];
	      int offset = 0;
	      boolean bn = true;
	      fos = new FileOutputStream(huizhifiletemp);
	      while ((offset = bf.read(by)) != -1)
	        fos.write(by, 0, offset);
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      try
	      {
	        if (fos != null) {
	          fos.close();
	        }
	        if (bf != null)
	          bf.close();
	      }
	      catch (Exception e1) {
	        System.out.print("关闭IO流出现错误！");
	      }
	    }
	    finally
	    {
	      try
	      {
	        if (fos != null) {
	          fos.close();
	        }
	        if (bf != null)
	          bf.close();
	      }
	      catch (Exception e1) {
	        System.out.print("关闭IO流出现错误！");
	      }
	    }
	  }
	public static File createNewFile(String fileNP) throws IOException
	  {
	    File file = new File(fileNP);
	    if ((file.exists()) && (file.isFile())) {
	      return file;
	    }
	    if (!file.exists())
	      file.mkdirs();
	    if (!file.isFile()) {
	      file.delete();
	      file.createNewFile();
	    }
	    return file;
	  }
	public static void main(String[] args) {
		String filepath = "E:\\fletest\\file";
		downloadFile(filepath);
	}
}
