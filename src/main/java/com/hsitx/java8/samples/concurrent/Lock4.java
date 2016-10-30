package com.hsitx.java8.samples.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

public class Lock4 {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Map<String, String> map = new HashMap<>();
		StampedLock lock = new StampedLock();
		
		executor.submit(() -> {
			System.out.println("writeLock locked ");
			long stamp = lock.writeLock();
			try {
				ConcurrentUtils.sleep(1);
				map.put("foo", "bar");
			} finally {
				System.out.println("writeLock unlocked " + lock.isWriteLocked());
				lock.unlockWrite(stamp);
			}
		});
		
		Runnable readTask = () -> {
			System.out.println("readLock locked ");
			long stamp = lock.readLock();
			try {
				System.out.println(map.get("foo"));
				ConcurrentUtils.sleep(1);
			} finally {
				System.out.println("readLock unlocked " + lock.isReadLocked());
				lock.unlockRead(stamp);
			}
		};
		
		executor.submit(readTask);
		executor.submit(readTask);
		
		ConcurrentUtils.stop(executor);
	}
}
