package org.torrent.bEncode;

import java.io.InputStream;

import org.junit.Test;
import org.torrent.bEncode.vlaue.BEByteArray;
import org.torrent.bEncode.vlaue.BECharArray;

public class ByteTest {
	
	@Test
	public void test1() throws Exception {
		System.out.println("byte");
		InputStream in = getClass().getResourceAsStream("/ubuntu-19.10-desktop-amd64.iso.torrent");
		System.out.println(in.available());
		BEByteArray byteValue = new BEByteArray(in.available(), in);
		System.out.println(byteValue.value().length);
		System.out.println(byteValue.toString().length());
		System.out.println(byteValue);
		
//		DictionaryValue stringValue = new BString(byteValue.value());
//		System.out.println(stringValue.value().length());

	}
	
//	@Test
	public void test2() throws Exception {
		System.out.println("char");
		InputStream in = getClass().getResourceAsStream("/ubuntu-19.10-desktop-amd64.iso.torrent");
		System.out.println(in.available());
		BECharArray byteValue = new BECharArray(in.available(), in);
		System.out.println(byteValue.value().length);
		System.out.println(byteValue);

	}
	

}
