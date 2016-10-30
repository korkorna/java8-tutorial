package com.hsitx.java8.samples.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Executors3 {

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		executorSubmit();
		
		executorSubmitUsingCallable();
		
		executorTimeout();
		
		executorInvokeAll();
		
		executorInvokeAny();
			
	}

	private static void executorInvokeAny() throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newWorkStealingPool();
		
		List<Callable<String>> callables = Arrays.asList(
				callable("task1", 2),
				callable("task2", 1),
				callable("task3", 3));
		
		String result = executor.invokeAny(callables);
		System.out.println(result);
	}
	
	private static Callable<String> callable(String result, long sleepSeconds) {
	    return () -> {
	        TimeUnit.SECONDS.sleep(sleepSeconds);
	        return result;
	    };
	}

	private static void executorInvokeAll() throws InterruptedException {
		ExecutorService executor = Executors.newWorkStealingPool();
		
		List<Callable<String>> callables = Arrays.asList(
				() -> "task1",
				() -> "task2",
				() -> "task3");
		
		executor
			.invokeAll(callables)
			.stream()
			.map(future -> {
				try {
					return future.get();
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			})
			.forEach(System.out::println);
	}

	private static void executorTimeout() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
		Future<Integer> future = executor.submit(()->{
			try {
				TimeUnit.SECONDS.sleep(2);
				return 123;
			} catch (InterruptedException e) {
				throw new IllegalStateException("task interrupted", e);
			}
		});
		try {
			future.get(1, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void executorSubmitUsingCallable() {
		Callable<Integer> task = () -> {
			try {
				TimeUnit.SECONDS.sleep(1);
				return 123;
			} catch (Exception e) {
				throw new IllegalStateException("task interrupted", e);
			}
		};
		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		Future<Integer> future = executor.submit(task);
		
		System.out.println("future done? " + future.isDone());
		
		Integer result = null;
		try {
			result = future.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("future done? " + future.isDone());
		System.out.print("result: " + result);
	}

	private static void executorSubmit() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(()->{
			String threadName = Thread.currentThread().getName();
			System.out.println("Hell " + threadName);
		});
		try {
		    System.out.println("attempt to shutdown executor");
		    executor.shutdown();
		    executor.awaitTermination(5, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
		    System.err.println("tasks interrupted");
		}
		finally {
		    if (!executor.isTerminated()) {
		        System.err.println("cancel non-finished tasks");
		    }
		    executor.shutdownNow();
		    System.out.println("shutdown finished");
		}
	}
}
