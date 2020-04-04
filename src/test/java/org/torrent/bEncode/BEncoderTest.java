package org.torrent.bEncode;


import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.torrent.bEncode.vlaue.BMap;

public class BEncoderTest {


	
	@Test
	public void torrentListTest() throws IOException {
		InputStream in = getClass().getResourceAsStream("/ubuntu-19.10-desktop-amd64.iso.torrent");
		
//		BEncoder<?> bEncoder = new BEncoder<>(in);
//		System.out.println(bEncoder.value());
		BEncodeReader<BMap> value = new BEncodeReader<BMap>(in);
//		System.out.println(value);
		BMap info = (BMap)value.searchValue("info");
		info.streamKey().forEach(System.out::println);
		String pieces = (String) info.search("pieces").get().value();
		System.out.println(pieces);
		
//		System.out.println(value.searchValue("info.piece length").value());
//		System.out.println(value.searchValue("info.length").value());
//		System.out.println(value.searchValue("info.name").value());
//		System.out.println(value.searchValue("info.pieces").value());
//		System.out.println(value.searchValue("info"));
		
//		int length = (int) value.searchValue("info.length").value();
//		int pieceLength = (int) value.searchValue("info.piece length").value();
//		String pieces = (String) value.searchValue("info.pieces").value();
//		
//		int piecesCount = (length/pieceLength) + ((length%pieceLength) > 0 ? 1: 0 );
//		
//		System.out.printf("length: %d, pieceLength: %d, piecesCount: %d\n", length, pieceLength, piecesCount);
//		System.out.printf("pieces SHA1: %d, count: %d\n", pieces.length(), pieces.length()/20);
//		for (int i = 0; i < piecesCount; i++) {
//			char[] temp = new char[20];
//			pieces.getChars(i*20, ((i+1)*20)-1, temp, 0);
//			System.out.printf("index: %d  -> SHA1: %s\n", i+1, Arrays.toString(temp));
//		}
		
		
//		System.out.println();
		
	}
	
	

}
