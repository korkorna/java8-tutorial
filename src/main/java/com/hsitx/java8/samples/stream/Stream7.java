package com.hsitx.java8.samples.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Stream7 {
	static class Foo {
		private String name;
		private List<Bar> bars;
		
		public Foo(String name) {
			this.name = name;
			this.bars = new ArrayList<>();
		}

		public String getName() {
			return name;
		}

		public List<Bar> getBars() {
			return bars;
		}
	}
	
	static class Bar {
		private String name;

		public Bar(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	
	public static void main(String[] args) {
		List<Foo> foos = new ArrayList<>();
		
		IntStream
			.range(1, 4)
			.forEach(i -> foos.add(new Foo("Foo"+i)));
		
		foos.forEach(f -> {
			IntStream
				.range(1, 4)
				.forEach(i -> f.getBars().add(new Bar("Bar" + i + " <- " + f.getName())));
		});
		
		foos.stream()
			.flatMap(f -> f.bars.stream())
			.forEach(b -> System.out.println(b.getName()));
	}
}
