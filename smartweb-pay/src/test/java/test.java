import com.adtec.sys.modules.sys.utils.PwdCheck;

import java.io.*;

public class test {
	public static void main(String[] args) {
//		String item1 = "1010000000001";
//		long busiPara1 = Long.parseLong(item1);
//		busiPara1++;
//		long busiPara2 = Long.parseLong("2" + String.valueOf(busiPara1).substring(1));
//		long busiPara3 = Long.parseLong("3" + String.valueOf(busiPara1).substring(1));
//		long busiPara4 = Long.parseLong("4" + String.valueOf(busiPara1).substring(1));
//		System.out.println(busiPara1+";" + busiPara2 + ";" + busiPara3+ ";" + busiPara4);
		String password = "47321_Qaz";
		char[] chars = password.toCharArray();
		for (int i = 0; i < chars.length - 2; i++) {
			int n1 = chars[i];
			int n2 = chars[i + 1];
			int n3 = chars[i + 2];
			if(n1 == n2 && n1 == n3){
				System.out.println("不行");;
			}
			//判断连续字符 正序+倒序
			if (n1 + 1 == n2 && n1 + 2 == n3 || n1 - 1 == n2 && n1 - 2 == n3) {
				System.out.println("不行");
			}
		}

	}
}
