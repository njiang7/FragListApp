package com.cs250.joanne.myfragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Statistics extends AppCompatActivity {

    private static int toBeDone = 0;
    private static int doneByDeadline = 0;
    private static int doneAfterDue = 0;
    private static int pastDue = 0;
    private static int totalTasks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }
}