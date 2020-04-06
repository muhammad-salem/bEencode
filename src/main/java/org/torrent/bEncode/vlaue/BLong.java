package org.torrent.bEncode.vlaue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.torrent.bEncode.util.BEncodeUtil;

public class BLong implements BEncode.BEncodeLong {
	
	private Long value;
	private byte[] bytes;
	
	public BLong(InputStream in) throws IOException {
		this(BEncodeUtil.readLong(in));
	}
	
	public BLong(Long value){
		this.value = value;
	}
	
	public BLong(byte[] bytes){
		this.bytes = bytes;
	}
	
	public BLong(byte[] srcBytes,  int srcPos, int length) {
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
	public Long value() {
		if (Objects.isNull(value)) {
			synchronized (this) {
				this.value = Long.parseLong(new String(bytes, StandardCharsets.UTF_8));
			}
		}
		return value;
	}

	@Override
	public String toString() {
		return value().toString();
	}
	
}
