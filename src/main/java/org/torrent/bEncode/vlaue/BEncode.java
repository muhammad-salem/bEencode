package org.torrent.bEncode.vlaue;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public interface BEncode <T> {
	
	T value();
	String bEncode();
	@Override String toString();

	default boolean isNULL()	{return this instanceof BNull;}
	default boolean isString()	{return this instanceof BEncodeString;}
	default boolean isInteger()	{return this instanceof BEncodeInteger;}
	default boolean isNumber()	{return this instanceof BEncodeNumber;}
	default boolean isLong()	{return this instanceof BEncodeLong;}
	default boolean isList()	{return this instanceof BEncodeList;}
	default boolean isMap()		{return this instanceof BEncodeMap;}
	default boolean isMapOrList(){return this instanceof BEncodeCollection;}
	
	default BString		asBString()		{ return isString()	? (BString) this : null; }
	default BInteger	asBInteger()	{ return isInteger()? (BInteger) this : null; }
	default BLong		asBLong()		{ return isLong()	? (BLong) this : null; }
	default BNumber		asBNumber()		{ return isNumber() ? (BNumber) this : null; }
	default BList		asBList()		{ return isList()	? (BList) this : null; }
	default BMap		asBMap()		{ return isMap()	? (BMap) this : null; }
	default BEncodeCollection<?> asBCollection() {
		return isMapOrList() ? (BEncodeCollection<?>) this : null; 
	}

	public static final BNull BNULL = new BNull();
	static class BNull implements BEncode<Object> {
		@Override public Object value() { return null; }
		@Override public String bEncode() { return null; }
	}

	public interface BEncodeString extends BEncode<String> {
		
		byte[] getBytes();
		
		default String bEncode() { 
			return value().length() + ":" + value();
		}
	}
	
	/**
	 * any Integer can be Long or Number.
	 * @author salem
	 *
	 */
	public interface BEncodeInteger extends BEncode<Integer> {
		byte[] getBytes();
		
		default Long	asLong() { return Long.valueOf( value() );}
		default Number	asNumber() { return  value();}
		default String	bEncode() { return 'i' + value().toString() + 'e'; }
	}
	
	public interface BEncodeLong extends BEncode<Long> {
		byte[] getBytes();
		
		default Number asNumber()	{ return  value();}
		default String bEncode()	{ return 'i' + value().toString() + 'e'; }
	}
	
	public interface BEncodeNumber extends BEncode<Number> {
		byte[] getBytes();
		
		default Long 	asLong()	{ return value().longValue() ;}
		default Integer asInteger()	{ return  value().intValue();}
		default String 	bEncode()	{ return 'i' + value().toString() + 'e'; }
	}
	
	public interface BEncodeCollection<T> extends BEncode<T> {
		
		BEncode<?> get(Object index);
		
		default BEncodeString getString (Object index) {
			BEncode<?> bEncode = get(index);
			return Objects.isNull(bEncode) ? null : bEncode.asBString();
		}
		
		default BEncodeInteger getInteger (Object index) {
			BEncode<?> bEncode = get(index);
			return Objects.isNull(bEncode) ? null : bEncode.asBInteger();
		}
		
		default BEncodeLong getLong (Object index) {
			BEncode<?> bEncode = get(index);
			return Objects.isNull(bEncode) ? null : bEncode.asBLong();
		}
		
		default BEncodeNumber getNumber (Object index) {
			BEncode<?> bEncode = get(index);
			return Objects.isNull(bEncode) ? null : bEncode.asBNumber();
		}
		
		default BEncodeMap getMap (Object index) { 
			BEncode<?> bEncode = get(index);
			if (Objects.nonNull(bEncode))	{return bEncode.asBMap();}
			else 							{return null;}
		}
		
		default BEncodeList getList (Object index) {
			BEncode<?> bEncode = get(index);
			return Objects.isNull(bEncode) ? null : bEncode.asBList();
		}
		
		
		
		
		/**
		 * keyName  map		==> "a.b.c.d"
		 * keyName  list	==> "[0].[1].[0].[3]"
		 * keyName  hybrid	==> "a.b[0].c.d[2]"
		 * @param keyName 
		 * @return
		 */
		default public BEncode<?> search(String keyName) {
			String[] names = keyName.split("\\.");
			return searchValue(names, 0);
		}
		
		default public BEncode<?> searchValue(String[] names, int index) {
			String keyName = names[index];
			BEncode<?> value = null;
			if (keyName.indexOf('[') > 0) {
				int listIndex = Integer.valueOf(
						keyName.substring(keyName.indexOf('[')+1, keyName.indexOf(']')));
				keyName = keyName.substring(0, keyName.indexOf('['));
				value = get(keyName).asBList().get(listIndex);
			} else {
				value = get(keyName);
			}
			if (index == names.length-1) {
				return value;
			} else if (index < names.length && Objects.nonNull(value) && value.isMapOrList()){
				return value.asBCollection().searchValue(names, index+1);
			} else {
				return null;
			}
		}
	}
	
	public interface BEncodeList extends BEncodeCollection< List<? extends BEncode<?>> > {
		
		default String bEncode() {
			StringBuilder builder  = new StringBuilder();
			builder.append('l');
			value().forEach((v) -> {
				builder.append(v.bEncode());
			});
			builder.append('e');
			return builder.toString();
		}
		
		default BEncode<?> get(int index) {
			return value().get(index);
		}
		
		
		default Stream<?> valueStream() {
			return value().stream().map(BEncode::value);
		}
		
		default Stream<String> asStringStream() {
			return value().stream().map(BEncode::toString);
		}
	}
	
	public interface BEncodeMap extends BEncodeCollection<Map<BEncode<String> , BEncode<?> >> {
		
		default String bEncode() {
			StringBuilder builder  = new StringBuilder();
			builder.append('d');
			value().forEach((k, v) -> {
				builder.append(k.bEncode());
				builder.append(v.bEncode());
			});
			builder.append('e');
			return builder.toString();
		}
		
		default Stream<String> keyStream() {
			return value().keySet().stream().map(BEncode::value);
		}
		
		default Stream<String> keyBencodeStream() {
			return value().keySet().stream().map(BEncode::bEncode);
		}
		
		default Stream<?> valuesStream() {
			return value().values().stream().map(BEncode::value);
		}
		
		default BEncode<?> get(BEncode<String> key){
			return value().get(key);
		}
		
		default BEncode<?> get(String key) {
			return search(key);
		}
		
		
	}
	
	
}
