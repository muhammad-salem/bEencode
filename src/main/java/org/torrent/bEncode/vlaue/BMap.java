package org.torrent.bEncode.vlaue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.Objects;
import java.util.Optional;

public class BMap implements BEncode.BEncodeMap {
	
	private Map<BEncode<String> , BEncode<?>> value;
	
	public BMap(Map<BEncode<String> , BEncode<?>> value){
		this.value = value;
	}
	
	public BMap(byte[] bytes) throws IOException {
		this(new ByteArrayInputStream(bytes));
	}

	public BMap(InputStream in) throws IOException {
		this.value = new HashMap<>();
		initDictionary(in);
	}
	
	private void initDictionary(InputStream in) throws IOException {
		char c;
		BEncode<String> key = null;
		BEncode<?> valueForKey = null;
		do {
//			key = new StringValue(in);
			c = (char) in.read();
			if (c == 'i') {
				valueForKey = new BLong(in);
			} else if (c == 'l') {
				valueForKey = new BList(in);
			} else if (c == 'd') {
				if (!this.value.isEmpty()) {
					valueForKey = new BMap(in);
				}
			} else if (c == 'e') {
				break;
			} else if( c >= '0' && c <= '9'){
				// string value
				BString stringValue =  new BString(in, c);
				if (Objects.isNull(key)) {
					key = stringValue;
				} else if (Objects.isNull(valueForKey)) {
					valueForKey = stringValue;
				}
			}
			else {
				System.out.println("in available " + in.available());
				break;
			}
			if (Objects.nonNull(key) && Objects.nonNull(valueForKey)) {
				this.value.put(key, valueForKey);
				key = null;
				valueForKey = null;
			}
		} while (true);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public BEncode<?> get(Object index) {
		if (index instanceof BEncode) {
			return get((BEncode<String>)index);
		}
		else if(index instanceof String) {
			Optional<BEncode<String>> optional =  value.keySet().stream().filter(new Predicate<BEncode<String>>() {

				@Override
				public boolean test(BEncode<String> bEncode) {
					return bEncode.value().equals(index) ;
				}
			}).findFirst();
			if (optional.isPresent()) {
				BEncode<String> key = optional.get();
				return value.get(key);
			} else {
				return null;
			}	
		} else {
			return value.get(index);
		}
	}

	@Override
	public Map<BEncode<String>, BEncode<?>> value() {
		return value;
	}
	
	@Override
	public String toString() {
		Iterator<Entry<BEncode<String>, BEncode<?>>> i = value.entrySet().iterator();
        if (! i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
        	Entry<BEncode<String>, BEncode<?>> e = i.next();
        	BEncode<String> key = e.getKey();
        	BEncode<?> value = e.getValue();
            sb.append(key);
            sb.append(": ");
            sb.append(value);
            if (! i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
	}
	
}
