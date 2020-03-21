package com.example.hrmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hrmanagement.Activity.DepartmentActivity;
import com.example.hrmanagement.Activity.EmployeeActivity;
import com.example.hrmanagement.DatabaseController.DatabaseOperation;

public class MainActivity extends AppCompatActivity {

//    private SQLiteDatabase wdb, rdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseOperation databaseOperation = new DatabaseOperation(this);

//        wdb = databaseOperation.getWritableDatabase();
//        rdb = databaseOperation.getReadableDatabase();

        Button btnViewEmp = findViewById(R.id.btnViewEmp);
        btnViewEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EmployeeActivity.class);
                startActivity(i);
            }
        });

        Button btnViewDep = findViewById(R.id.btnViewDep);
        btnViewDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DepartmentActivity.class);
                startActivity(i);
            }
        });
    }
}
