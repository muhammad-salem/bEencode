package org.torrent.bEncode.vlaue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BMap implements BEncode<Map<BEncode<String> , BEncode<?> >> {
	
	private Map<BEncode<String> , BEncode<?>> value;
	
	public BMap(Map<BEncode<String> , BEncode<?>> value){
		this.value = value;
	}
	
	public BMap(String bEncode) throws IOException {
		this(bEncode.getBytes(StandardCharsets.UTF_8));
	}
	public BMap(byte[] buf) throws IOException {
		this(new ByteArrayInputStream(buf));
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
				valueForKey = new BInt(in);
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
	
	@Override
	public Map<BEncode<String> ,BEncode<?>> value() {
		return value;
	}
	
	public BEncode<?> getKey(String key) {
		return value.entrySet()
				.stream()
				.filter(e -> key.equals(e.getKey().value()))
				.findAny()
				.get()
				.getValue();
	}
	
	public Optional<BEncode<?>> search(String keyName) {
		Map<BEncode<String>, BEncode<?>> map = value;
		return map.keySet().stream().filter(new Predicate<BEncode<String>>() {
			@Override
			public boolean test(BEncode<String> key) {
				return key.value().equals(keyName);
			}
		}).map(new Function<BEncode<String>, BEncode<?>>() {
			@Override
			public BEncode<?> apply(BEncode<String> key) {
				return map.get(key);
			}
		}).findAny();
	}
	
	
	public Stream<String> streamKey() {
		return value.keySet().stream().map(BEncode::value);
	}
	
	public Stream<String> streamBEncodeKey() {
		return value.keySet().stream().map(BEncode::bEncode);
	}
	
	
	@Override
	public String bEncode() {
		StringBuilder builder  = new StringBuilder();
		builder.append('d');
		value.forEach((k, v) -> {
			builder.append(k.bEncode());
			builder.append(v.bEncode());
		});
		builder.append('e');
		return builder.toString();
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
