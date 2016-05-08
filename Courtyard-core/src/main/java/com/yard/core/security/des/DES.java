package com.yard.core.security.des;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.sun.crypto.provider.SunJCE;

@SuppressWarnings({ "unused", "restriction" })
public class DES {

	private String Algorithm;
	private Cipher c;
	private byte cipherByte[];

	private SecretKey deskey;
	private KeyGenerator keygen;
	SecretKeySpec kspec;
	String str_key;

	public DES(byte strkey[], boolean flag) throws Exception {
		Algorithm = "DES";
		if (flag)
			init(strkey, flag);
		else
			init(strkey);
	}

	public DES(String strkey, boolean flag) throws Exception {
		Algorithm = "DES";
		if (flag)
			init(strkey, flag);
		else
			init(strkey);
	}

	public String createDecryptor(byte buff[]) throws Exception {
		c.init(2, kspec);
		cipherByte = c.doFinal(buff);
		return new String(cipherByte);
	}

	public byte[] createEncryptor(byte str[]) throws Exception {
		c.init(1, kspec);
		cipherByte = c.doFinal(str);
		return cipherByte;
	}

	public byte[] createEncryptor(String str) throws Exception {
		c.init(1, kspec);
		cipherByte = c.doFinal(str.getBytes());
		return cipherByte;
	}

	public String getStr_key() {
		return str_key;
	}

	public void init(byte key[], boolean flag) throws Exception {
		Security.addProvider(new SunJCE());
		keygen = KeyGenerator.getInstance(Algorithm);
		deskey = keygen.generateKey();
		kspec = new SecretKeySpec(key, Algorithm);
		c = Cipher.getInstance(Algorithm);
	}

	public void init(String key, boolean flag) throws Exception {
		Security.addProvider(new SunJCE());
		str_key = new String(key);
		keygen = KeyGenerator.getInstance(Algorithm);
		deskey = keygen.generateKey();
		kspec = new SecretKeySpec(key.getBytes(), Algorithm);
		c = Cipher.getInstance(Algorithm);
	}

	public void init(byte key[]) throws Exception {
		Security.addProvider(new SunJCE());
		keygen = KeyGenerator.getInstance(Algorithm);
		deskey = keygen.generateKey();
		kspec = new SecretKeySpec(key, Algorithm);
		c = Cipher.getInstance("DES/ECB/NoPadding");
	}

	public void init(String key) throws Exception {
		Security.addProvider(new SunJCE());
		str_key = new String(key);
		keygen = KeyGenerator.getInstance(Algorithm);
		deskey = keygen.generateKey();
		kspec = new SecretKeySpec(str_key.getBytes("ISO8859_1"), Algorithm);
		c = Cipher.getInstance("DES/ECB/NoPadding");
	}

	public void setStr_key(String str_key) {
		this.str_key = str_key;
	}
}
