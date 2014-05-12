package com.boucher.homework252.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.boucher.homework252.data.Task;

public class DatabaseHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    public static final String DATABASE_NAME = "tasks";
 
    // Task table name
    private static final String TABLE_NAME = "task";
 
    // Task Table Columns names
    private static final String TABLE_ROW_ID = "id";
    private static final String TABLE_ROW_TASK_NUM = "num"; 
    private static final String TABLE_ROW_TASK_DESCRIPTION = "desc"; 
    private static final String CREATE_TASK_TABLE = 
            "CREATE TABLE " + 
            TABLE_NAME + " (" + 
            TABLE_ROW_ID + " INTEGER PRIMARY KEY, " +
            TABLE_ROW_TASK_NUM + " TEXT," +
            TABLE_ROW_TASK_DESCRIPTION + " TEXT" + ");";
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
      Log.v("steve", "About to execute db.execSQL()...");

   	  db.execSQL(CREATE_TASK_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
 
        // Create tables again
        onCreate(db);
    }

    
    public int addTask (Task task) { 	
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	if (db == null) {
            Log.v("steve", "addTask(): db is null!");
    		return -2;
    	}
    	
        ContentValues values = new ContentValues(); 
        values.put(TABLE_ROW_TASK_NUM, Integer.toString(task.getTaskNum()));
        values.put(TABLE_ROW_TASK_DESCRIPTION, task.getDescription());
        // Inserting Row 
        long res = db.insert(TABLE_NAME, null, values);
        Log.v("steve", "DB addTask(): RESULT is " + res);  
 //       db.close(); // Closing database connection      
        return (int)res;
    }
       
    // Return all tasks from database in array list format of Task class type
    public List<Task> getAllContacts() {
        List<Task> taskList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
      
        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Task task = new Task();
            	// Ignore getString(0) -the row ID
            	task.setTaskNum(Integer.parseInt(cursor.getString(1))); // String to int
            	task.setDescription(cursor.getString(2));
                // Adding task to list
            	taskList.add(task);
            } while (cursor.moveToNext());
        }

        return taskList; 
    }
    
	public int getNumberofItems() {	
		String countQuery = "SELECT  * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        return count;
	}    
    
    public int deleteTask(int taskNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.delete(TABLE_NAME, TABLE_ROW_TASK_NUM + " = ?",
                new String[] { String.valueOf(taskNum) });
        Log.v("steve", "DB deleteTask(): RESULT is " + res);
//        db.close();
        return (int)res;
    }   
 }
