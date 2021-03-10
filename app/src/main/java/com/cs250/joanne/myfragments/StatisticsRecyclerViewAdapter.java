package com.cs250.joanne.myfragments;

import androidx.recyclerview.widget.RecyclerView;

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


    public StatisticsRecyclerViewAdapter(List<StatItem> items, MainActivity mainActivity) {
        mValues = items;

        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentDay = mYear*10000 + (mMonth+1)*100 + mDay; // format date into an integer
        final Calendar c = Calendar.getInstance();

        this.mainActivity = mainActivity;
    }

    // Return the number of tasks completed by the deadline.
    private int getNumDoneByDeadline() {
        int count = 0;
        for(Task task: mainActivity.completedTasks) {
            if(task.getDeadline() <= currentDay && task.getCompleted()) {
                count++;
            }
        }

        return count;
    }

    // Return the number of tasks completed after the deadline
    private int getNumDoneAfterDue() {
        int count = 0;
        for(Task task: mainActivity.completedTasks) {
            if(task.getDeadline() > currentDay && task.getCompleted()) {
                count++;
            }
        }

        return count;
    }

    // Return the number of tasks past due
    private int getNumPastDue() {
        int count = 0;
        for(Task task: mainActivity.myTasks) {
            if(task.getDeadline() < currentDay && !task.getCompleted()) {
                count++;
            }
        }

        return count;
    }

    // Return the number of tasks past due
    private int getNumToBeDone() {
        int count = 0;
        for(Task task: mainActivity.myTasks) {
            if(task.getDeadline() >= currentDay && !task.getCompleted()) {
                count++;
            }
        }

        return count;
    }

    // Return the number of tasks past due
    private int getTotalTasks() {
        return mainActivity.myTasks.size() + mainActivity.completedTasks.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stat_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if(mValues.get(position).content.equals("done by deadline")) {
            holder.mIdView.setText("" + getNumDoneByDeadline());
        } else if (mValues.get(position).content.equals("done after due")) {
            holder.mIdView.setText("" + getNumDoneAfterDue());
        } else if (mValues.get(position).content.equals("past due")) {
            holder.mIdView.setText("" + getNumPastDue());
        } else if (mValues.get(position).content.equals("to be done")) {
            holder.mIdView.setText("" + getNumToBeDone());
        } else if (mValues.get(position).content.equals("Total Tasks")) {
            holder.mIdView.setText("" + getTotalTasks());
        } else {
            holder.mIdView.setText("0");
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