package com.hsitx.java8.samples.stream;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Stream5 {
	public static void main(String[] args) {
		List<String> strings = 
				Arrays.asList("d2", "a2", "b1", "b3", "c");
		
//		test1(strings);
//		test2(strings);
//		test3(strings);
//		test4(strings);
//		test5(strings);
//		test6(strings);
//		test7(strings);
//		test8(strings);
	}

	private static void test1(List<String> strings) {
		strings
         .stream()
         .filter(s -> {
             System.out.println("filter:  " + s);
             return true;
         })
         .forEach(s -> System.out.println("forEach: " + s));
	}

	private static void test2(List<String> strings) {
		strings
        .stream()
        .map(s -> {
            System.out.println("map:     " + s);
            return s.toUpperCase();
        })
        .filter(s -> {
            System.out.println("filter:  " + s);
            return s.startsWith("A");
        })
        .forEach(s -> System.out.println("forEach: " + s));
	}

	private static void test3(List<String> strings) {
		strings
			.stream()
			.filter(s -> {
				System.out.println("filter: " + s);
				return s.startsWith("a");
			})
			.map(s -> {
				System.out.println("map: " + s);
				return s.toUpperCase();
			})
			.forEach(s -> System.out.println("forEach: " + s));
	}

	// sorted = horizontal
	private static void test4(List<String> strings) {
		strings
			.stream()
			.sorted((s1, s2) -> {
				System.out.printf("sort: %s; %s\n", s1, s2 );
				return s1.compareTo(s2);
			})
			.filter(s -> {
				System.out.println("filter: " + s);
				return s.toLowerCase().startsWith("a");
			})
			.map(s -> {
				System.out.println("map: " + s);
				return s.toUpperCase();
			})
			.forEach(s -> System.out.println("foreach: " + s));
	}

	private static void test5(List<String> strings) {
		strings
			.stream()
			.filter(s -> {
				System.out.println("filter: " + s);
				return s.toLowerCase().startsWith("a");
			})
			.sorted((s1, s2) -> {
				System.out.printf("sort: %s; %s\n", s1, s2);
				return s1.compareTo(s2);
			})
			.map(s -> {
				System.out.println("map: " + s);
				return s.toUpperCase();
			})
			.forEach(s -> System.out.println("foreach: " + s));
	}


	// short-circuit
	private static void test6(List<String> strings) {
		strings
			.stream()
			.map(s -> {
				System.out.println("map: " + s);
				return s.toUpperCase();
			})
			.anyMatch(s -> {
				System.out.println("anyMatch: " + s);
				return s.startsWith("A");
			});
	}

	// stream has already been operated upon or closed
	private static void test7(List<String> strings) {
		Stream<String> stream = strings
				.stream()
				.filter(s -> s.startsWith("a"));
		
		System.out.println(stream.anyMatch(s -> true));
		System.out.println(stream.noneMatch(s -> true));
	}

	private static void test8(List<String> strings) {
		Supplier<Stream<String>> streamSupplier =
				() -> strings
					.stream()
					.filter(s -> s.startsWith("a"));
				
		System.out.println(streamSupplier.get());
		System.out.println(streamSupplier.get().anyMatch(s -> true));
		System.out.println(streamSupplier.get().noneMatch(s -> true));
	}
}
