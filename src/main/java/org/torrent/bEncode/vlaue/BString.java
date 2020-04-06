package org.torrent.bEncode.vlaue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.torrent.bEncode.util.BEncodeUtil;

public class BString implements BEncode.BEncodeString {
	
	public static BString parse(String bytesValue) {
		return new BString(bytesValue.getBytes(StandardCharsets.UTF_8));
	}
	
	private byte[] bytes;
	private String value;

	
	public BString(InputStream in) throws IOException {
		this(BEncodeUtil.readString(in));
	}
	
	public BString(InputStream in, char firstChar) throws IOException {
		this.value = BEncodeUtil.readString(BEncodeUtil.readLength(firstChar, in), in);
	}
	
	public BString(InputStream in, int byteLength) throws IOException {
		this(BEncodeUtil.readBytes(byteLength, in));
	}
	
	public BString(String value){
		this.value = value;
	}
	
	public BString(byte[] bytes) {
		this.bytes = bytes;
	}
	
	public BString(byte[] srcBytes,  int srcPos, int length) {
		this.bytes = new byte[length];
		System.arraycopy(srcBytes, srcPos, this.bytes, 0, length);
	}
	

	@Override
	public byte[] getBytes() {
		if (Objects.isNull(bytes)) {
			synchronized (this) {
				this.bytes = value.getBytes(StandardCharsets.UTF_8);
			}
		}
		return bytes;
	}
	
	@Override
	public String value() {
		if (Objects.isNull(value)) {
			synchronized (this) {
				this.value = new String(bytes, StandardCharsets.UTF_8);
			}
		}
		return value;
	}
	
	@Override
	public String toString() {
		return '\'' + value() + '\'';
	}
	
	
}
