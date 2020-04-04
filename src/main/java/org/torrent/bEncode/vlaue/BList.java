package org.torrent.bEncode.vlaue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class BList implements BEncode<List<? extends BEncode<?> >> {
	
	private List<BEncode<?>> value;
	
	public BList(List<BEncode<?>> value){
		this.value = value;
	}
	
	public BList(String bEncode) throws IOException {
		this(bEncode.getBytes(StandardCharsets.UTF_8));
	}
	public BList(byte[] buf) throws IOException {
		this(new ByteArrayInputStream(buf));
	}
	public BList(InputStream in) throws IOException {
		this.value = new ArrayList<>();
		initList(in);
	}

	private void initList(InputStream in) throws IOException {
		char c;
		while ((c = (char) in.read()) != -1) {
			if (c == 'i') {
				this.value.add(new BInt(in));
			} else if (c == 'l') {
				this.value.add(new BList(in));
			} else if (c == 'd') {
				this.value.add(new BMap(in));
			} else if (c == ':') {
				continue;
			} else if (c == 'e') {
				break;
			} else if( c >= '0' && c <= '9'){
				// string value
				this.value.add(new BString(in, c));
			}
			else {
				System.out.println("in available " + in.available());
				break;
			}
		}
	}
	
	@Override
	public List<BEncode<?>> value() {
		return value;
	}
	
	
	public Stream<?> streamValue() {
		return value.stream().map(BEncode::value);
	}
	
	public Stream<String> streamString() {
		return value.stream().map(BEncode::toString);
	}
	
	@Override
	public String bEncode() {
		StringBuilder builder  = new StringBuilder();
		builder.append('l');
		value.forEach((v) -> {
			builder.append(v.bEncode());
		});
		builder.append('e');
		return builder.toString();
	}
	
	@Override
	public String toString() {
		
		Iterator<BEncode<?>> i = value.iterator();
        if (! i.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
        	BEncode<?> e = i.next();
            sb.append(e.toString());
            if (! i.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
	}
	
}
