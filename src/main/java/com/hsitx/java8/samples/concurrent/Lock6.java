package com.hsitx.java8.samples.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

public class Lock6 {
	private static int count = 0;
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		StampedLock lock = new StampedLock();
		
		Runnable runable = () -> {
			String name = Thread.currentThread().getName();
			System.out.println(name + " readLock locking ");
			long stamp = lock.readLock();
			System.out.println(name + " readLock locked ");
			try {
				if (count == 0) {
					stamp = lock.tryConvertToWriteLock(stamp);
					System.out.println(name + " tryConvertToWriteLock " + stamp);
					if (stamp == 0L) {
						System.out.println("Could not convert to write lock");
						stamp = lock.writeLock();
						System.out.println("write locked");
						ConcurrentUtils.sleep(1);
					}
					count = 23;
				}
				System.out.println(count);
				ConcurrentUtils.sleep(1);
			} finally {
				lock.unlock(stamp);
				System.out.println(name + " unlocked " + stamp);
			}
		};
		
		executor.submit(runable);
		executor.submit(runable);
		
		ConcurrentUtils.stop(executor);
	}
}
