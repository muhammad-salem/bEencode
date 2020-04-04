package org.torrent.bEncode.vlaue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.torrent.bEncode.util.BEncodeUtil;

public class BInt implements BEncode<Integer> {
	
	private Integer value;
	
	public BInt(Integer value){
		this.value = value;
	}
	public BInt(String bEncode) throws IOException {
		this(bEncode.getBytes(StandardCharsets.UTF_8));
	}
	public BInt(byte[] buf) throws IOException {
		this(new ByteArrayInputStream(buf));
	}
	public BInt(InputStream in) throws IOException {
		this.value = BEncodeUtil.readInt(in);
	}
	
	@Override
	public Integer value() {
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
