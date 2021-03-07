package com.cs250.joanne.myfragments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class AddTask extends AppCompatActivity implements View.OnClickListener {

    private EditText selectDate;
    private int mYear, mMonth, mDay;
    private SharedPreferences prefs;
    private String taskName, taskDueDate, taskCategory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        selectDate = (EditText) findViewById(R.id.editTextDate);
        selectDate.setOnClickListener(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        taskName = prefs.getString("taskName", "Task");
        taskDueDate = prefs.getString("taskDueDate", "Date");
        taskCategory = prefs.getString("taskCategory", "Category");

        // populate fields if editing a task instead of creating a new task
//        EditText editTextName = (EditText) findViewById(R.id.editTextName);
//        editTextName.setText(taskName);
//        selectDate.setText(taskDueDate);
//        EditText editTextCategory = (EditText) findViewById(R.id.editTextCategory);
//        editTextCategory.setText(taskCategory);

    }

    @Override
    public void onClick(View v) {
        if (v == selectDate) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            selectDate.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    public void onClickCancel(View view) {
        // return to main activity
        finish();
    }

    public void onClickSave(View view) {
        // save input data and return to main activity
        finish();

    }
}