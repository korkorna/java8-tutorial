package com.hsitx.java8.samples.stream;

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Stream4 {

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			if (i % 2 == 0) {
				System.out.print(i + ", ");
			}
		}
		System.out.println();
		IntStream.range(0, 10)
			.forEach((i) -> {
				if (i % 2 ==0) {
					System.out.print(i + ", ");
				}
			});
		System.out.println();
		
		IntStream.range(0, 10)
			.filter(i -> i % 2 == 0)
			.forEach(i -> System.out.print(i + ", "));
		
		// ???
		OptionalInt reduced1 = 
				IntStream.range(0, 10)
					.reduce((a, b) -> a +b);
		System.out.println(reduced1.getAsInt());
		
		int reduced2 = 
				IntStream.range(0, 10)
					.reduce(7, (a, b) -> a + b);
		System.out.println(reduced2);
	}
}
