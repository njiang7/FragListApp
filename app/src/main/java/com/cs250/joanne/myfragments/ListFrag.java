package com.cs250.joanne.myfragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Collections;


public class ListFrag extends Fragment {

    public static final int MENU_ITEM_EDITVIEW = Menu.FIRST;
    public static final int MENU_ITEM_DELETE = Menu.FIRST + 1;
    public static final int MENU_ITEM_COPY = Menu.FIRST + 2;

    private ListView myList;
    private MainActivity myact;

    Context cntx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.list_frag, container, false);

        cntx = getActivity().getApplicationContext();

        myact = (MainActivity) getActivity();

        // Sort in chronological order
        Collections.sort(myact.myTasks);
        Collections.sort(myact.completedTasks);

        myList = (ListView) myview.findViewById(R.id.mylist);
        // connect listview to the array adapter in MainActivity
//        myList.setAdapter(myact.aa);

        myList.setAdapter(myact.taskAdapter);

        myact.getSupportActionBar().setTitle("Current Tasks");
        registerForContextMenu(myList);
        // refresh view
        myact.taskAdapter.notifyDataSetChanged();

        // program a short click on the list item
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Task task = (Task) parent.getAdapter().getItem(position);
                new AlertDialog.Builder(getContext())
                        .setTitle(task.getName())
                        .setMessage("Due: " + task.formateDeadline())
                        .setPositiveButton("Mark Completed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Mark task as completed
                                Calendar cal = Calendar.getInstance();
                                int year = cal.get(Calendar.YEAR);
                                int month = cal.get(Calendar.MONTH) + 1;
                                int day = cal.get(Calendar.DAY_OF_MONTH);
                                int date = year*10000 + month*100 + day; // get current date
                                task.setCompleted(true);
                                task.setDateCompleted(date);
                                myact.completedTasks.add(new Task(task));
                                myact.myTasks.remove(task);
                                myact.taskAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", null)
//                        .setIcon(android.R.drawable.checkbox_on_background)
                        .show();
            }
        });

        return myview;
    }

    // for a long click on a menu item use ContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // create menu in code instead of in xml file (xml approach preferred)
        menu.setHeaderTitle("Select Item");

        // Add menu items
        menu.add(0, MENU_ITEM_EDITVIEW, 0, R.string.menu_editview);
        menu.add(0, MENU_ITEM_DELETE, 0, R.string.menu_delete);
        menu.add(0, MENU_ITEM_COPY, 0, "Copy");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        AdapterView.AdapterContextMenuInfo menuInfo;
        menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = menuInfo.position; // position in array adapter

        switch (item.getItemId()) {
            case MENU_ITEM_EDITVIEW: {

                // launch an intent to start a addtask activity
                Toast.makeText(cntx, "edit request",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), TaskUpdate.class);
                intent.putExtra("taskIndex", index); // pass the index of the object in mytasks to activity
                intent.putExtra("whichArray", 0); // 0 for myTasks arraylist
                startActivityForResult(intent, 1); // use this to get a result to update taskadapter notifydatasetchanged
//                startActivity(intent);



                return false;
            }
            case MENU_ITEM_DELETE: {
                myact.myTasks.remove(index);
                Collections.sort(myact.myTasks);
                Toast.makeText(cntx, "job " + index + " deleted",
                        Toast.LENGTH_SHORT).show();
                // refresh view
                myact.taskAdapter.notifyDataSetChanged();
                myact.statisticsAdapter.notifyDataSetChanged();
                return true;
            }
            case MENU_ITEM_COPY: {
                Toast.makeText(cntx, "copy task",
                        Toast.LENGTH_SHORT).show();
                Task taskCopy = new Task(MainActivity.myTasks.get(index));
                taskCopy.setName(taskCopy.getName() + " (Copy)");
                MainActivity.myTasks.add(taskCopy);
                Collections.sort(myact.myTasks);
                myact.taskAdapter.notifyDataSetChanged();
                myact.statisticsAdapter.notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode) {
            myact.taskAdapter.notifyDataSetChanged();
            myact.statisticsAdapter.notifyDataSetChanged();
        }

    }

    // Called at the start of the visible lifetime.
    @Override
    public void onStart(){
        super.onStart();
        Collections.sort(myact.myTasks);
        Log.d ("Other Fragment2", "onStart");
        // Apply any required UI change now that the Fragment is visible.
    }

    // Called at the start of the active lifetime.
    @Override
    public void onResume(){
        super.onResume();
        Log.d ("Other Fragment", "onResume");
        // Resume any paused UI updates, threads, or processes required
        // by the Fragment but suspended when it became inactive.
    }

    // Called at the end of the active lifetime.
    @Override
    public void onPause(){
        Log.d ("Other Fragment", "onPause");
        // Suspend UI updates, threads, or CPU intensive processes
        // that don't need to be updated when the Activity isn't
        // the active foreground activity.
        // Persist all edits or state changes
        // as after this call the process is likely to be killed.
        super.onPause();
    }

    // Called to save UI state changes at the
    // end of the active lifecycle.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d ("Other Fragment", "onSaveInstanceState");
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate, onCreateView, and
        // onCreateView if the parent Activity is killed and restarted.
        super.onSaveInstanceState(savedInstanceState);
    }

    // Called at the end of the visible lifetime.
    @Override
    public void onStop(){
        Log.d ("Other Fragment", "onStop");
        // Suspend remaining UI updates, threads, or processing
        // that aren't required when the Fragment isn't visible.
        super.onStop();
    }

    // Called when the Fragment's View has been detached.
    @Override
    public void onDestroyView() {
        Log.d ("Other Fragment", "onDestroyView");
        // Clean up resources related to the View.
        super.onDestroyView();
    }

    // Called at the end of the full lifetime.
    @Override
    public void onDestroy(){
        Log.d ("Other Fragment", "onDestroy");
        // Clean up any resources including ending threads,
        // closing database connections etc.
        super.onDestroy();
    }

    // Called when the Fragment has been detached from its parent Activity.
    @Override
    public void onDetach() {
        Log.d ("Other Fragment", "onDetach");
        super.onDetach();
    }
}
