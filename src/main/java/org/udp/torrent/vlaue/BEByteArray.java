package org.udp.torrent.vlaue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.udp.torrent.util.BEncodeUtil;

public class BEByteArray implements BEncode<byte[]> {
	
	private byte[] value;
	private String stringValue;
	
	
	public BEByteArray(int length, InputStream in) throws IOException {
		this.value = BEncodeUtil.readBytes(length, in);
		this.stringValue = new String(value, StandardCharsets.UTF_8);
	}
	
	@Override
	public byte[] value() {
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

	
	public BMap asMap() throws IOException{
		return new BMap(new ByteArrayInputStream(value));
	}
	
	public BList asList() throws IOException{
		return new BList(new ByteArrayInputStream(value));
	}
	
	public BString asString() throws IOException{
		return new BString(new ByteArrayInputStream(value));
	}
	
	public BInt asInt() throws IOException{
		return new BInt(new ByteArrayInputStream(value));
	}
	

	
	
}
