package com.hsitx.java8.samples.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Synchronized1 {

	private static int count = 0;
	
	public static void main(String[] args) {
		executorNonSync();
		
		System.out.println("non sync: " + count);
		
		count = 0;
		executorSync();
		
		System.out.println("sync: " + count);
	}

	private static void executorSync() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		IntStream.range(0, 10000)
			.forEach(i -> executor.submit(Synchronized1::incrementSync));
		
		ConcurrentUtils.stop(executor);
	}

	private static void executorNonSync() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		IntStream.range(0, 10000)
	    	.forEach(i -> executor.submit(Synchronized1::increment));
		
		ConcurrentUtils.stop(executor);
	}
	
	private static void increment() {
		count = count + 1;
	}
	
	private static synchronized void incrementSync() {
		count = count + 1;
	}
	
}
