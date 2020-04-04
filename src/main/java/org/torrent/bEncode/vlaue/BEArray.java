package org.torrent.bEncode.vlaue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.torrent.bEncode.util.BEncodeUtil;

public abstract class BEArray<T> implements BEncode<T[]> {
	
	private int[] ints;
	
	
	public BEArray(int length, InputStream in) throws IOException {
		this.ints = BEncodeUtil.readInts(length, in);
	}
	
	

	@Override
	public String toString() {
		return Arrays.toString(ints);
	}
	
}
