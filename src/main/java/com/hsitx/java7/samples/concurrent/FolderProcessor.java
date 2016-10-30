package com.hsitx.java7.samples.concurrent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderProcessor extends RecursiveTask<List<String>>{
	private static final long serialVersionUID = 1L;
	//This attribute will store the full path of the folder this task is going to process.
	private final String      path;
	//This attribute will store the name of the extension of the files this task is going to look for.
	private final String      extension;
	
	public FolderProcessor(String path, String extension) {
		super();
		this.path = path;
		this.extension = extension;
	}

	@Override
	protected List<String> compute() {
		//List to store the names of the files stored in the folder.
		List<String> list = new ArrayList<String>();
		//FolderProcessor tasks to store the subtasks that are going to process the subfolders stored in the folder
		List<FolderProcessor> tasks = new ArrayList<FolderProcessor>();
		
		//Get the content of the folder
		File file = new File(path);
		File content[] = file.listFiles();
		
		//For each element in the folder, if there is a subfolder, create a new FolderProcessor object
		//and execute it asynchronously using the fork() method.
		
		if (content != null) {
			for (File childFile : content) {
				if (childFile.isDirectory()) {
					FolderProcessor task =  new FolderProcessor(childFile.getAbsolutePath(), extension);
					task.fork();
					tasks.add(task);
				}
				//Otherwise, compare the extension of the file with the extension you are looking for using the checkFile() method
				//and, if they are equal, store the full path of the file in the list of strings declared earlier.
				else {
					if (checkFile(childFile.getName())) {
						list.add(childFile.getAbsolutePath());
					}
				}
			}
		}
		
		//If the list of the FolderProcessor subtasks has more than 50 elements,
		//write a message to the console to indicate this circumstance.
		if (tasks.size() > 50) {
			System.out.printf("%s : %d tasks ran.\n", file.getAbsolutePath(), tasks.size());
		}
		
		//add to the list of files the results returned by the subtasks launched by this task.
		addResultFromTasks(list, tasks);
		
		return list;
	}

	private void addResultFromTasks(List<String> list, List<FolderProcessor> tasks) {
		for (FolderProcessor task : tasks) {
			list.addAll(task.join());
		}
	}

	private boolean checkFile(String name) {
		return name.endsWith(extension);
	}

}
