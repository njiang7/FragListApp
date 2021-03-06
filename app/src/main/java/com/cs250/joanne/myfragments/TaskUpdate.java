package com.cs250.joanne.myfragments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;

public class TaskUpdate extends AppCompatActivity implements View.OnClickListener {

    private EditText selectDate;
    private int mYear, mMonth, mDay;
    private SharedPreferences prefs;
    private int taskIndex = -1;
    private int whichArray;
    private String taskName, taskDueDate, taskCategory;
    private Task task;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); // disable back toolbar button
        getSupportActionBar().setTitle("Task Update");
        setContentView(R.layout.activity_task_update);
        selectDate = (EditText) findViewById(R.id.editTextDate);
        selectDate.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskIndex = extras.getInt("taskIndex", -1);
            whichArray = extras.getInt("whichArray", 0);


        }

//        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        taskName = prefs.getString("taskName", "Task");
//        taskDueDate = prefs.getString("taskDueDate", "Date");
//        taskCategory = prefs.getString("taskCategory", "Category");

        if (taskIndex != -1) {
            if (whichArray == 0) {
                task = MainActivity.myTasks.get(taskIndex);
            } else if (whichArray == 1) {
                task = MainActivity.completedTasks.get(taskIndex);
            }

            if (task != null) {

                String dateString = task.getDeadline().toString();

                mYear = Integer.parseInt(dateString.substring(0,4));
                mMonth = Integer.parseInt(dateString.substring(4,6)) - 1;
                mDay = Integer.parseInt(dateString.substring(6,8));
                // populate fields if editing a task instead of creating a new task
                EditText editTextName = (EditText) findViewById(R.id.editTextName);
                editTextName.setText(task.getName());
                selectDate.setText((mMonth+1) + "/" + mDay + "/" + mYear);
                EditText editTextCategory = (EditText) findViewById(R.id.editTextCategory);
                editTextCategory.setText(task.getCategory());
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v == selectDate) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            // doesn't work?
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            selectDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            selectDate.setError(null); // clear error message
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    public void onClickCancel(View view) {
        // return to main activity

        setResult(0); // 0 means cancel
        finish();
    }

    public void onClickSave(View view) {
        // save input data and return to main activity
        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        EditText editTextCategory = (EditText) findViewById(R.id.editTextCategory);

        // make sure edittext are not empty
        int errorFlag = 0;
        if (TextUtils.isEmpty(editTextName.getText().toString())) {
            editTextName.setError("Task name cannot be empty.");
            errorFlag = 1;
        }
        if (TextUtils.isEmpty(editTextCategory.getText().toString())) {
            editTextCategory.setText("misc"); // use misc as category if not specified
        }
        if (TextUtils.isEmpty(selectDate.getText().toString())) {
            selectDate.setError("Deadline cannot be empty.");
            errorFlag = 1;
        }

        if (errorFlag == 1) {
            return;
        }

        int date = mYear * 10000 + (mMonth + 1) * 100 + mDay; // format date into an integer
        if (task != null) { // if you are editing an existing task
            task.setName(editTextName.getText().toString());
            task.setDeadline(date);
            task.setCategory(editTextCategory.getText().toString());
        } else {
            Task myTask = new Task(editTextName.getText().toString(), date, editTextCategory.getText().toString());
            if (whichArray == 0) {
                MainActivity.myTasks.add(myTask);
            } else if (whichArray == 1) {
                MainActivity.completedTasks.add(myTask);
            }
        }

        // sort chronologically
        //Collections.sort(MainActivity.myTasks);
        //Collections.sort(MainActivity.completedTasks);

        setResult(1);
        finish();
    }
}