package prj.coa.challenge;



	/**
	 * 类描述：一种报数游戏是从1开始连续报数，如果报到7的倍数（7，14，21，28……）或者包含数字7的数（7，17，27，37……）
	 * 就用拍手代替这个数而不能报出。假设你连续听到m声拍手，
	 * 问造成你听到m声拍手的第一下拍手所代表的数是几？
	 * 例如，你听到了两次连续的拍手，最小的可能这两次拍手是27和28，因此输出27。 
	 * 输入m, 输出这m次连续的拍手第一下所代表的最小可能的数。 
 	 * @version: 1.0
	 * @author: 吴国栋
	 * @version: 2014年2月11日 下午2:47:36 
	 * 编写部门  ECM事业部 
	 * 版权所有  杭州慧智电子科技有限公司
	 */
public class OneNumGame {
	
	public static boolean numIsd7Multi(int n){
		return (n%7==0);
	}
	
	public static boolean num7Contains(int n){
		String numstr = String.valueOf(n);
		return numstr.contains("7");
	}

	public static boolean check(int m, int n){
		for (int j = n; j < n + m; j++) {
			if(!numIsd7Multi(j) && !num7Contains(j)){
				return false;
			}
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		int m = 11;
		int min = 0;
		for (int i = 0; i < 10000; i++) {
			if(check(m, i)){
				min = i;
				break;
			}
		}
		System.out.println(min);
	}
}
