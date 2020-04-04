package org.udp.torrent.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class BEncodeUtil {
	private BEncodeUtil() {}
	
	

	/**
	 * Read BEncode Stream, convert to string
	 * BEncode like "4:spam" return -> "spam"
	 * @param in
	 * @return string of this BEncode
	 * @throws IOException
	 */
	public static String readString(InputStream in) throws IOException {
		int length = readInt(in);
		return readString(length, in);
	}

	/**
	 * read specific length from input stream, convert it to string
	 * @param length
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String readString(int length, InputStream in) throws IOException {
		return new String(BEncodeUtil.readBytes(length, in), StandardCharsets.UTF_8);
	}
	
	
	public static Integer readInt(InputStream in) throws IOException {
		return readInt(in, new ArrayList<>());
	}
	
	public static Integer readLength(char firstChar, InputStream in) throws IOException {
		ArrayList<Character> list = new ArrayList<>();
		list.add(firstChar);
		return readInt(in, list);
	}

	static Integer readInt(InputStream in, List<Character> list) throws IOException {
		int c;
		do {
			c = in.read();
			if (c == 'e' || c == ':') {
				break;
			} else if (c == 'i') {
				continue;
			}
			list.add((char) c);
		} while (true);
		char[] cs = new char[list.size()];
		for(int i = 0; i < cs.length; i++) {
		    cs[i] = list.get(i);
		}
		return Integer.valueOf(new String(cs));
	}
	
	
	
	
	public static byte[] readBytes(int length, InputStream in) throws IOException {
		byte[] bytes = new byte[length];
		for (int i = 0; i < length; i++) {
			bytes[i] = (byte) in.read();
		}
		return bytes;
	}
	
	
	public static char[] readChars(int length, InputStream in) throws IOException {
		char[] chars = new char[length];
		for (int i = 0; i < length; i++) {
			chars[i] = (char) in.read();
		}
		return chars;
	}
	
	public static int[] readInts(int length, InputStream in) throws IOException {
		int[] ints = new int[length];
		for (int i = 0; i < length; i++) {
			ints[i] =  in.read();
		}
		return ints;
	}
	
	public static short[] readShorts(int length, InputStream in) throws IOException {
		short[] shorts = new short[length];
		for (int i = 0; i < length; i++) {
			shorts[i] =  (short) in.read();
		}
		return shorts;
	}
	

}
