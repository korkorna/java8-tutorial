package com.hsitx.java8.samples.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Lambda1 {
	public static void main(String[] args) {
		List<String> names = Arrays.asList("peter", "anna", "mika", "xenia");
		
		Collections.sort(names, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return b.compareTo(a);
			}

		});
		
		System.out.println(names);
		
		Collections.sort(names, (String a, String b) -> {
			return a.compareTo(b);
		});
		
		System.out.println(names);
		
		Collections.sort(names, (String a, String b) -> b.compareTo(a));
		
		System.out.println(names);
		
		Collections.sort(names, (a, b) -> a.compareTo(b));
		
		System.out.println(names);
	}
}
