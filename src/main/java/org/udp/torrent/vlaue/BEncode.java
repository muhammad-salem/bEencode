package org.udp.torrent.vlaue;

public interface BEncode <T> {
	
	T value();
	String bEncode();
	@Override String toString();
	
}
