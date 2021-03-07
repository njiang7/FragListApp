package com.cs250.joanne.myfragments;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class Task implements Comparable<Task> {
    private String name;
    private Integer deadline;
    private String category;

    public Task(String name, Integer deadline, String category) {
        this.name = name;
        this.deadline = deadline;
        this.category = category;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public int compareTo(Task task) {
        // return negative if task deadline is smaller (earlier) than the one being compared to
        return Integer.compare(this.deadline, task.deadline);
    }

    public String getName() {
        return name;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public String getCategory() {
        return category;
    }
}
