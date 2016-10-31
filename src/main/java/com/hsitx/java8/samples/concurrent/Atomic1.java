package com.hsitx.java8.samples.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Atomic1 {
	public static void main(String[] args) {
		executeIncrementGet();
		
		executeUpdateGet();
		
		AtomicInteger atomicInt = new AtomicInteger(0);
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		IntStream.range(0, 4)
			.forEach(i -> {
				Runnable task = () -> 
					atomicInt.accumulateAndGet(i, (n, m) -> n + m);
				executor.submit(task);
			});
		
		ConcurrentUtils.stop(executor);
		
		System.out.println(atomicInt.get());    // => 499500
	}

	private static void executeUpdateGet() {
		AtomicInteger atomicInt = new AtomicInteger(0);
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		IntStream.range(0,  1000)
			.forEach(i -> {
				Runnable task = () -> 
					atomicInt.updateAndGet(n -> n + 2);
				executor.submit(task);
			});
		
		ConcurrentUtils.stop(executor);
		
		System.out.println(atomicInt.get());
	}

	private static void executeIncrementGet() {
		AtomicInteger atomicInt = new AtomicInteger(0);
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		IntStream.range(0, 1000)
			.forEach(i -> executor.submit(atomicInt::incrementAndGet));
		
		ConcurrentUtils.stop(executor);
		
		System.out.println(atomicInt.get());
	}
}
