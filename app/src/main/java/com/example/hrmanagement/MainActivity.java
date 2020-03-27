package com.example.hrmanagement;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hrmanagement.Activity.DepartmentActivity;
import com.example.hrmanagement.Activity.EmployeeActivity;
import com.example.hrmanagement.DatabaseController.AsyncTaskHelper;
import com.example.hrmanagement.DatabaseController.DatabaseOperation;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Open the Database
        DatabaseOperation databaseOperation = new DatabaseOperation(this);
        mDb = databaseOperation.openDb();


        //Button View Employees
        Button btnViewEmp = findViewById(R.id.btnViewEmp);
        btnViewEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EmployeeActivity.class));
            }
        });

        //Button View Departments
        Button btnViewDep = findViewById(R.id.btnViewDep);
        btnViewDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DepartmentActivity.class));
            }
        });
    }
}
