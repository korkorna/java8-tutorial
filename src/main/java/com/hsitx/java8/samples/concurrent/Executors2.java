package com.hsitx.java8.samples.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Executors2 {
	public static void main(String[] args) {
//		executorSchedule();
		
//		executorDelaySchedule();
		
		executorDelayFromTaskSchedule();
		
	}

	private static void executorDelayFromTaskSchedule() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		Runnable task = () -> {
		    try {
		        TimeUnit.SECONDS.sleep(2);
		        System.out.println("Scheduling: " + System.nanoTime());
		    }
		    catch (InterruptedException e) {
		        System.err.println("task interrupted");
		    }
		};

		// 1초마다 작업이 진행되는데 scheduleAtFixedRate와 달리 이전 작업완료 후 딜레이 1초 후 작업 실행
		executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
	}

	private static void executorDelaySchedule() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		
		Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
		
		int initialDelay = 0;
		int period = 1;
		// 1초마다 작업이 진행되는데 작업 시간이 2초라면 작업이 완료전에 또다른 작업이 실행된다.
		executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
	}

	private static void executorSchedule() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
		
		Runnable task = () -> System.out.println("Scheduling " + System.nanoTime());
		ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);
		
		try {
			TimeUnit.MILLISECONDS.sleep(1337);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
		System.out.printf("Remaining Delay: %sms\n", remainingDelay);
	}
}
