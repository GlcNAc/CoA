package prj.coa.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;

public class FoodqsPhotoDownload {
	/**
	 * 棣栭〉url
	 */
	private String indexUrl = null;
	/**
	 * 鏈湴涓嬭浇鍒扮洰褰�
	 */
	private File localFild = null;
	/**
	 * html椤甸潰鐨勭紪鐮�
	 */
	private String enc = null;
	/**
	 * url鐩綍
	 */
	private String urlFold = null;
	/**
	 * 瑕佷笅杞界殑鍥剧墖鏁�
	 */
	private int totalPics = 0;
	/**
	 * 宸蹭笅杞界殑鍥剧墖鏁�
	 */
	private int downloadPics = 0;
	/**
	 * 椤甸潰鍒嗘瀽瀹屾瘯锛屽彲浠ュ垎鏋愭槸鍚︿笅杞藉畬姣曚簡
	 */
	private boolean pageurlSetover = false;
	private long beginTimeMillis;

	/**
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		FoodqsPhotoDownload test = new FoodqsPhotoDownload(
				"http://www.sohu.com/",
				"娴嬭瘯鍥�", "c:/pics", "gb2312");
		test.begin();
	}

	/**
	 * 寮�濮嬩笅杞�
	 */
	public void begin() {
		beginTimeMillis = System.currentTimeMillis();
		new DownLoadPageAndDownloadPhoto(this.indexUrl, true).start();
	}

	public FoodqsPhotoDownload(String indexUrl, String title, String localFild,
			String enc) {
		this.indexUrl = indexUrl;
		urlFold = FilenameUtils.getFullPath(indexUrl);

		if (localFild.endsWith("/") || localFild.endsWith("\\")) {
			localFild = localFild.substring(0, localFild.length() - 1);
		}
		this.localFild = new File(localFild + File.separator + title);
		{
			if (!this.localFild.exists())
				this.localFild.mkdirs();
		}

		this.enc = enc;
	}

	/**
	 * 鍥犱负FileUtils涓嶆敮鎸佲�淪tring content =
	 * FileUtils.readFileToString(FileUtils.toFile(new
	 * URL("http://www.baidu.com")), "gb2312");鈥濇墍浠ユ坊鍔犱釜鏂规硶
	 * 
	 * @param source
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readURLToString(URL source, String encoding)
			throws IOException {
		InputStream input = source.openStream();
		try {
			return IOUtils.toString(input, encoding);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	/**
	 * 鑾峰緱鍏抽敭鐨則d鑺傜偣,id=zoom
	 * 
	 * @param content
	 * @return
	 */
	public Node getKeyTD(String content) {
		Parser parser = Parser.createParser(content, enc);
		HtmlPage page = new HtmlPage(parser);
		try {
			parser.visitAllNodesWith(page);
		} catch (ParserException e1) {
		}
		NodeList tdNode = page.getBody();
		NodeFilter tdfilter = new NodeAttributeFilter("id", "zoom",
				TableColumn.class);
		tdNode = tdNode.extractAllNodesThatMatch(tdfilter, true);
		if (tdNode != null && tdNode.size() > 0) {
			return tdNode.elementAt(0);
		}
		return null;
	}

	/**
	 * 鑾峰緱鏌愪釜鍥剧墖鐨剈rl
	 * 
	 * @param content
	 * @param tdid
	 * @return
	 */
	public String getImgUrl(Node keyTDNode) {
		NodeList imgChildNodelist = keyTDNode.getChildren();
		NodeFilter imgChildFilter = new TagNameFilter("IMG");
		imgChildNodelist = imgChildNodelist.extractAllNodesThatMatch(
				imgChildFilter, true);
		if (imgChildNodelist.size() > 0) {
			ImageTag img = (ImageTag) imgChildNodelist.elementAt(0);
			return img.getImageURL();
		}

		return null;
	}

