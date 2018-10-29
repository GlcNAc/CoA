package prj.coa.challenge;


	/**
	 * 类描述：给定两个字符串，仅由小写字母组成，它们包含了相同字符。
	 *  求把第一个字符串变成第二个字符串的最小操作次数，且每次操作只能对第一个字符串中的某个字符移动到此字符串中的开头。
	 *   例如给定两个字符串“abcd" "bcad" ，输出：2，因为需要操作2次才能把"abcd"变成“bcad" ，方法是：abcd->cabd->bcad。 
 	 * @version: 1.0
	 * @author: 吴国栋
	 * @version: 2014年2月11日 下午2:23:40 
	 * 编写部门  ECM事业部 
	 * 版权所有  杭州慧智电子科技有限公司
	 */
public class CharacterMove {
	
	public static void strMove(String fromStr, String toStr){
		char[] ch1 = fromStr.toCharArray();
		char[] ch2 = toStr.toCharArray();		
		int idx = ch1.length - 1;
		int t = 0;
		for (int i = idx; i > 0 ; i--) {
			while (ch1[i]!=ch2[i]) {
				ch1 = cmove(ch1,i);
				t++;
			}
		}
		System.out.println(t);
	}
	
	public static char[] cmove(char[] ch1, int idx){
		char[] result = new char[ch1.length];
		for (int i = 0; i < result.length; i++) {
			if(i==0){
				result[i] = ch1[idx];
			}else if(i>idx){
				result[i] = ch1[i];
			}else {
				result[i] = ch1[i-1];
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		String str1 = "abcdef";
		String str2 = "bcaefd";
		strMove(str1, str2);
	}
	
}
