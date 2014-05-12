package com.boucher.homework252.data;

public class Task {

	private int id;  // Row number in database
	private int taskNum;
	private String taskDescription; 

	public Task() { }
	
	public Task(int id, int taskNum, String description) {
		this.id = id;
		this.taskNum = taskNum;
		this.taskDescription = description;
	}
	
	public int getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}
	
	public String getDescription() {
		return taskDescription;
	}

	public void setDescription(String description) {
		this.taskDescription = description;
	}
	
    @Override
    public String toString() {
        return taskDescription;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
