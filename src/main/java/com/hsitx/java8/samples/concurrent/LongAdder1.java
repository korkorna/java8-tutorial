package com.hsitx.java8.samples.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

public class LongAdder1 {
	// 메모리를 많이 사용하는 단점
	public static void main(String[] args) {
		LongAdder adder = new LongAdder();
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		IntStream.range(0, 1000)
			.forEach(i -> executor.submit(adder::increment));
		
		ConcurrentUtils.stop(executor);
		
		System.out.println(adder.sumThenReset());
	}
}
