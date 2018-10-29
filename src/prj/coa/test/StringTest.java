package prj.coa.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class StringTest {

	
	public static void main(String[] args) {
		test4();
	}
	
	private static void test4() {
		java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		java.text.SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dd = new Date();
		System.out.println(sdf.format(dd));
		System.out.println(sdf2.format(dd));
		String filename = "QZ$12321";
		System.out.println(filename.substring(3));
	}

	protected static void test3() {
		String tuqian1 = "��ӡͼ��-$ISO_expand_A0_(841.\r\n" + 
				"00_x_1189.00_MM)+$���3-$+$�Ƿ�������ӡ-$��+$���2-$+$���1-$+$����-$+$��-$��\r\n" + 
				"��+$�ؼ��汾-$20170323+$���-$����+$��ӡ�鵵ͼ-$��+$���3-$3+$���1-$1+$���2-$2\r\n" + 
				"+$ͼ�η���-$A0+$dwg�ļ�id-$3990695$+$ͼ��-$A0��-Model+$plt����-$23281168279961+$\r\n" + 
				"���Ǳ��ò���Hz-$+$У��-$����+$plt�ļ�-$C:\\Users\\huizhi\\Desktop\\A0��-Model.PLT+$\r\n" + 
				"�����û�����-$gsj+$��-$+$����handle-$22+$��ӡ�ļ���-$E:\\tjhg��򻯹�\\��򲳻���\r\n" + 
				"�����޹�˾ͼ��\\��򲳻��������޹�˾ͼ��\\��׼ͼ��\\A0��.dwg+$���-$0+$����-$+$��ӡ\r\n" + 
				"����Ϊ�ļ�һ����-$C:\\Program Files\\zhihui2000\\temp\\temphuizhi.dwg+$��Ŀ����-$��\r\n" + 
				"�򲳻��������޹�˾���ݸ���+$������ͼ��-$A0+$����-$+$���-$����+$ƴͼ����Id-$30\r\n" + 
				"511+$������������-$GSJ+$��ƽ׶�-$ʩ��ͼ+$ͼ�α���-$1:1+$��2-$+$��1-$+$��ӡ\r\n" + 
				"�ݸ�-$δ����+$��3-$+$��ͼ������-$33244MA8+$��ӡ����-$1+$�ϴ��ļ�·��-$C:\\Users\r\n" + 
				"\\Administrator\\Desktop\\+$���ڸ���Pdf-$3990697$+$����IP-$192.168.137.1+$CAD����-$\r\n" + 
				"Autodesk AutoCAD 2014 - [A0��.dwg]+$ͼ��ǵ�-$(1189.000000,841.000000,0.000000)(\r\n" + 
				"1189.000000,0.000000,0.000000)(0.000000,0.000000,0.000000)(0.000000,841.000000,0\r\n" + 
				".000000)+$��Ч��ӡ����-$1167.412549 * 815.692104+$װ��/����-$+$רҵ-$+$�����ӡ\r\n" + 
				"����-$+$��ӡ����-$1+$�ؼ�����-$20170323+$��ӡʱ��-$2018-07-13 14:39:40+$��׼��-$\r\n" + 
				"����+$plt�ļ�id-$3990696+$�Ƿ���ͼǩ-$��+$���3-$+$ͼ��-$116-EL-LI-DIA-01+$���2\r\n" + 
				"-$+$У��2-$+$У��1-$����+$���1-$+$У��3-$+$ͼֽ����-$����ͼֽ+$��������-$Model+\r\n" + 
				"$��Ŀ���-$17-103AC-001+$ȫͼ����-$3990698+$�Ƿ�����ͼֽ-$��+$��ӡ�����豸-$Desi\r\n" + 
				"gnJet 755CM C3198A.pc3+$";
		 String paper_type1=tuqian1.substring(tuqian1.indexOf("ͼ�η���-$") + 1, tuqian1.lastIndexOf("$��ӡʱ��"));  
		    String paper_type=paper_type1.substring(paper_type1.indexOf("$") + 1, paper_type1.lastIndexOf("+"));  
         System.out.println("paper_type--------------------------------------------"+paper_type);
	}
	
	protected static void test2(){
		List<String> strList = new ArrayList<String>();
		strList.add("K1997-13-D01");
		strList.add("K1997-14-T01");
		strList.add("K1997-16-D02");
		strList.add("K1997-15-D05");
		strList.add("K1997-133-D06");
		strList.add("K1997-12");
		strList.add("K1997-11-D1");
		strList.add("K1997-111-D11");
		strList.add("K1997-");
		List<Integer> yList = new ArrayList<Integer>();
		List<Integer> sList = new ArrayList<Integer>();
		System.out.println(yList.size());
		Collections.sort(yList);
		Collections.sort(sList);
		for (String str : strList) {
			str = str.substring(6);
			//System.out.println(str);
			String[] strs = str.split("-");
			for (int i = 0; i < strs.length; i++) {
				if(i==0 && strs[0].length() > 0){
					try {
						yList.add(Integer.parseInt(strs[0]));						
					} catch (Exception e) {
					}
				}
				if(i==1 && strs[1].startsWith("D")){
					try {
						sList.add(Integer.parseInt(strs[1].substring(1)));		
					} catch (Exception e) {
					}
				}
			}
		}
		Collections.sort(yList, Collections.reverseOrder());
		Collections.sort(sList, Collections.reverseOrder());
		System.out.println(yList);
		System.out.println(sList);
		System.out.println(yList.get(yList.size()-1));
		System.out.println(sList.get(sList.size()-1));
	}
	
	
	protected static void test1(){
		String sql = "( ";
		String filesid = "2011$$2222";

		if (!"".equals(filesid) && filesid != null) {
			if (filesid.indexOf("$") > 0) {
				String ss[] = filesid.split("\\u0024");
				System.out.println(ss.length);
				for (int i = 0; i < ss.length; i++) {
					if (i == ss.length - 1) {
						sql = sql + ss[i].trim() + " )";
					} else {
						sql = sql + ss[i].trim() + " , ";
					}
				}
			} else {
				sql = " ( " + filesid + " ) ";
			}
		} else {
			sql = "( -1 ) ";
		}

		System.out.println(sql);

		String testStr = "S23432A";
		System.out.println(testStr.substring(0, testStr.length() - 1));

		String str = "B3781S-D0108A-02";
		String[] strs = str.split("-");
		char c = strs[1].charAt(strs[1].length() - 1);
		if ('A' <= c && c <= 'Z') {
			strs[1] = strs[1].substring(0, strs[1].length() - 1);
		}
		for (int i = 0; i < strs.length; i++) {
			System.out.println(strs[i]);
		}

		String fid = "2852439";
		System.out.println(Integer.parseInt(fid));

		String picNumber = "475S0181Z-D04";
		if(picNumber.startsWith("475")){
			picNumber = picNumber.replaceFirst("475", "");
		}
		System.out.println(picNumber);
		
		StringTokenizer st = new StringTokenizer(picNumber, "-");

		List<String> list = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		if (list.size() < 3) {
			if (list.size() == 2)
				list.add("01");
		}
		for (int i = 0; i < list.size(); ++i) {
			if (i == 0) {
				StringBuilder sb = new StringBuilder((String) list.get(i));
				sb = sb.reverse();

				System.out.println(sb.substring(0, sb.length() - 1));
			}

		}
	}
}
