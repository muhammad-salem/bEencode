package org.torrent.bEncode.vlaue;

public interface BEncode <T> {
	
	T value();
	String bEncode();
	@Override String toString();
	
}
