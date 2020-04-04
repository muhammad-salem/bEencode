package org.udp.torrent.vlaue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.udp.torrent.util.BEncodeUtil;

public class BString implements BEncode<String> {
	
	private String value;
	
	public BString(String value){
		this.value = value;
	}
	
	public BString(byte[] buf) throws IOException{
		this.value = new String(buf);
	}
	

	
	public BString(String value, Void v) throws IOException{
		this(value.getBytes());
	}
	
	public BString(byte[] buf, Void v) throws IOException{
		this(new ByteArrayInputStream(buf));
	}
	
	public BString(InputStream in) throws IOException {
		this.value = BEncodeUtil.readString(in);
	}
	
	
	public BString(InputStream in, char firstChar) throws IOException {
		this.value = BEncodeUtil.readString(BEncodeUtil.readLength(firstChar, in), in);
	}
	
	public BString(int length, InputStream in) throws IOException {
		this.value = BEncodeUtil.readString(length, in);
	}
	
	@Override
	public String value() {
		return value;
	}
	
	@Override
	public String bEncode() {
		return value.length() + (':' + value) ;
	}
	
	@Override
	public String toString() {
		return '\'' + value + '\'';
	}
	
}
