package org.torrent.bEncode.vlaue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import org.torrent.bEncode.util.BEncodeUtil;

public class BNumber implements BEncode<Number> {
	
	private Number value;
	
	public BNumber(Long value){
		this.value = value;
	}
	public BNumber(String bEncode) throws IOException, ParseException {
		this(bEncode.getBytes(StandardCharsets.UTF_8));
	}
	public BNumber(byte[] buf) throws IOException, ParseException {
		this(new ByteArrayInputStream(buf));
	}
	public BNumber(InputStream in) throws IOException, ParseException {
		this.value = BEncodeUtil.readNumber(in);
	}
	
	@Override
	public Number value() {
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