	/**
	 * 鑾峰緱棣栭〉闄ゅ鐨勫叾瀹冮〉闈㈢殑閾炬帴
	 * 
	 * @param keyTDNode
	 * @return
	 */
	@SuppressWarnings("serial")
	public NodeList getOtherPageUrl(Node keyTDNode) {
		NodeList childNodelist = keyTDNode.getChildren();
		NodeFilter childfilter = new NodeFilter() {
			public boolean accept(Node node) {
				if (node instanceof LinkTag) {
					String txt = ((LinkTag) node).getStringText();// id澶у皬鍐欐棤鎵�璋�
					if (txt.length() >= 3 && !"[1]".equals(txt)) {
						txt = txt.replace("[", "");
						txt = txt.replace("]", "");
						try {
							int i = Integer.parseInt(txt);
							if (i > 0) {
								return true;
							}
						} catch (NumberFormatException e) {
						}
					}
				}
				return false;
			}
		};
		return childNodelist.extractAllNodesThatMatch(childfilter, true);
	}

	// implements Runnable
	// 涓�鏃︽浛鎹㈡垚Runnable涔嬪悗锛� 鍦═estURLThread鐨剅un閲岄潰锛�
	// sendMSsg.notify()灏嗕笉鑳藉強鏃跺敜閱扴endMsg鐨剋ait(),鍙兘闈� this.wait(30*1000);鏉ラ��鍑虹瓑寰呫��
	class PhotoDownload extends Thread {
		String source = null;

		public PhotoDownload(String source) {
			this.source = source;
		}

		public void run() {
			try {
				File destination = null;
				String name = null;
				URL url = null;
				url = new URL(source);
				name = FilenameUtils.getName(source);
				destination = new File(localFild, name);
				System.out.println("姝ｅ湪涓嬭浇鍥剧墖 " + name + "鈥︹��");
				FileUtils.copyURLToFile(url, destination);
				System.out.println("鍥剧墖涓嬭浇瀹屾垚 " + name + "銆恛k銆�");
				downloadPics++;
				if (pageurlSetover && downloadPics == totalPics) {
					beginTimeMillis = System.currentTimeMillis()
							- beginTimeMillis;
					System.out.println("銆愬叏閮ㄣ�戝浘鐗囦笅杞藉畬鎴愶紒鑰楁椂"
							+ (beginTimeMillis / 1000) + "s");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("serial")
	class NodeAttributeFilter implements NodeFilter {
		String attributeName = null;
		String value = null;
		Class tagType = null;

		public NodeAttributeFilter(String attributeName, String value) {
			this.attributeName = attributeName;
			this.value = value;
		}

		public NodeAttributeFilter(String attributeName, String value,
				Class tagType) {
			this.attributeName = attributeName;
			this.value = value;
			this.tagType = tagType;
		}

		public boolean accept(Node node) {
			if (tagType != null && node.getClass() != tagType) {// TableColumn
				return false;
			}

			String id = ((TableColumn) node).getAttribute(attributeName);// id澶у皬鍐欐棤鎵�璋�
			if (value.equals(id)) {
				return true;
			}

			return false;
		}
	}

	class DownLoadPageAndDownloadPhoto extends Thread {
		String pageUrl = null;
		boolean otherPage = false;

		public DownLoadPageAndDownloadPhoto(String pageUrl) {
			this.pageUrl = pageUrl;
		}

		public DownLoadPageAndDownloadPhoto(String pageUrl, boolean otherPage) {
			this.pageUrl = pageUrl;
			this.otherPage = otherPage;
		}

		public void run() {
			try {
				System.out.println("姝ｅ湪涓嬭浇椤甸潰 " + pageUrl);
				String pageContent = readURLToString(new URL(pageUrl), enc);
				System.out.println("pageContent:::"+pageContent);
				System.out.println("寮�濮嬪垎鏋愰〉闈㈠浘鐗囬摼鎺�" + pageUrl);
				Node keyTDNode = getKeyTD(pageContent);
				System.out.println("keyTDNode:::"+keyTDNode);
				String img = getImgUrl(keyTDNode);
				if (img != null) {
					new PhotoDownload(img).start();
				}
				if (otherPage) {
					NodeList otherPageUrls = getOtherPageUrl(keyTDNode);
					for (int i = 0; i < otherPageUrls.size(); i++) {
						LinkTag pageLink = (LinkTag) otherPageUrls.elementAt(i);
						new DownLoadPageAndDownloadPhoto(urlFold
								+ pageLink.getLink(), false).start();
					}
					totalPics = 1 + otherPageUrls.size();
					pageurlSetover = true;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
