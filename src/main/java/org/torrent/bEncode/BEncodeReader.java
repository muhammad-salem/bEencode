package org.torrent.bEncode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.torrent.bEncode.vlaue.BEncode;
import org.torrent.bEncode.vlaue.BList;
import org.torrent.bEncode.vlaue.BLong;
import org.torrent.bEncode.vlaue.BMap;
import org.torrent.bEncode.vlaue.BString;

public class BEncodeReader {
	
	public static BEncode<?> parse(InputStream in) throws IOException {
		return initBEncoder(in);
	}
	
	public static BEncode<?> parse(byte[] bs) throws IOException {
		return initBEncoder(new ByteArrayInputStream(bs));
	}

	private static BEncode<?> initBEncoder(InputStream in) throws IOException {
		char c = (char) in.read();
		if (c == 'i') {
			return  new BLong(in);
		} else if( c >= '0' && c <= '9'){
			return new BString(in, c);
		} else if (c == 'l') {
			return new BList(in);
		} else if (c == 'd') {
			return new BMap(in);
		}
		return BEncode.BNULL;
	}
}
