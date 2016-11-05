package com.hsitx.java8.samples.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Stream1 {

	public static void main(String[] args) {
		List<String> stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");
		
		// Filter
		stringCollection
			.stream()
			.filter((s) -> s.startsWith("a"))
			.forEach(System.out::println);		// "aaa2", "aaa1"	
		
		// Sorted
		stringCollection
			.stream()
			.sorted()
			.filter((s) -> s.startsWith("a"))
			.forEach(System.out::println);		// "aaa1", "aaa2"
		
		System.out.println(stringCollection);	// ddd2, aaa2, bbb1, aaa1, bbb3, ccc, bbb2, ddd1
		
		// Map
		stringCollection
			.stream()
			.map(String::toUpperCase)
			.sorted((a, b) -> b.compareTo(a))
			.forEach(System.out::println);		// DDD2, DDD1, CCC, BBB3, BBB2, BBB1, AAA2, AAA1
		
		// Match
		boolean anyStartsWithA = 
				stringCollection
					.stream()
					.anyMatch((s) -> s.startsWith("a"));
		
		System.out.println(anyStartsWithA);
		
		boolean allStartsWithB =
				stringCollection
					.stream()
					.allMatch((s) -> s.startsWith("a"));
		System.out.println(allStartsWithB);
		
		boolean noneStartsWithZ =
				stringCollection
					.stream()
					.noneMatch((s) -> s.startsWith("z"));
		System.out.println(noneStartsWithZ);
		
		// Count
		long startsWithB =
			stringCollection
				.stream()
				.filter((s) -> s.startsWith("b"))
				.count();
		System.out.println(startsWithB);
		
		// Reduce
		Optional<String> reduced = 
				stringCollection
					.stream()
					.map(String::toUpperCase)
					.sorted()
					.reduce((s1, s2) -> s1 + "#" + s2);
		reduced.ifPresent(System.out::println);
		
	}
}
