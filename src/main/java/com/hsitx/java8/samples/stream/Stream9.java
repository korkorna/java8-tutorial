package com.hsitx.java8.samples.stream;

import java.util.Arrays;

public class Stream9 {
	public static void main(String[] args) {
		Arrays.asList("a1", "a2", "b1", "c2", "c1")
			.stream()
			.filter(s -> s.startsWith("c"))
			.map(String::toUpperCase)
			.sorted()
			.forEach(System.out::println);
	}
}
