package com.cs250.joanne.myfragments;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs250.joanne.myfragments.dummy.StatContent.StatItem;

import java.util.Calendar;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link StatItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class StatisticsRecyclerViewAdapter extends RecyclerView.Adapter<StatisticsRecyclerViewAdapter.ViewHolder> {

    private final List<StatItem> mValues;
    private MainActivity mainActivity;
    private Integer currentDay;
    private SharedPreferences myPrefs;
    private StatisticsFrag statisticsFrag;
    public static Integer numDoneByDeadline = 0;
    public static Integer numDoneAfterDue = 0;
    public static Integer numPastDue = 0;
    public static Integer numToBeDone = 0;
    public static Integer totalTasks = 0;
    private boolean isDoneAfterDue = false;
    private boolean isDoneByDeadline = false;


    public StatisticsRecyclerViewAdapter(List<StatItem> items, Integer numTotalTasks, StatisticsFrag statisticsFrag, MainActivity mainActivity) {
        mValues = items;

        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentDay = mYear * 10000 + (mMonth + 1) * 100 + mDay; // format date into an integer
        final Calendar c = Calendar.getInstance();

        this.statisticsFrag = statisticsFrag;

        this.mainActivity = mainActivity;
        myPrefs = PreferenceManager.getDefaultSharedPreferences(mainActivity);
    }

    // Return the number of tasks completed by the deadline.
    private void setNumDoneByDeadline() {
        int count = 0;
        for (Task task : mainActivity.completedTasks) {
            if (task.getDeadline() >= currentDay && task.getCompleted()) {
                count++;
            }
        }

        if(numDoneByDeadline < count) {
            statisticsFrag.numDoneByDeadline += count - numDoneByDeadline;
            numDoneByDeadline = count;
        }
        else if(numDoneByDeadline > count) {
            isDoneByDeadline = true;
        }

        SharedPreferences.Editor peditor = myPrefs.edit();
        peditor.putInt("DONE_BY_DEADLINE_KEY", statisticsFrag.numDoneByDeadline);
        peditor.apply();
    }

    // Return the number of tasks completed after the deadline
    private void setNumDoneAfterDue() {
        int count = 0;
        for (Task task : mainActivity.completedTasks) {
            if (task.getDeadline() < currentDay && task.getCompleted()) {
                count++;
            }
        }

        if(numDoneAfterDue < count) {
            statisticsFrag.numDoneAfterDue += count - numDoneAfterDue;
            numDoneAfterDue = count;
        } else if(numDoneAfterDue > count) {
            isDoneAfterDue = true;
        }

        SharedPreferences.Editor peditor = myPrefs.edit();
        peditor.putInt("DONE_AFTER_DUE_KEY", statisticsFrag.numDoneAfterDue);
        peditor.apply();
    }

    // Return the number of tasks past due
    private void setNumPastDue() {
        int count = 0;
        for (Task task : mainActivity.myTasks) {
            if (task.getDeadline() < currentDay && !task.getCompleted()) {
                count++;
            }
        }

        if(numPastDue < count) {
            statisticsFrag.numPastDue += count - numPastDue;
            numPastDue = count;
        } else if (numPastDue > count) {
            statisticsFrag.numPastDue -= numPastDue - count;
            numPastDue = count;
        }

        SharedPreferences.Editor peditor = myPrefs.edit();
        peditor.putInt("PAST_DUE_KEY", statisticsFrag.numPastDue);
        peditor.apply();
    }

    // Return the number of tasks past due
    private void setNumToBeDone() {
        int count = 0;
        for (Task task : mainActivity.myTasks) {
            if (task.getDeadline() >= currentDay && !task.getCompleted()) {
                count++;
            }
        }

        if(numToBeDone < count) {
            statisticsFrag.numToBeDone += count - numToBeDone;
            numToBeDone = count;
        } else if (numToBeDone > count) {
            statisticsFrag.numToBeDone -= numToBeDone - count;
            numToBeDone = count;
        }

        SharedPreferences.Editor peditor = myPrefs.edit();
        peditor.putInt("TO_BE_DONE_KEY", statisticsFrag.numToBeDone);
        peditor.apply();
    }

    // Return the number of tasks past due
    private void setTotalTasks() {
        if(isDoneByDeadline) {
            isDoneByDeadline = false;
            numDoneByDeadline = 0;
        }
        if(isDoneAfterDue) {
            isDoneAfterDue = false;
            numDoneAfterDue = 0;
        }

       /* if(totalTasks < total) {
            statisticsFrag.totalTasks += total - totalTasks;
            totalTasks = total;
        } else if (totalTasks > total) {
            statisticsFrag.totalTasks -= totalTasks - total;
            totalTasks = total;
        }*/

        statisticsFrag.totalTasks = statisticsFrag.numDoneAfterDue
                + statisticsFrag.numDoneByDeadline
                + statisticsFrag.numPastDue
                + statisticsFrag.numToBeDone;

        SharedPreferences.Editor peditor = myPrefs.edit();
        peditor.putInt("TOTAL_TASKS_KEY", statisticsFrag.totalTasks);
        peditor.apply();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stat_layout, parent, false);
        //myPrefs = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if(mValues.get(position).content.equals("done by deadline")) {
            setNumDoneByDeadline();
            holder.mIdView.setText("" + statisticsFrag.numDoneByDeadline);
        } else if (mValues.get(position).content.equals("done after due")) {
            setNumDoneAfterDue();
            holder.mIdView.setText("" + statisticsFrag.numDoneAfterDue);
        } else if (mValues.get(position).content.equals("past due")) {
            setNumPastDue();
            holder.mIdView.setText("" + statisticsFrag.numPastDue);
        } else if (mValues.get(position).content.equals("to be done")) {
            setNumToBeDone();
            holder.mIdView.setText("" + statisticsFrag.numToBeDone);
        } else if (mValues.get(position).content.equals("Total Tasks")) {
            setTotalTasks();
            holder.mIdView.setText("" + statisticsFrag.totalTasks);
        }
        holder.mContentView.setText(mValues.get(position).content);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public StatItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.count);
            mContentView = (TextView) view.findViewById(R.id.stat_title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}