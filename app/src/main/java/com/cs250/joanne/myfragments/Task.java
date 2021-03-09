package com.cs250.joanne.myfragments;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class Task implements Comparable<Task> {
    private String name;
    private Integer deadline;
    private String category;
    private Boolean completed;

    public Task(String name, Integer deadline, String category) {
        this.name = name;
        this.deadline = deadline;
        this.category = category;
        this.completed = false;
    }

    // copy constructor
    public Task(Task task) {
        this.name = task.name;
        this.deadline = task.deadline;
        this.category = task.category;
        this.completed = task.completed;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
