package com.hsitx.java8.samples.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Lock3 {
	
	/*
	 * READ 락이 걸려도 WRITE 락은 접근이 가능함.
	 * WRITE 락이 걸려도 READ 락은 접근이 가능함.
	 */
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Map<String, String> map = new HashMap<>();
		ReadWriteLock lock = new ReentrantReadWriteLock();
		
		Runnable readTask = () -> {
			System.out.println("readLock locked");
			lock.readLock().lock();
			try {
				System.out.println(map.get("foo"));
				ConcurrentUtils.sleep(1);
			} finally {
				System.out.println("readLock unlocked "  + lock.readLock());
				lock.readLock().unlock();
			}
		};
		
		executor.submit(() -> {
			lock.writeLock().lock();
			System.out.println("writeLock locked");
			try {
				ConcurrentUtils.sleep(1);
				map.put("foo", "bar1");
			} finally {
				System.out.println("writeLock unlocked " + lock.writeLock());
				lock.writeLock().unlock();
			}
		});
		
		executor.submit(readTask);
		executor.submit(readTask);
		
		ConcurrentUtils.stop(executor);
		
	}
}
