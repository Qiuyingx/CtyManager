package com.yard.core.util;

import java.util.BitSet;

public class ISOUtil {
	/*
	 * Convert ASCII to BCD @param s input string @padLeft indicating wether pad ,真假结果一样 @return byte[] public static byte[]
	 * str2bcd(String s, boolean padLeft) { int len = s.length(); byte[] d = new byte[ (len+1) >> 1 ]; int start = (((len & 1) ==
	 * 1) && padLeft) ? 1 : 0; for (int i=start; i < len+start; i++) d [i >> 1] |= (s.charAt(i-start)-'0') << ((i & 1) == 1 ? 0 :
	 * 4); return d; }
	 */
	/*
	 * Convert BCD to ASCII @param byte[] input @padLeft indicating wether pad ,真假结果一样 @return byte[]
	 */
	public static String bcd2str(byte[] b, int offset, int len, boolean padLeft) {
		StringBuffer d = new StringBuffer(len);
		int start = (((len & 1) == 1) && padLeft) ? 1 : 0;
		for (int i = start; i < len + start; i++) {
			int shift = ((i & 1) == 1 ? 0 : 4);
			char c = Character.forDigit(((b[offset + (i >> 1)] >> shift) & 0x0F), 16);

			d.append(Character.toUpperCase(c));
		}
		return d.toString();
	}

