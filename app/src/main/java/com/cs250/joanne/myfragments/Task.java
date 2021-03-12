package com.cs250.joanne.myfragments;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class Task implements Comparable<Task> {
    private String name;
    private Integer deadline;
    private Integer dateCompleted;
    private String category;
    private Boolean completed;

    public Task(String name, Integer deadline, String category) {
        this.name = name;
        this.deadline = deadline;
        this.category = category;
        this.completed = false;
        this.dateCompleted = 0;
    }

    // copy constructor
    public Task(Task task) {
        this.name = task.name;
        this.deadline = task.deadline;
        this.category = task.category;
        this.completed = task.completed;
        this.dateCompleted = task.dateCompleted;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public int compareTo(Task task) {
        // return negative if task deadline is smaller (earlier) than the one being compared to
        return Integer.compare(this.deadline, task.deadline);
    }

    public String formateDeadline() {
        String date = deadline.toString();
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(4,6));
        int day = Integer.parseInt(date.substring(6,8));
        return month + "/" + day + "/" + year;
    }

    public String formatDateCompleted() {
        String date = dateCompleted.toString();
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(4,6));
        int day = Integer.parseInt(date.substring(6,8));
        return month + "/" + day + "/" + year;
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

    public Integer getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Integer dateCompleted) {
        this.dateCompleted = dateCompleted;
    }
}
