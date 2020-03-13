package com.example.hrmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class EmployeeActivity extends AppCompatActivity {

    private SQLiteDatabase wdb, rdb;
    private DatabaseOperation databaseOperation;
    private RecyclerView recyclerView;
    private ArrayList<Employee> employees;
    private EmpRVAdapter empRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        databaseOperation = new DatabaseOperation(this);
        wdb = databaseOperation.getWritableDatabase();
        rdb = databaseOperation.getReadableDatabase();

        employees = getEmployees();

        recyclerView = findViewById(R.id.rViewEmployee);
        empRVAdapter = new EmpRVAdapter(employees);
        recyclerView.setAdapter(empRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Spinner for Department ID
        final Spinner sDep = findViewById(R.id.spnDep);
        ArrayList<String> depIds = getDepIds(); //get DepartmentIDs list
        ArrayAdapter<String> adapterDep = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, depIds);
        sDep.setAdapter(adapterDep);

        //Spinner for Job Title
        final ArrayList<String> jobsList = getJobs();
        final Spinner sJob = findViewById(R.id.spnJobTitle);
        ArrayAdapter<String> adapterJob = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jobsList);
        sJob.setAdapter(adapterJob);

        //Button Add An Employee to Table FACT
        Button btnAddEmp = findViewById(R.id.btnAddEmp);
        btnAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add an Employee to Table EMPLOYEE in Database
                long temNewEmpId = addEmployee();

                String selectedDepId = sDep.getSelectedItem().toString();
                String selectedJobTitle = sJob.getSelectedItem().toString();

                //Add Data to Table FACT (emp_id, dep_id, job_title)
                addFactTable(temNewEmpId, selectedDepId, selectedJobTitle);
            }
        });


        //Button Add A Job Tilte
        TextView txtAddJob = findViewById(R.id.txtAddJobTitle);
        txtAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(EmployeeActivity.this, JobTitleActivity.class);
                startActivity(i);
            }
        });
    }

    private ArrayList<String> getJobs() {
        ArrayList<String> tem_jobs = new ArrayList<>();
        Cursor cursor = rdb.rawQuery("SELECT job_title FROM TAB_JOB", null);
        try{
            if(cursor!=null){
                cursor.moveToFirst();
                do {
                    tem_jobs.add(cursor.getString(0));
                } while (cursor.moveToNext());
                cursor.close();
            }
        }catch (Exception ex) { }
        return tem_jobs;
    }

    private void addFactTable(long temNewEmpId, String temDepId, String temJobTitle) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("emp_id", temNewEmpId);
        contentValues.put("dep_id", Integer.parseInt(temDepId));
        contentValues.put("job_title", temJobTitle);
        long newRowID = wdb.insert("TAB_FACT", null, contentValues);
    }

    private long addEmployee() {
        EditText tv1 = findViewById(R.id.edtFName1);
        EditText tv2 = findViewById(R.id.edtLName1);
        EditText tv3 = findViewById(R.id.edtPhone1);
        EditText tv4 = findViewById(R.id.edtAddress1);

        ContentValues contentValues = new ContentValues();
        contentValues.put("emp_fname", tv1.getText().toString());
        contentValues.put("emp_lname", tv2.getText().toString());
//        contentValues.put("emp_phone", Integer.parseInt(tv3.getText().toString()));
        contentValues.put("emp_phone", tv3.getText().toString());
        contentValues.put("emp_address", tv4.getText().toString());

        long newRowID = wdb.insert("TAB_EMPLOYEE", null, contentValues);

        employees = getEmployees();
        empRVAdapter = new EmpRVAdapter(employees);
        recyclerView.setAdapter(empRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        return newRowID;
    }


    private ArrayList<String> getDepIds() {
        ArrayList<String> tem_depIds = new ArrayList<>();
        Cursor cursor = rdb.rawQuery("SELECT dep_id FROM TAB_DEPARTMENT", null);
        try{
            if(cursor!=null){
                cursor.moveToFirst();
                do {
                    tem_depIds.add(cursor.getString(0));
                } while (cursor.moveToNext());
                cursor.close();
            }
        }catch (Exception ex) { }
        return tem_depIds;
    }


    private ArrayList<Employee> getEmployees() {
        ArrayList<Employee> tem_employees = new ArrayList<>();
        String SQL_SELECT_ALL_EMPLOYEE ="SELECT * FROM TAB_EMPLOYEE";
        Cursor cursor = rdb.rawQuery(SQL_SELECT_ALL_EMPLOYEE, null);
        try {
            if(cursor!=null){
                cursor.moveToFirst();
                do{
                    int tem_id = Integer.parseInt(cursor.getString(0));
                    String tem_fname = cursor.getString(1);
                    String tem_lname = cursor.getString(2);
                    String tem_phone = cursor.getString(3);
                    String tem_address = cursor.getString(4);

                    Employee tem_emp = new Employee(tem_id, tem_fname, tem_lname, tem_phone, tem_address);
                    tem_employees.add(tem_emp);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception ex) { Log.d("errs3", ex.toString()); }
        return tem_employees;
    }
}
