package org.torrent.bEncode.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.torrent.bEncode.vlaue.BEncode;
import org.torrent.bEncode.vlaue.BInteger;
import org.torrent.bEncode.vlaue.BList;
import org.torrent.bEncode.vlaue.BMap;
import org.torrent.bEncode.vlaue.BString;

public final class BEParser {
	private BEParser() {}

//	public static BEncode<?> parse(int[] bs) {
//		
//		int c =  bs[0];
//		if (c == 'i') {
//			return new BEncode<Integer>() {
//				int[] value = bs;
//				int i = 0;
//				{
//					char[] cs = new char[value.length];
//					for(int i = 0; i < cs.length; i++) {
//					    cs[i] = (char) bs[i];
//					}
//					i = Integer.valueOf(new String(cs));
//				}
//				@Override
//				public Integer value() {
//					return i;
//				}
//
//				@Override
//				public String bEncode() {
//					return "i" + i + "e";
//				}
//			};
//		} else if( c >= '0' && c <= '9'){
//			return new BEncode<String>() {
//
//				@Override
//				public String value() {
//					// TODO Auto-generated method stub
//					return null;
//				}
//
//				@Override
//				public String bEncode() {
//					// TODO Auto-generated method stub
//					return null;
//				}};
//		} else if (c == 'l') {
//			this.value = (T) new BList(in);
//		} else if (c == 'd') {
//			this.value = (T) new BMap(in);
//		} 
//		
//		
//		return null;
//	}
	
	public static BMap parse(Map<String, ?> map) {
		final Map<BEncode<String> , BEncode<?>> result = new HashMap<>();
		map.forEach(new BiConsumer<String, Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public void accept(String key, Object value) {
				if (value instanceof Map) {
					result.put(new BString(key), parse((Map<String, ?>) value));
				}
				else if (value instanceof List) {
					result.put(new BString(key), parse((List<?>) value));
				}
				else if (value instanceof Integer) {
					result.put(new BString(key), new BInteger((Integer) value));
				}
				else if (value instanceof String) {
					result.put(new BString(key), new BString((String) value));
				} else {
					result.put(new BString(key), new BString(value.toString()));
				}
			}
		});
		
		return new BMap(result);
	}
	
	public static BList parse(List<?> list) {
		List<BEncode<?>> result = new ArrayList<>();
		list.forEach(new Consumer<Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public void accept(Object value) {
				if (value instanceof Map) {
					result.add(parse((Map<String, ?>) value));
				}
				else if (value instanceof List) {
					result.add(parse((List<?>) value));
				}
				else if (value instanceof Integer) {
					result.add(new BInteger((Integer) value));
				}
				else if (value instanceof String) {
					result.add(new BString((String) value));
				} else {
					result.add(new BString(value.toString()));
				}
			}
		});
		
		return new BList(result);
	}

}
