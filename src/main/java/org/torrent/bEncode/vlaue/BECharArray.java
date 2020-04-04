package org.torrent.bEncode.vlaue;

import java.io.IOException;
import java.io.InputStream;

import org.torrent.bEncode.util.BEncodeUtil;

public class BECharArray implements BEncode<char[]> {
	
	private char[] value;
	private String stringValue;
	
	
	public BECharArray(int length, InputStream in) throws IOException {
		this.value = BEncodeUtil.readChars(length, in);
		this.stringValue = new String(value);
	}
	
	@Override
	public char[] value() {
		return value;
	}
	
	@Override
	public String bEncode() {
		return stringValue;
	}
	
	@Override
	public String toString() {
		return stringValue;
	}
	
}
