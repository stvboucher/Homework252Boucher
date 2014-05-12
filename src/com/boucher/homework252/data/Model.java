package com.boucher.homework252.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

public class Model {
	
	DatabaseHelper db = null;
    
	public List<Task> mTaskList = new ArrayList<Task>();

	// Make this class a singleton
	private static Model instance = new Model();
	private Model() { }
	public static Model getInstance() { return instance; }
	
	public int getLargestTaskNum() {
		int largestTaskNum = 0;
		List<Task> taskList = db.getAllContacts();
		Iterator<Task> it = taskList.iterator();
		
		while (it.hasNext()) {
			Task taskData = (Task)it.next();
			int currentTaskNum = taskData.getTaskNum();
			largestTaskNum = currentTaskNum > largestTaskNum ? currentTaskNum : largestTaskNum;
		}
		return largestTaskNum;
	}
	
	public void addItem(int listPosition, Task task) {	
		if (db == null) {
			Log.v("steve", "DatabaseHelper is null!");
		} else {
			if (db.addTask(task) < 0) {
				Log.v("steve", "Adding item failed");
			} else {
				mTaskList.add(task);
			}	
		}
	}
	
	public void initializeModel(Context context) {
		if (db == null) {
			db = new DatabaseHelper(context);
			List<Task> taskList = db.getAllContacts();
			Iterator<Task> it = taskList.iterator();
			while (it.hasNext()) {
				Task taskData = (Task)it.next();			
				mTaskList.add(taskData);
			}
		} else {
			Log.v("steve", "Already created DatabaseHelper");
		}
	}
	
	public void closeDatabase() {
		db.close();	
	}
	
	public void deleteItem(Task task) {
		if ((task == null) || db.deleteTask(task.getTaskNum()) < 0) {
			Log.v("steve", "Deleting item failed");
		} else {
			mTaskList.remove(task);
		}
	}
	
	public int getNumberofItems() {
		return db.getNumberofItems();
	}
}
