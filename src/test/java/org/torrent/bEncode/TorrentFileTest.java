package org.torrent.bEncode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.torrent.bEncode.vlaue.BMap;
import org.torrent.bEncode.vlaue.BString;

public class TorrentFileTest {
	
	
	@Test
	public void torrentListTest() throws IOException, NoSuchAlgorithmException {
		InputStream in = getClass().getResourceAsStream("/ubuntu-19.10-desktop-amd64.iso.torrent");
		BEncodeReader<BMap> torrent = new BEncodeReader<BMap>(in);
		String pieces = (String) torrent.searchValue("info.pieces").value();
		long pieceLength = (long) torrent.searchValue("info.piece length").value();
		long length = (long) torrent.searchValue("info.length").value();
		System.out.printf("pieces: %d,\tbytes: %d,\t length: %d\n", pieces.length(), pieces.getBytes("UTF-8").length , length);
		
		System.out.println("length: " + length);
		System.out.println("piece.length: " + pieces.length());
		System.out.println("pieceLength: " + pieceLength);
		
//		byte[] sha1 = new byte[20];
//		System.arraycopy(pieces.getBytes(), 0, sha1, 0, sha1.length);
//		String sha1String = new String(sha1, "UTF8");
//		System.out.printf("sha1:\t%s,\tlength: %d\n", sha1String, sha1String.length());

		BString bString = new BString(20, new ByteArrayInputStream(pieces.getBytes()));
		System.out.println("bString: " + bString);
		in.close();
	}

}
