package org.torrent.bEncode;


import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.torrent.bEncode.vlaue.BList;
import org.torrent.bEncode.vlaue.BMap;

public class AppTest2 {


	
//	@Test
	public void torrentListTest() throws IOException {
//		String list = "i-3e:i44:i345623e";
		InputStream in = getClass().getResourceAsStream("/ubuntu-19.10-desktop-amd64.iso.torrent");
		BList listValue = new BList(in);
//		System.out.println("list getValue");
		System.out.println(listValue);
//		System.out.println(listValue.bEncode());
//		System.out.println(listValue.value());
//		listValue.streamString().forEach(System.out::println);
		
//		listValue.value().forEach(v -> {System.out.println(v.value().toString());});
	}
	
	
	@Test
	public void torrentMapTest() throws IOException {
//		String list = "i-3e:i44:i345623e";
		InputStream in = getClass().getResourceAsStream("/ubuntu-19.10-desktop-amd64.iso.torrent");
		BMap dictionary = new BMap(in);
//		System.out.println("list getValue");
//		System.out.println(dictionary.value());
//		System.out.println(dictionary.bEncode());
		System.out.println(dictionary.getKey("info"));
//		dictionary.streamString().forEach(System.out::println);
		
//		dictionary.value().forEach(v -> {System.out.println(v.value().toString());});
	}
	
	
	

}
