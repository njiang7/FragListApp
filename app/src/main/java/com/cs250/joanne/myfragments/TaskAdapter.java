package com.cs250.joanne.myfragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    int resource; // resource is R.layout.item_task

    public TaskAdapter(@NonNull Context context, int resource, @NonNull List<Task> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LinearLayout itemView;
        Task task = getItem(position);

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }


        TextView nameView = (TextView) itemView.findViewById(R.id.taskName);
        TextView deadlineView = (TextView) itemView.findViewById(R.id.taskDeadline);
        TextView categoryView = (TextView) itemView.findViewById(R.id.taskCategory);

        nameView.setText(task.getName());
        deadlineView.setText(task.getDeadline().toString());
        categoryView.setText(task.getCategory());

        return itemView;
    }
}
