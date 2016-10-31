package com.hsitx.java8.samples.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentMap1 {
	public static void main(String[] args) {
		ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
		map.put("foo", "bar");
		map.put("han", "solo");
		map.put("r2", "d2");
		map.put("c3", "p0");
		
		map.forEach((k, v) -> System.out.printf("%s = %s\n", k, v));
		
		String value = map.putIfAbsent("c3", "p1");	// 값이 없을 경우 대입
		System.out.println(value);	// p0
		
		value = map.getOrDefault("hi", "there");
		System.out.println(value);	// there
		
		map.replaceAll((k, v) -> "r2".equals(k) ? "d3" : v);
		System.out.println(map.get("r2"));		//d3
		
		map.compute("foo", (k, v) -> v + v);
		System.out.println(map.get("foo"));  	//barbar
		
		map.merge("foo", "boo", (oldVal, newVal) -> newVal + " was " + oldVal);
		System.out.println(map.get("foo"));  	//boo was barbar
	}
}
