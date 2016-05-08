package com.yard.core.security.des;

@SuppressWarnings("unused")
public class DesUtil {

	/**
	 * @return
	 * @throws Exception
	 */
	public String s() throws Exception {
		// byte b_key[] = {
		// 56, 55, 56, 56, 56, 56, 56, 56 };
		String key = "dvswsfds";
		byte b_key[] = key.getBytes();
		DES my_des = new DES(b_key, true);
		byte bs[] = new byte[16];
		byte[] str_key = my_des.createEncryptor("反对".getBytes());
		System.out.println(new String(str_key));
		String r = my_des.createDecryptor(str_key);
		System.out.println(r);

		// ///des加密
		String a = "你";
		byte[] b = a.getBytes();
		System.out.println(b.length);
		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}
		System.out.println(new String(b, 0, 2));
		return a;
	}
}