	/**
	 * 将byte数组中的内容转换为十六进制 适用二进制和ASCII的包 (suitable for dumps and ASCII packaging of Binary fields
	 * 
	 * @param b
	 *            - byte array
	 * @return String representation
	 */
	public static String hexString(byte[] b) {
		StringBuffer d = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			char hi = Character.forDigit((b[i] >> 4) & 0x0F, 16);
			char lo = Character.forDigit(b[i] & 0x0F, 16);
			d.append(Character.toUpperCase(hi));
			d.append(Character.toUpperCase(lo));
		}
		return d.toString();
	}

	/**
	 * 将byte[]转换成字符串,如果内容为ascii
	 * 
	 * @param b
	 *            - byte array
	 * @return String representation
	 */
	public static String dumpString(byte[] b) {
		StringBuffer d = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			char c = (char) b[i];
			if (Character.isISOControl(c)) {
				switch (c) {
				case '\r':
					d.append("{CR}");
					break;
				case '\n':
					d.append("{LF}");
					break;
				case '\000':
					d.append("{NULL}");
					break;
				case '\001':
					d.append("{SOH}");
					break;
				case '\002':
					d.append("{STX}");
					break;
				case '\003':
					d.append("{ETX}");
					break;
				case '\004':
					d.append("{EOT}");
					break;
				case '\005':
					d.append("{ENQ}");
					break;
				case '\006':
					d.append("{ACK}");
					break;
				case '\007':
					d.append("{BEL}");
					break;
				case '\020':
					d.append("{DLE}");
					break;
				case '\025':
					d.append("{NAK}");
					break;
				case '\026':
					d.append("{SYN}");
					break;
				case '\034':
					d.append("{FS}");
					break;
				default:
					char hi = Character.forDigit((b[i] >> 4) & 0x0F, 16);
					char lo = Character.forDigit(b[i] & 0x0F, 16);
					d.append('[');
					d.append(Character.toUpperCase(hi));
					d.append(Character.toUpperCase(lo));
					d.append(']');
					break;
				} // end switch
			} else {
				d.append(c);
			} // end if
		} // end for
		return d.toString();
	} // end dumpString

	/**
	 * Bitwise XOR between corresponding bytes
	 * 
	 * @param op1
	 *            byteArray1
	 * @param op2
	 *            byteArray2
	 * @return an array of length = the smallest between op1 and op2
	 */
	public static byte[] xor(byte[] op1, byte[] op2) {
		byte[] result = null;
		// Use the smallest array
		if (op2.length > op1.length) {
			result = new byte[op1.length];
		} else {
			result = new byte[op2.length];
		}
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) (op1[i] ^ op2[i]);
		}
		return result;
	}

	/*
	 * 在指定字符串左侧添加指定的char @param s - input string @param len - len of return string @param c - charactor to pad @return string
	 */
	public static String padleft(String s, int len, char c) throws Exception {
		s = s.trim();
		if (s.length() > len) {
			throw new Exception("invalid len " + s.length() + "/" + len);
		}
		StringBuffer d = new StringBuffer(len);
		int fill = len - s.length();
		while (fill-- > 0) {
			d.append(c);
		}
		d.append(s);
		return d.toString();
	}

	/*
	 * 在指定字符串左侧添加 0 补位 @param s - input string @param len - len of return string
	 * 
	 * @param c - charactor to pad @return string
	 */
	public static String zeropad(String s, int len) throws Exception {
		return padleft(s, len, '0');
	}

	public static byte[] hex2byte(byte[] b, int offset, int len) {
		byte[] d = new byte[len];
		for (int i = 0; i < len * 2; i++) {
			int shift = i % 2 == 1 ? 0 : 4;
			d[i >> 1] |= Character.digit((char) b[offset + i], 16) << shift;
		}
		return d;
	}

	/**
	 * converts a BitSet into a binary field used in pack routines
	 * 
	 * @param b
	 *            - the BitSet
	 * @return binary representation
	 */
	public static byte[] bitSet2byte(BitSet b) {
		int len = (b.length() > 65) ? 128 : 64;
		byte[] d = new byte[len >> 3];
		for (int i = 0; i < len; i++) {
			if (b.get(i + 1)) {
				d[i >> 3] |= (0x80 >> (i % 8));
			}
		}
		if (len > 64) {
			d[0] |= 0x80;
		}
		return d;
	}

	public static int[] bitSet2byte2(BitSet b) {
		int len = (b.length() > 65) ? 128 : 64;
		int[] d = new int[len >> 3];
		for (int i = 0; i < len; i++) {
			if (b.get(i + 1)) {
				d[i >> 3] |= (0x80 >> (i % 8));
			}
		}
		if (len > 64) {
			d[0] |= 0x80;
		}
		return d;
	}

	/**
	 * Converts a binary representation of a Bitmap field into a Java BitSet
	 * 
	 * @param b
	 *            - binary representation
	 * @param offset
	 *            - staring offset
	 * @param bitZeroMeansExtended
	 *            - true for ISO-8583
	 * @return java BitSet object
	 */
	public static BitSet byte2BitSet(byte[] b, int offset, boolean bitZeroMeansExtended) {
		int len = bitZeroMeansExtended ? ((b[offset] & 0x80) == 0x80 ? 128 : 64) : 64;

		BitSet bmap = new BitSet(len);
		for (int i = 0; i < len; i++) {
			if (((b[offset + (i >> 3)]) & (0x80 >> (i % 8))) > 0) {
				bmap.set(i + 1);
			}
		}
		return bmap;
	}

	/**
	 * bit representation of a BitSet suitable for dumps and debugging
	 * 
	 * @param b
	 *            - the BitSet
	 * @return string representing the bits (i.e. 011010010...)
	 */
	public static String bitSet2String(BitSet b) {
		int len = b.size();
		len = (len > 128) ? 128 : len;
		StringBuffer d = new StringBuffer(len);
		for (int i = 0; i < len; i++) {
			d.append(b.get(i) ? '1' : '0');
		}
		return d.toString();
	}

	/*
	 * 转换byte[]到bitset @param b-byte[] input with ascii @param offset - start position @param len - stop position @return bitset
	 */
	public static BitSet byte2BitSet(byte[] b, int offset, int len) {
		BitSet bmap = new BitSet(len);
		for (int i = 0; i < len; i++) {
			if (((b[offset + (i >> 3)]) & (0x80 >> (i % 8))) > 0) {
				bmap.set(i + 1);
			}
		}
		return bmap;
	}

	public static BitSet hex2BitSet(byte[] b, int offset, boolean bitZeroMeansExtended) {
		int len = bitZeroMeansExtended ? ((Character.digit((char) b[offset], 16) & 0x08) == 8 ? 128 : 64) : 64;
		BitSet bmap = new BitSet(len);
		for (int i = 0; i < len; i++) {
			int digit = Character.digit((char) b[offset + (i >> 2)], 16);
			if ((digit & (0x08 >> (i % 4))) > 0) {
				bmap.set(i + 1);
			}
		}
		return bmap;
	}

	/**
	 * pads to the right
	 * 
	 * @param s
	 *            - original string
	 * @param len
	 *            - desired len
	 * @return space padded string
	 */
	public static String strpad(String s, int len) {
		StringBuffer d = new StringBuffer(s);
		while (d.length() < len) {
			d.append(' ');
		}
		return d.toString();
	}

	public static byte[] byte2bcd(byte[] bt) {

		byte rst[] = null;
		byte data[] = null;
		if (bt.length % 2 != 0) {
			data = new byte[bt.length + 1];
			data[0] = 0;
			System.arraycopy(bt, 0, data, 1, bt.length);
		} else {
			data = bt;
		}

		rst = new byte[data.length / 2];

		int low = 0;
		int high = 0;
		int outpos = 0;

		for (int i = 0; i < data.length; i += 2) {

			high = data[i];
			low = data[i + 1];

			if (((char) low) > '9') {
				low = (byte) (low - 'A' + 10);
			} else {
				low = (byte) (low & 0x0F);
			}
			if (((char) high) > '9') {
				high = (byte) (high - 'A' + 10);
			} else {
				high = (byte) (high & 0X0F);
			}
			rst[outpos] = (byte) (high << 4 | low);
			outpos++;
		}
		return rst;
	}

	public static byte[] str2bcd(String value, boolean pad) {

		byte in[] = value.toUpperCase().getBytes();
		int len = value.length();
		byte r[] = new byte[(len + 1) >> 1]; // r=结果
		int low = 0, high = 0;
		int inpos = 0, outpos = 0; // low=低四位，high=高四位，pos=r当前位置

		if ((len % 2) > 0) { // 如果不是2的整数倍
			if (((char) in[0]) > '9') {
				r[outpos] = (byte) (in[0] - 'A' + 10);
			} else {
				r[outpos] = (byte) (in[0] & 0x0F);
			}
			outpos++;
			inpos++;
		}
		for (; inpos < len; inpos += 2) {
			high = in[inpos];
			low = in[inpos + 1];
			if (((char) low) > '9') {
				low = (byte) (low - 'A' + 10);
			} else {
				low = (byte) (low & 0x0F);
			}
			if (((char) high) > '9') {
				high = (byte) (high - 'A' + 10);
			} else {
				high = (byte) (high & 0X0F);
			}
			r[outpos] = (byte) (high << 4 | low);
			outpos++;
		} // end for

		return r;
	}

	public static byte[] bcd2ascii(byte[] data, int offset, int num) {
		byte result[];
		int counter = 0;
		int high = 0;
		int iByte = 0;
		int low = 0;
		result = new byte[num * 2];
		for (counter = 0; counter < num; counter++) {
			iByte = data[offset + counter];
			if (iByte < 0) {
				iByte &= 0xFF;
			}
			high = iByte >> 4;
			low = iByte & 0x0F;
			if (high > 9) {
				result[counter * 2] = (byte) (high - 10 + 'A');
			} else {
				result[counter * 2] = (byte) (high + '0');
			}
			if (low > 9) {
				result[counter * 2 + 1] = (byte) (low - 10 + 'A');
			} else {
				result[counter * 2 + 1] = (byte) (low + '0');
			}
		}
		return result;
	}

	public static byte CheckSum(byte[] byteArrayData) {
		int i = 0;
		byte b = 0;
		for (i = 1; i < byteArrayData.length; i++) {
			b ^= byteArrayData[i];
		}

		return b;
	}

}
