package org.torrent.bEncode.vlaue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.torrent.bEncode.util.BEncodeUtil;

public class BInteger implements BEncode.BEncodeInteger {

	private Integer value;
	private byte[] bytes;
	
	public BInteger(InputStream in) throws IOException {
		this(BEncodeUtil.readInt(in));
	}
	
	public BInteger(Integer value){
		this.value = value;
	}
	
	public BInteger(byte[] bytes){
		this.bytes = bytes;
	}
	
	public BInteger(byte[] srcBytes,  int srcPos, int length) {
		this.bytes = new byte[length];
		System.arraycopy(srcBytes, srcPos, this.bytes, 0, length);
	}
	
	@Override
	public byte[] getBytes() {
		if (Objects.isNull(bytes)) {
			synchronized (this) {
				this.bytes = value.toString().getBytes(StandardCharsets.UTF_8);
			}
		}
		return bytes;
	}
	
	@Override
	public Integer value() {
		if (Objects.isNull(value)) {
			synchronized (this) {
				this.value = Integer.parseInt(new String(bytes, StandardCharsets.UTF_8));
			}
		}
		return value;
	}

	@Override
	public String toString() {
		return value().toString();
	}
	
}
