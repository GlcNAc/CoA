package prj.coa.test;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.digest.DigestUtils;

public class TestMd5 {

	public static void main(String[] args) {
		String path = "d://dtree.js";
		File file = new File(path);
		try {
			String s = new String(DigestUtils.md5Hex(new FileInputStream(file)));
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
