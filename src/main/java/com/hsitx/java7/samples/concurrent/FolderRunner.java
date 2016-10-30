package com.hsitx.java7.samples.concurrent;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class FolderRunner extends RecursiveTask<List<String>>{

	private static final long serialVersionUID = 1L;
	
	private String path;
	private String extension;

	public FolderRunner(String path, String extension) {
		super();
		this.path = path;
		this.extension = extension;
	}

	@Override
	protected List<String> compute() {
		List<String> list = new ArrayList<>();
		List<FolderRunner> tasks = new ArrayList<>();
		
		File file = new File(path);
		File childFiles[] = file.listFiles(f -> {
			return f.isDirectory() | f.getName().endsWith(extension);
		});
		
		if (childFiles != null) {
			for (File childFile : childFiles) {
				if (childFile.isDirectory()) {
					FolderRunner task = new FolderRunner(childFile.getAbsolutePath(), extension);
					task.fork();
					tasks.add(task);
				} else {
					list.add(file.getAbsolutePath());
				}
			}
		}
		
		if (tasks.size() > 50) {
			System.out.printf("%s : %d tasks rans.\n", file.getAbsolutePath(), tasks.size());
		}
		
		addResultFromTasks(list, tasks);
		
		return list;
	}

	private void addResultFromTasks(List<String> list, List<FolderRunner> tasks) {
		for (FolderRunner task : tasks) {
			list.addAll(task.join());
		}
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		FolderRunner log = new FolderRunner("/var/log", "log");
		FolderRunner download = new FolderRunner("/home/storage/Downloads", "tmp");
		FolderRunner workspace = new FolderRunner("/home/storage/workspace", "java");
		
		ForkJoinPool pool = new ForkJoinPool();
		pool.execute(log);
		pool.execute(download);
		pool.execute(workspace);
		
		do {
			 System.out.printf("******************************************\n");
	         System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
	         System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
	         System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
	         System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
	         System.out.printf("******************************************\n");
	         
	         try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while ((!log.isDone()) | (!download.isDone()) | (!workspace.isDone()));
		
		stop(pool);
		
		List<String> results;
		results = log.join();
		System.out.printf("Log: %d files found.\n", results.size());
		results = download.join();
		System.out.printf("Download: %d files found.\n", results.size());
		results = workspace.join();
		System.out.printf("Workspace: %d files found.\n", results.size());
		
		long end = System.currentTimeMillis();
		System.out.println("Duration: " + (end - start));
		
	}

	private static void stop(ForkJoinPool pool) {
		try {
			pool.shutdown();
			pool.awaitQuiescence(60, TimeUnit.SECONDS);
		} finally {
			if (!pool.isTerminated()) {
				System.err.println("killing non-finished tasks");
			}
			pool.shutdownNow();
		}
	}

}
