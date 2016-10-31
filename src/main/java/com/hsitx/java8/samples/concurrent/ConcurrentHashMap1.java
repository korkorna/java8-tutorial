package com.hsitx.java8.samples.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

public class ConcurrentHashMap1 {
	public static void main(String[] args) {
		System.out.println(ForkJoinPool.getCommonPoolParallelism());  // 3
		//-Djava.util.concurrent.ForkJoinPool.common.parallelism=5 
		
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		map.put("foo", "bar");
		map.put("han", "solo");
		map.put("r2", "d2");
		map.put("c3", "p0");
		
		// parallel operations: foreach, search, reduce
		
		map.forEach(1, (k, v) -> 
				System.out.printf("key: %s; value: %s; thread: %s\n", 
						k, v, Thread.currentThread().getName()));
		// key: r2; value: d2; thread: main
		// key: foo; value: bar; thread: ForkJoinPool.commonPool-worker-1
		// key: han; value: solo; thread: ForkJoinPool.commonPool-worker-2
		// key: c3; value: p0; thread: main
		
		System.out.println("///////////////////////////////////////");
		
		String result = map.search(1, (k, v) -> {
			System.out.println(Thread.currentThread().getName());
			if ("foo".equals(k)) {
				return v;
			}
			return null;
		});
		
		System.out.println("Result: " + result);
		
		// ForkJoinPool.commonPool-worker-2
		// main
		// ForkJoinPool.commonPool-worker-3
		// Result: bar
		
		System.out.println("///////////////////////////////////////");
		
		result = map.searchValues(1, v -> {
			System.out.println(Thread.currentThread().getName());
			if (v.length() > 3) {
				return v;
			}
			return null;
		});
		
		System.out.println("Result: " + result);
		
		// ForkJoinPool.commonPool-worker-2
		// main
		// main
		// ForkJoinPool.commonPool-worker-1
		// Result: solo
		
		System.out.println("///////////////////////////////////////");
		
		result = map.reduce(1, 
			(k, v) -> {
				System.out.println("Transform: " + Thread.currentThread().getName());
				return k + "=" + v;
			}, 
			(s1, s2) -> {
				System.out.println("Reduce: " + Thread.currentThread().getName());
				return s1 + ", " + s2;
			});
		
		System.out.println("Result: " + result);
	}
}
