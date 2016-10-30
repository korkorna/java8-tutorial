package com.hsitx.java8.samples.concurrent;

import java.util.concurrent.TimeUnit;

public class Thread1 {

	public static void main(String[] args) {
		printThreadName();
		
		printTheadNameAndSleep();
	}

	private static void printTheadNameAndSleep() {
		Runnable runnable = () -> {
			try {
				String name = Thread.currentThread().getName();
				System.out.println("Foo " + name);
				TimeUnit.SECONDS.sleep(1);  // <== Thread.sleep(1000);
				System.out.println("Bar " + name);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		
		Thread thread = new Thread(runnable);
		thread.start();
	}

	private static void printThreadName() {
		Runnable task = () -> {
			String threadName = Thread.currentThread().getName();
			System.out.println("Hello, " + threadName);
		};
		
		task.run();
		
		Thread thread = new Thread(task);
		thread.start();
		
		System.out.println("Done");
	}
}
