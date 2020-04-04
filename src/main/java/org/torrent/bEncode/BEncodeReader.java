package org.torrent.bEncode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.torrent.bEncode.vlaue.BEncode;
import org.torrent.bEncode.vlaue.BInt;
import org.torrent.bEncode.vlaue.BList;
import org.torrent.bEncode.vlaue.BMap;
import org.torrent.bEncode.vlaue.BString;

public class BEncodeReader<T extends BEncode<?>> implements BEncode<T>{
	
	private T value;
	public BEncodeReader(InputStream in) throws IOException {
		initBEncoder(in);
	}
	
	public BEncodeReader(byte[] bs) throws IOException {
		initBEncoder(new ByteArrayInputStream(bs));
	}

	@SuppressWarnings("unchecked")
	private void initBEncoder(InputStream in) throws IOException {
		char c = (char) in.read();
		if (c == 'i') {
			this.value = (T) new BInt(in);
		} else if (c == 'l') {
			this.value = (T) new BList(in);
		} else if (c == 'd') {
			this.value = (T) new BMap(in);
		} else if( c >= '0' && c <= '9'){
			// string value
			this.value = (T) new BString(in, c);
		}
	}

	@Override
	public T value() {
		return value;
	}

	@Override
	public String bEncode() {
		return value.bEncode();
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	/**
	 * search only dictionary for (key.name) in format [parent.child.child]
	 * @param keyName
	 * @return value
	 */
	public BEncode<?> searchValue(String keyName) {
		if (value instanceof BMap) {
			String[] names = keyName.split("\\.");
			BMap dictionary = (BMap) value;
			return searchValue(dictionary, names, 0);
		}
		return null;
	}
	
	public BEncode<?> searchValue(BMap value, String[] names, int index) {
		BMap dictionary = (BMap) value;
		Optional<BEncode<?>> optional = dictionary.search(names[index]);
		if (optional.isEmpty()) { return null; }
		BEncode<?> bValue = optional.get();
		if (bValue instanceof BMap) {
			if (index == names.length-1) {
				return bValue;
			} else if (index < names.length) {
				return searchValue((BMap)bValue, names, index+1);
			}
		} else {
			if (index+1 == names.length) {
				return bValue;
			} 
//			else if (index < names.length) {
//				return null;
//			}
		}
		return null;
	}
	
	
	

}
