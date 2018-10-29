package prj.coa.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * 类描述：获取页面源码
 * 
 * @version: 1.0
 * @author: 吴国栋
 * @version: 2014年1月17日 上午8:30:24 编写部门 ECM事业部 版权所有 杭州慧智电子科技有限公司
 */
public class HtmlParser {
	public static final String SURL_STRING = "mail.163.com";

	public static String readURLToString(URL source, String encoding) {
		InputStream input = null;
		try {
			input = source.openStream();
			return IOUtils.toString(input, encoding);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(input);
		}

		return null;

	}

	public static String readURLToString(String sourceUrl, String encoding) {
		try {
			if (!sourceUrl.toLowerCase().startsWith("http://")) {
				sourceUrl = "http://" + sourceUrl;
			}
			URL url = new URL(sourceUrl);
			return readURLToString(url, "utf-8");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static String getHtmlContent(URL url, String encode) {
		StringBuffer contentBuffer = new StringBuffer();

		int responseCode = -1;
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");// IE代理进行下载
			con.setConnectTimeout(60000);
			con.setReadTimeout(60000);
			// 获得网页返回信息码
			responseCode = con.getResponseCode();
			if (responseCode == -1) {
				System.out.println(url.toString()
						+ " : connection is failure...");
				con.disconnect();
				return null;
			}
			if (responseCode >= 400) // 请求失败
			{
				System.out.println("请求失败:get response code: " + responseCode);
				con.disconnect();
				return null;
			}

			InputStream inStr = con.getInputStream();
			InputStreamReader istreamReader = new InputStreamReader(inStr,
					encode);
			BufferedReader buffStr = new BufferedReader(istreamReader);

			String str = null;
			while ((str = buffStr.readLine()) != null)
				contentBuffer.append(str);
			inStr.close();
		} catch (IOException e) {
			e.printStackTrace();
			contentBuffer = null;
			System.out.println("error: " + url.toString());
		} finally {
			con.disconnect();
		}
		return contentBuffer.toString();
	}

	public static String getHtmlContent(String url, String encode) {
		if (!url.toLowerCase().startsWith("http://")) {
			url = "http://" + url;
		}
		try {
			URL rUrl = new URL(url);
			return getHtmlContent(rUrl, encode);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String argsp[]) {
		//System.out.println(getHtmlContent(SURL_STRING, "utf-8"));
		String url = "http://fivehz:8088";
		System.out.println(readURLToString(url, "utf-8"));
	}
}
