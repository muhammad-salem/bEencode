package org.udp.torrent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.udp.torrent.util.BEParser;
import org.udp.torrent.vlaue.BList;
import org.udp.torrent.vlaue.BMap;

public class BEParserTest {
	
	@Test
	public void test1() {
		
		List<Object> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("i", 1);
		
		
		
		
		BList bList = BEParser.parse(list);
		System.out.println(bList);
		System.out.println(bList.bEncode());
		
		BMap bMap = BEParser.parse(map);
		System.out.println(bMap);
		System.out.println(bMap.bEncode());
		
		
	}

}
