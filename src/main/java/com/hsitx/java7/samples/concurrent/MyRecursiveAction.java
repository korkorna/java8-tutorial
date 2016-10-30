package com.hsitx.java7.samples.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class MyRecursiveAction extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	
	private long workLoad = 0;
	
	public MyRecursiveAction(long workLoad) {
		super();
		this.workLoad = workLoad;
	}

	@Override
	protected void compute() {
		// if work is above threshold, break tasks up into smaller tasks
		if (this.workLoad > 16) {
			System.out.println("Splitting workLoad :" + this.workLoad);
			
			List<MyRecursiveAction> subtasks = new ArrayList<>();
			
			subtasks.addAll(createSubtasks());
			
			for (MyRecursiveAction subtask : subtasks) {
				subtask.fork();
			}
		} else {
			System.out.println("Doing workLoad myself : " + this.workLoad);
		}
	}

	private Collection<? extends MyRecursiveAction> createSubtasks() {
		List<MyRecursiveAction> subtasks = new ArrayList<>();
		
		MyRecursiveAction subtask1 = new MyRecursiveAction(this.workLoad / 2);
		MyRecursiveAction subtask2 = new MyRecursiveAction(this.workLoad / 2);
		
		subtasks.add(subtask1);
		subtasks.add(subtask2);
		
		return subtasks;
	}
	
	public static void main(String[] args) {
		MyRecursiveAction action = new MyRecursiveAction(24);
		
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		forkJoinPool.invoke(action);
	}

}
