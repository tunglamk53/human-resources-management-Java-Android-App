package com.example.hrmanagement.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmanagement.CustomAdapter.DepRVAdapter;
import com.example.hrmanagement.DatabaseController.DatabaseOperation;
import com.example.hrmanagement.DatabaseHelper.DBHelperDepartment;
import com.example.hrmanagement.Entity.Department;
import com.example.hrmanagement.MainActivity;
import com.example.hrmanagement.R;

import java.util.ArrayList;

public class DepartmentActivity extends AppCompatActivity {

    private SQLiteDatabase wdb, rdb;
    private DatabaseOperation databaseOperation;
    private RecyclerView recyclerView;
    private ArrayList<Department> departments;
    private DepRVAdapter depRVAdapter;
    private DBHelperDepartment dbHelperDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        databaseOperation = new DatabaseOperation(this);
        wdb = databaseOperation.getWritableDatabase();
        rdb = databaseOperation.getReadableDatabase();

        dbHelperDepartment = new DBHelperDepartment(rdb);

        displayRVDepartmentTable();

        Button btnAddDep = findViewById(R.id.btnAddDep);
        btnAddDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Department department = getDepartmentFromEditText();
                dbHelperDepartment.addDepartment(department);
                displayRVDepartmentTable();
            }
        });



        Button btnFooter = findViewById(R.id.btnFooter);
        btnFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DepartmentActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }







    private void displayRVDepartmentTable() {
        departments = dbHelperDepartment.fetchAllDepartments();
        setRVAdapterDepartment(departments);
    }

    private void setRVAdapterDepartment(ArrayList<Department> departments) {
        recyclerView = findViewById(R.id.rViewDepartment);
        depRVAdapter = new DepRVAdapter(departments);
        recyclerView.setAdapter(depRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private Department getDepartmentFromEditText() {
        EditText tv1 = findViewById(R.id.edtDepName);
        EditText tv2 = findViewById(R.id.edtDepLocation);

        Department department = new Department(
                tv1.getText().toString().trim(),
                tv2.getText().toString().trim());

        tv1.setText("");tv2.setText("");

        return department;
    }




}
