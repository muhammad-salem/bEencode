package org.torrent.bEncode.vlaue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.torrent.bEncode.util.BEncodeUtil;

public class BLong implements BEncode<Long> {
	
	private Long value;
	
	public BLong(Long value){
		this.value = value;
	}
	public BLong(String bEncode) throws IOException {
		this(bEncode.getBytes(StandardCharsets.UTF_8));
	}
	public BLong(byte[] buf) throws IOException {
		this(new ByteArrayInputStream(buf));
	}
	public BLong(InputStream in) throws IOException {
		this.value = BEncodeUtil.readLong(in);
	}
	
	@Override
	public Long value() {
		return value;
	}
	@Override
	public String bEncode() {
		return 'i' + value.toString() + 'e';
	}
	@Override
	public String toString() {
		return value.toString();
	}
	
}
