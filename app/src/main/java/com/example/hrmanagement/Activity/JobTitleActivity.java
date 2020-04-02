package com.example.hrmanagement.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hrmanagement.DatabaseController.DatabaseInitialization;
import com.example.hrmanagement.DatabaseController.DatabaseOperation;
import com.example.hrmanagement.DatabaseHelper.DBHelperJob;
import com.example.hrmanagement.Entity.Job;
import com.example.hrmanagement.MainActivity;
import com.example.hrmanagement.R;

public class JobTitleActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private DBHelperJob dbHelperJob;
    private Job job;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_title);

        //Open Database
        DatabaseOperation databaseOperation = new DatabaseOperation(this);
        mDb = databaseOperation.openDb();

        dbHelperJob = new DBHelperJob(mDb);

        Button btnAddJob = findViewById(R.id.btnAddJob);
        btnAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job = getJobFromEditText();
                dbHelperJob.addJob(job);
                Intent i = new Intent(JobTitleActivity.this, EmployeeActivity.class);
                startActivity(i);
            }
        });


        Button btnFooter = findViewById(R.id.btnFooter);
        btnFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JobTitleActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }



    private Job getJobFromEditText() {
        EditText tv1 = findViewById(R.id.edtJobTitle);
        EditText tv2 = findViewById(R.id.edtJobDescription);

        job = new Job(
                tv1.getText().toString().trim(),
                tv2.getText().toString().trim());

        tv1.setText("");tv2.setText("");

        return job;
    }


}
