package org.torrent.bEncode.vlaue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BList implements BEncode.BEncodeList {
	
	private List<BEncode<?>> value;
	
	
	public BList(List<BEncode<?>> value){
		this.value = value;
	}
	
	public BList(byte[] bytes) throws IOException {
		this(new ByteArrayInputStream(bytes));
	}

	public BList(InputStream in) throws IOException {
		this.value = new ArrayList<>();
		initList(in);
	}
	
	@Override
	public BEncode<?> get(Object index) {
		return value().get((int)index);
	}

	@Override
	public List<? extends BEncode<?>> value() {
		return value;
	}
	
	private void initList(InputStream in) throws IOException {
		char c;
		while ((c = (char) in.read()) != -1) {
			if (c == 'i') {
				this.value.add(new BLong(in));
			} else if( c >= '0' && c <= '9'){
				// string value
				this.value.add(new BString(in, c));
			} else if (c == 'l') {
				this.value.add(new BList(in));
			} else if (c == 'd') {
				this.value.add(new BMap(in));
			} else if (c == ':') {
				continue;
			} else if (c == 'e') {
				break;
			}
			else {
				System.out.println("in available " + in.available());
				break;
			}
		}
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
