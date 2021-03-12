package com.cs250.joanne.myfragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cs250.joanne.myfragments.dummy.StatContent;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// HELLO

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment list;
    private Fragment statistics;
    private Fragment completedTasksFrag;
    private FragmentTransaction transaction;
    protected TaskAdapter taskAdapter;
    protected StatisticsRecyclerViewAdapter statisticsAdapter;
    public static List<StatContent.StatItem> statItems;
    protected TaskAdapter completedTasksAdapter;
    public static ArrayList<Task> myTasks;
    public static ArrayList<Task> completedTasks;
    //private SharedPreferences myPrefs;
    private Integer numDoneByDeadline;
    private Integer numDoneAfterDue;
    private Integer numPastDue;
    private Integer numToBeDone;
    private Integer totalTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Context context = getApplicationContext(); // app level storage
        //myPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // create arraylist of tasks
        myTasks = new ArrayList<>();
        completedTasks = new ArrayList<>();
        statItems = StatContent.ITEMS;

        // make array adapter to bind arraylist to listview with custom item layout
        taskAdapter = new TaskAdapter(this, R.layout.item_task, myTasks); // create taskAdapter
        completedTasksAdapter = new TaskAdapter(this, R.layout.item_task, completedTasks);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        list = new ListFrag();
        statistics = new StatisticsFrag();
        completedTasksFrag = new CompletedTasksFrag();

        statisticsAdapter = new StatisticsRecyclerViewAdapter(statItems, 0, (StatisticsFrag) statistics,this);

        /*numDoneByDeadline = myPrefs.getInt("DONE_BY_DEADLINE_KEY", 0);
        numDoneAfterDue = myPrefs.getInt("DONE_AFTER_DUE_KEY", 0);
        numPastDue = myPrefs.getInt("PAST_DUE_KEY", 0);
        numToBeDone = myPrefs.getInt("TO_BE_DONE_KEY", 0);
        totalTasks = myPrefs.getInt("TOTAL_TASKS_KEY", 0);*/

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, list).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        if (id == R.id.addtask) {
            Intent intent = new Intent(this, AddTask.class);
            startActivityForResult(intent, 1);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode) {
            taskAdapter.notifyDataSetChanged();
            statisticsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onStart() {
        Log.d ("Main", "onStart");
        super.onStart();

        /*numDoneByDeadline = myPrefs.getInt("DONE_BY_DEADLINE_KEY", 0);
        numDoneAfterDue = myPrefs.getInt("DONE_AFTER_DUE_KEY", 0);
        numPastDue = myPrefs.getInt("PAST_DUE_KEY", 0);
        numToBeDone = myPrefs.getInt("TO_BE_DONE_KEY", 0);
        totalTasks = myPrefs.getInt("TOTAL_TASKS_KEY", 0);*/
    }

    @Override
    protected void onResume() {
        Log.d ("Main", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d ("Main", "onPause");
        /*SharedPreferences.Editor peditor = myPrefs.edit();
        peditor.putInt("DONE_BY_DEADLINE_KEY", numDoneByDeadline);
        peditor.putInt("DONE_AFTER_DUE_KEY", numDoneAfterDue);
        peditor.putInt("PAST_DUE_KEY", numPastDue);
        peditor.putInt("TO_BE_DONE_KEY", numToBeDone);
        peditor.putInt("TOTAL_TASKS_KEY", totalTasks);
        peditor.apply();*/

        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d ("Main", "onStop");
        /*SharedPreferences.Editor peditor = myPrefs.edit();
        peditor.putInt("DONE_BY_DEADLINE_KEY", numDoneByDeadline);
        peditor.putInt("DONE_AFTER_DUE_KEY", numDoneAfterDue);
        peditor.putInt("PAST_DUE_KEY", numPastDue);
        peditor.putInt("TO_BE_DONE_KEY", numToBeDone);
        peditor.putInt("TOTAL_TASKS_KEY", totalTasks);
        peditor.apply();*/

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d ("Main", "onDestroy");
        // do stuff here
        Log.d("onDestroy", "exit 3");
        super.onDestroy();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.list_frag) {
            transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, list);
            transaction.addToBackStack(null);

// Commit the transaction
            transaction.commit();
        } else if (id == R.id.statistics_frag) {
            transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, statistics);
            transaction.addToBackStack(null);

// Commit the transaction
            transaction.commit();
        } else if (id == R.id.completedtasks_frag) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, completedTasksFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // Set the tile of the toolbar.
    public void setToolBarTitle(Toolbar toolbar, String title){
        toolbar.setTitle(title);
    }



}
