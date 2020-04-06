package org.torrent.bEncode.vlaue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Objects;

import org.torrent.bEncode.util.BEncodeUtil;

public class BNumber implements BEncode.BEncodeNumber {
	
	private Number value;
	private byte[] bytes;
	
	public BNumber(InputStream in) throws IOException, ParseException {
		this(BEncodeUtil.readNumber(in));
	}
	
	public BNumber(Number value){
		this.value = value;
	}
	
	public BNumber(byte[] bytes){
		this.bytes = bytes;
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
	public Number value() {
		if (Objects.isNull(value)) {
			synchronized (this) {
				try {
					this.value =NumberFormat.getInstance().parse(new String(bytes, StandardCharsets.UTF_8));
				} catch (ParseException e) { 
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	@Override
	public String toString() {
		return value().toString();
	}
	
}
