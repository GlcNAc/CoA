package com.glcnac.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.codec.digest.DigestUtils;

public class PwdGenerator {
	private static final char[] CHS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~!@#$%^&*()-_=+[]{}|\\/<>'\";:,.".toCharArray();
	
	public static void createPwd(int pwdStartLen, int pwdMaxLen, char[] pwdDict) {
		long pwdDictLen = pwdDict.length;
		char[] pwd = new char[pwdMaxLen + 2];
		int[] array = new int[pwdMaxLen];

		int currentLen = pwdStartLen;
		int i, j = 0;
		boolean next;
		try {
			long startTime = System.currentTimeMillis();
			FileOutputStream fos = new FileOutputStream("pwd1.txt");
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);

			while (currentLen <= pwdMaxLen) {
				for (i = 0; i < pwdMaxLen; i++)
					array[i] = 0;
				next = true;
				// String tempPwd;
				while (next) {

					for (i = 0; i < currentLen; i++)
						pwd[i] = pwdDict[array[i]];
					pwd[i] = '\0';
					// tempPwd = String.valueOf(pwd);
					bw.write(new String(pwd, 0, currentLen));
					bw.write('\n');

					for (j = currentLen - 1; j >= 0; j--) {
						array[j]++;
						if (array[j] != pwdDictLen)
							break;
						else {
							array[j] = 0;
							if (j == 0)
								next = false;
						}
					}
				}
				currentLen++;
			}
			bw.flush();
			bw.close();
			long endTime = System.currentTimeMillis();
			System.out.println("Pwd Generate Complete! " + (endTime - startTime) / 1000.0 + " seconds");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		// "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
		// normal way
		//PwdGenerator.createPwd(4, 5, CHS);
		// improved with recursive
		//PwdGenerator.createPwdRecursive(4, 5, CHS);
		for (int i = 3; i < 7; i++) {
			PwdGenerator.createPwdRecursive(i, i, CHS);
		}
		
	}

	public static void createPwdRecursive(int pwdStartLen, int pwdMaxLen, char[] pwdDict) {
		try {
			long startTime = System.currentTimeMillis();
			String filename = "pwd" + pwdStartLen + "-" + pwdMaxLen + ".txt";
			FileOutputStream fos = new FileOutputStream(filename);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			char[] pwd = new char[pwdMaxLen + 2];
			for (int currentLen = pwdStartLen; currentLen <= pwdMaxLen; currentLen++) {
				createPwdInnerRecursive(currentLen, 0, pwd, pwdMaxLen, pwdDict, bw);
			}
			bw.flush();
			bw.close();
			long endTime = System.currentTimeMillis();
			System.out.println("Pwd Generate Complete! " + ((endTime - startTime) / 1000.0) + " seconds");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createPwdInnerRecursive(int cur, int index, char[] pwd, int pwdMaxLen, char[] pwdDict,
			BufferedWriter bw) {

		try {
			for (int i = 0; i < pwdDict.length; i++) {
				pwd[index] = pwdDict[i];
				if (index == cur - 1) {
					String npw = new String(pwd, 0, cur);
					String md5pw = DigestUtils.md5Hex(npw).toUpperCase();
					bw.write(npw + "=" + md5pw);
					bw.write('\n');
				} else
					createPwdInnerRecursive(cur, index + 1, pwd, pwdMaxLen, pwdDict, bw);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void createPwdRecursive2Db(int pwdStartLen, int pwdMaxLen, char[] pwdDict) {
		try {
			long startTime = System.currentTimeMillis();
			char[] pwd = new char[pwdMaxLen + 2];
			for (int currentLen = pwdStartLen; currentLen <= pwdMaxLen; currentLen++) {
				createPwdInnerRecursive2Db(currentLen, 0, pwd, pwdMaxLen, pwdDict);
			}
			long endTime = System.currentTimeMillis();
			System.out.println("Pwd Generate Complete! " + ((endTime - startTime) / 1000.0) + " seconds");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createPwdInnerRecursive2Db(int cur, int index, char[] pwd, int pwdMaxLen, char[] pwdDict) {

		try {
			for (int i = 0; i < pwdDict.length; i++) {
				pwd[index] = pwdDict[i];
				if (index == cur - 1) {
					String npw = new String(pwd, 0, cur);
					String md5pw = DigestUtils.md5Hex(npw).toUpperCase();
					save(npw, md5pw);
				} else
					createPwdInnerRecursive2Db(cur, index + 1, pwd, pwdMaxLen, pwdDict);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void save(String npw, String md5pw) {
		
	}
}
