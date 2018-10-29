package com.glcnac.test;

import java.io.File;

public class Test {
	public static void main(String[] args) {
		test();
	}

	private static void test() {
		String path = "";
		File ff = new File(path);
		System.out.println(ff.exists());
	}
}
