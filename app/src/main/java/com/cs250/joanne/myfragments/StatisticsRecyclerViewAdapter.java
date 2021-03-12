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
    private Integer numTotalTasks;
    //private Integer numDoneByDeadline;
    //private Integer numDoneAfterDue;
    //private Integer numPastDue;
    //private Integer numToBeDone;
    //private Integer totalTasks;


    public StatisticsRecyclerViewAdapter(List<StatItem> items, Integer numTotalTasks, StatisticsFrag statisticsFrag, MainActivity mainActivity) {
        mValues = items;

        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentDay = mYear * 10000 + (mMonth + 1) * 100 + mDay; // format date into an integer
        final Calendar c = Calendar.getInstance();

        statisticsFrag = this.statisticsFrag;

        this.mainActivity = mainActivity;
        numTotalTasks = this.numTotalTasks;
    }

    // Return the number of tasks completed by the deadline.
    private void setNumDoneByDeadline() {
        int count = 0;
        for (Task task : mainActivity.completedTasks) {
            if (task.getDeadline() <= currentDay && task.getCompleted()) {
                count++;
            }
        }

        //if(mainActivity.completedTasks.size() + mainActivity.myTasks.size() != numTotalTasks) {
        statisticsFrag.numDoneByDeadline += count;
        //numTotalTasks = mainActivity.completedTasks.size() + mainActivity.myTasks.size();
        //}

        //statisticsFrag.numDoneByDeadline += count;
    }

    // Return the number of tasks completed after the deadline
    private void setNumDoneAfterDue() {
        int count = 0;
        for (Task task : mainActivity.completedTasks) {
            if (task.getDeadline() > currentDay && task.getCompleted()) {
                count++;
                //numTotalTasks = mainActivity.completedTasks.size() + mainActivity.myTasks.size();
            }
        }

        //if(mainActivity.completedTasks.size() + mainActivity.myTasks.size() != numTotalTasks) {
        statisticsFrag.numDoneAfterDue += count;
        //numTotalTasks = mainActivity.completedTasks.size() + mainActivity.myTasks.size();
        //}

        //statisticsFrag.numDoneAfterDue += count;
    }

    // Return the number of tasks past due
    private void setNumPastDue() {
        int count = 0;
        for (Task task : mainActivity.myTasks) {
            if (task.getDeadline() < currentDay && !task.getCompleted()) {
                count++;
            }
        }

        //if(mainActivity.completedTasks.size() + mainActivity.myTasks.size() != numTotalTasks) {
        statisticsFrag.numPastDue += count;
        //numTotalTasks = mainActivity.completedTasks.size() + mainActivity.myTasks.size();
        //}

        //statisticsFrag.numPastDue += count;
    }

    // Return the number of tasks past due
    private void setNumToBeDone() {
        int count = 0;
        for (Task task : mainActivity.myTasks) {
            if (task.getDeadline() >= currentDay && !task.getCompleted()) {
                count++;
            }
        }

        //if(mainActivity.completedTasks.size() + mainActivity.myTasks.size() != numTotalTasks) {
        statisticsFrag.numToBeDone += count;
        //numTotalTasks = mainActivity.completedTasks.size() + mainActivity.myTasks.size();
        //}

        //statisticsFrag.numToBeDone += count;
    }

    // Return the number of tasks past due
    private void setTotalTasks() {
        //if (myPrefs.getInt("TOTAL_TASKS_KEY", 0) != statisticsFrag.totalTasks) {

    //if(mainActivity.completedTasks.size() + mainActivity.myTasks.size() != numTotalTasks) {
            statisticsFrag.totalTasks += mainActivity.myTasks.size()+mainActivity.completedTasks.size();
            ///SharedPreferences.Editor peditor = myPrefs.edit();
            //peditor.putInt("TOTAL_TASKS_KEY", statisticsFrag.totalTasks);
            //peditor.apply();
    //numTotalTasks = mainActivity.completedTasks.size() + mainActivity.myTasks.size();
    //}
    //statisticsFrag.totalTasks += mainActivity.myTasks.size() + mainActivity.completedTasks.size();

       // }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stat_layout, parent, false);
        myPrefs = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if(mValues.get(position).content.equals("done by deadline")) {
            //if(mainActivity.completedTasks.size() + mainActivity.myTasks.size() != numTotalTasks) {
                setNumDoneByDeadline();
               // numTotalTasks = mainActivity.completedTasks.size() + mainActivity.myTasks.size();
           // }
            holder.mIdView.setText("" + statisticsFrag.numDoneByDeadline);
        } else if (mValues.get(position).content.equals("done after due")) {
            //if(mainActivity.completedTasks.size() + mainActivity.myTasks.size() != numTotalTasks) {
                setNumDoneAfterDue();
                //numTotalTasks = mainActivity.completedTasks.size() + mainActivity.myTasks.size();
           // }
            holder.mIdView.setText("" + statisticsFrag.numDoneAfterDue);
        } else if (mValues.get(position).content.equals("past due")) {
            //if(mainActivity.completedTasks.size() + mainActivity.myTasks.size() != numTotalTasks) {
                setNumPastDue();
               // numTotalTasks = mainActivity.completedTasks.size() + mainActivity.myTasks.size();
           // }
            holder.mIdView.setText("" + statisticsFrag.numPastDue);
        } else if (mValues.get(position).content.equals("to be done")) {
           // if(mainActivity.completedTasks.size() + mainActivity.myTasks.size() != numTotalTasks) {
                setNumToBeDone();
               // numTotalTasks = mainActivity.completedTasks.size() + mainActivity.myTasks.size();
            //}
            holder.mIdView.setText("" + statisticsFrag.numToBeDone);
        } else if (mValues.get(position).content.equals("Total Tasks")) {
            //if(mainActivity.completedTasks.size() + mainActivity.myTasks.size() != numTotalTasks) {
                //setTotalTasks();
              //  numTotalTasks = mainActivity.completedTasks.size() + mainActivity.myTasks.size();
           // }
            holder.mIdView.setText("" + statisticsFrag.totalTasks);
        } /*else {
            holder.mIdView.setText("0");
        }*/
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