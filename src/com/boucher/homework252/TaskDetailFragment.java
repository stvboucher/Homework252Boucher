package com.boucher.homework252;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boucher.homework252.data.Model;
import com.boucher.homework252.data.Task;

/**
 * A fragment representing a single Task detail screen.
 * This fragment is either contained in a {@link TaskListActivity}
 * in two-pane mode (on tablets) or a {@link TaskDetailActivity}
 * on handsets.
 */
public class TaskDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private Model mModel;
    private Task mTaskItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes). 
     */
    public TaskDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mModel = Model.getInstance();

        if (getArguments().containsKey(ARG_ITEM_ID)) {
        	int listPosition = getArguments().getInt(ARG_ITEM_ID, 1);
        	mTaskItem = mModel.mTaskList.get(listPosition);        	
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_detail, container, false);

        if (mTaskItem != null) {
            ((TextView) rootView.findViewById(R.id.task_detail)).setText(mTaskItem.getDescription());
        }

        return rootView;
    }
}
