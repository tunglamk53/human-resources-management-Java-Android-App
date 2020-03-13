package com.example.hrmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JobTitleActivity extends AppCompatActivity {

    private DatabaseOperation databaseOperation;
    private SQLiteDatabase wdb, rdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_title);

        databaseOperation = new DatabaseOperation(this);
        wdb = databaseOperation.getWritableDatabase();
        rdb = databaseOperation.getReadableDatabase();

        Button btnAddJob = findViewById(R.id.btnAddJob);
        btnAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewJob();
                Intent i = new Intent(JobTitleActivity.this, EmployeeActivity.class);
                startActivity(i);
            }
        });
    }

    private void addNewJob() {
        EditText tem_jobTitle = findViewById(R.id.edtJobTitle);
        EditText tem_description = findViewById(R.id.edtJobDescription);

        ContentValues contentValues = new ContentValues();
        contentValues.put("job_title", tem_jobTitle.getText().toString());
        contentValues.put("job_description", tem_description.getText().toString());

        long newRowId = wdb.insert("TAB_jOB", null, contentValues);
    }
}
