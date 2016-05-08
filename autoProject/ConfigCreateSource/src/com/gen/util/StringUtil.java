package com.gen.util;

import java.util.Random;

public class StringUtil {

	/**
	 * 随机生成一个字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String randStr(int letterLength, int numberLength) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < letterLength; i++) {
			int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
			sb.append((char) (choice + random.nextInt(26)));
		}
		for (int i = 0; i < numberLength; i++) {
			int r = random.nextInt(10);
			if (i == 0 && r == 0) {
				r = 1;
			}
			sb.append(r);
		}
		return sb.toString();
	}

	public static boolean isNotEmpty(String str) {
		return str != null && !"".equals(str);
	}
}
