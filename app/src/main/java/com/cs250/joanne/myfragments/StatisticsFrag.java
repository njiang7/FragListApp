package com.cs250.joanne.myfragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.PresetReverb;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs250.joanne.myfragments.dummy.StatContent;


/**
 * A fragment representing a list of Items.
 */
public class StatisticsFrag extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private int currentDate;
    private MainActivity mainActivity;
    private SharedPreferences myPrefs;
    public static Integer numDoneByDeadline;
    public static Integer numDoneAfterDue;
    public static Integer numPastDue;
    public static Integer numToBeDone;
    public static Integer totalTasks;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StatisticsFrag() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StatisticsFrag newInstance(int columnCount) {
        StatisticsFrag fragment = new StatisticsFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        mainActivity = (MainActivity) getActivity();
        myPrefs = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        numDoneByDeadline = myPrefs.getInt("DONE_BY_DEADLINE_KEY", 0);
        numDoneAfterDue = myPrefs.getInt("DONE_AFTER_DUE_KEY", 0);
        numPastDue = myPrefs.getInt("PAST_DUE_KEY", 0);
        numToBeDone = myPrefs.getInt("TO_BE_DONE_KEY", 0);
        totalTasks = myPrefs.getInt("TOTAL_TASKS_KEY", 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivity.getSupportActionBar().setTitle("Statistics");
        View view = inflater.inflate(R.layout.statistics_frag, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(mainActivity.statisticsAdapter);
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode) {
            mainActivity.statisticsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        numDoneByDeadline = myPrefs.getInt("DONE_BY_DEADLINE_KEY", 0);
        numDoneAfterDue = myPrefs.getInt("DONE_AFTER_DUE_KEY", 0);
        numPastDue = myPrefs.getInt("PAST_DUE_KEY", 0);
        numToBeDone = myPrefs.getInt("TO_BE_DONE_KEY", 0);
        totalTasks = myPrefs.getInt("TOTAL_TASKS_KEY", 0);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {

        SharedPreferences.Editor peditor = myPrefs.edit();
        peditor.putInt("DONE_BY_DEADLINE_KEY", numDoneByDeadline);
        peditor.putInt("DONE_AFTER_DUE_KEY", numDoneAfterDue);
        peditor.putInt("PAST_DUE_KEY", numPastDue);
        peditor.putInt("TO_BE_DONE_KEY", numToBeDone);
        peditor.putInt("TOTAL_TASKS_KEY", totalTasks);
        peditor.apply();

        super.onPause();
    }


    @Override
    public void onStop() {
        SharedPreferences.Editor peditor = myPrefs.edit();
        peditor.putInt("DONE_BY_DEADLINE_KEY", numDoneByDeadline);
        peditor.putInt("DONE_AFTER_DUE_KEY", numDoneAfterDue);
        peditor.putInt("PAST_DUE_KEY", numPastDue);
        peditor.putInt("TO_BE_DONE_KEY", numToBeDone);
        peditor.putInt("TOTAL_TASKS_KEY", totalTasks);
        peditor.apply();

        super.onStop();
    }

    @Override
    public void onDestroy() {
        // do stuff here
        Log.d("onDestroy", "exit 3");
        super.onDestroy();
    }
}