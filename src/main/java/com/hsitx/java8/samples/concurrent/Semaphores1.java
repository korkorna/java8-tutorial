package com.hsitx.java8.samples.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Semaphores1 {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		
		Semaphore semaphore = new Semaphore(5);
		
		Runnable longRunningTask = () -> {
			boolean permit = false;
			try {
				permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);

				if (permit) {
					System.out.println("Semaphore acquired " + semaphore.availablePermits());
					ConcurrentUtils.sleep(5);
				} else {
					System.out.println("Colud not acquire semaphore");
				}
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			} finally {
				if (permit) {
					semaphore.release();
				}
			}
		};
		
		IntStream.range(0, 10)
			.forEach(i -> executor.submit(longRunningTask));
		
		ConcurrentUtils.stop(executor);
	}
}
