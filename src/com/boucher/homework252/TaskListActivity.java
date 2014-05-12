package com.boucher.homework252;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boucher.homework252.data.DatabaseHelper;
import com.boucher.homework252.data.Model;
import com.boucher.homework252.data.Task;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


/**
 * An activity representing a list of Tasks. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TaskDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link TaskListFragment} and the item details
 * (if present) is a {@link TaskDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link TaskListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class TaskListActivity extends FragmentActivity
        implements TaskListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Model mModel;
    private TaskListFragment mListFragment;
	private int mSelectedItemId = -1;
	private int mLargestTaskNum = -1;  
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        mModel = Model.getInstance();
        mModel.initializeModel(this);
        mLargestTaskNum = mModel.getLargestTaskNum();
               
        mListFragment = ((TaskListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.task_list));

        if (findViewById(R.id.task_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            mListFragment.setActivateOnItemClick(true);
        }
        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link TaskListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(int id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(TaskDetailFragment.ARG_ITEM_ID, id);
            TaskDetailFragment fragment = new TaskDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.task_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, TaskDetailActivity.class);
            detailIntent.putExtra(TaskDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
        mSelectedItemId = id; 
    }
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actionbar_main, menu);
		return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_add:
			int listPosition = mModel.getNumberofItems(); // Add to end of list
			mModel.addItem(listPosition, new Task(listPosition, ++mLargestTaskNum,
					                              "Task " + mLargestTaskNum));
			mListFragment.refresh(); // Redraw screen
			// Select the newly added item, if in two screen mode, so that it shows
			// in the detail view
			if (mTwoPane) {
			   mListFragment.selectNewPosition(listPosition);
			}
			break;
			
		case R.id.action_remove:
			if (mModel.getNumberofItems() == 0) {
				Log.v("steve", "Invalid remove request. No items available to delete!");
				break;
			}				
			if ((mSelectedItemId < ListView.INVALID_POSITION) || 
			    (mSelectedItemId >= mModel.getNumberofItems())) {
				mSelectedItemId = 0;
			}
			
			// Getting task item: should this be done in TaskListFragment?
			Task task = (Task) mListFragment.getListView().getAdapter().getItem(mSelectedItemId);
			mModel.deleteItem(task);			
			
			mListFragment.refresh();  // Redraw screen
			// Select new item so that detail screen doesn't show last deleted item
			if (mTwoPane) {
				mListFragment.selectNewPosition(mSelectedItemId - 1);	
			}		
			
			/* TEST CODE ONLY -- enabled as needed to remove database file
			mModel.closeDatabase();
			this.deleteDatabase(DatabaseHelper.DATABASE_NAME);
			*/			
			break; 
		}		
		return true;
    }
}
