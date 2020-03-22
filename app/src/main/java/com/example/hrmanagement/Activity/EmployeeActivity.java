package com.example.hrmanagement.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmanagement.CustomAdapter.EmpRVAdapter;
import com.example.hrmanagement.DatabaseController.DatabaseOperation;
import com.example.hrmanagement.DatabaseHelper.DBHelperDepartment;
import com.example.hrmanagement.DatabaseHelper.DBHelperEmployee;
import com.example.hrmanagement.DatabaseHelper.DBHelperFact;
import com.example.hrmanagement.DatabaseHelper.DBHelperJob;
import com.example.hrmanagement.Entity.Department;
import com.example.hrmanagement.Entity.Employee;
import com.example.hrmanagement.Entity.Fact;
import com.example.hrmanagement.Entity.Job;
import com.example.hrmanagement.MainActivity;
import com.example.hrmanagement.R;

import java.util.ArrayList;
import java.util.Map;

public class EmployeeActivity extends AppCompatActivity {

    private SQLiteDatabase wdb, rdb;
    private DatabaseOperation databaseOperation;
    private RecyclerView recyclerView;
    private EmpRVAdapter empRVAdapter;
    ArrayList<Employee> empDepNamesList;
    private DBHelperEmployee dbHelperEmployee;
    private DBHelperDepartment dbHelperDepartment;
    private DBHelperJob dbHelperJob;
    private DBHelperFact dbHelperFact;
    private Spinner spnDepNames, spnJobTitles;


//-----------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        databaseOperation = new DatabaseOperation(this);
        wdb = databaseOperation.getWritableDatabase();
        rdb = databaseOperation.getReadableDatabase();

        dbHelperEmployee = new DBHelperEmployee(rdb);

        //Get All Employee Table and display to Recycler View
        displayRVEmployeeTable();


        //Button Add an Employee
        Button btnAddEmp = findViewById(R.id.btnAddEmp);
        btnAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Employee employee = getEmployeeFromEditText();
                long empId = dbHelperEmployee.addEmployee(employee);

                //Add Data to FactTable
                if(empId!=-1)
                    addDataToFactTable(empId);

                displayRVEmployeeTable();
            }
        });



        //Spinner for Department ID
        spnDepNames = findViewById(R.id.spnDepNames);
        ArrayList<String> depNames = getDepNames(); //get DepartmentIDs list
        ArrayAdapter<String> adapterDepNames = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, depNames);
        spnDepNames.setAdapter(adapterDepNames);


        //Button Add a Job Tilte
        TextView txtAddJob = findViewById(R.id.txtAddJobTitle);
        txtAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(EmployeeActivity.this, JobTitleActivity.class);
                startActivity(i);
            }
        });


        //Spinner for Job Title
        spnJobTitles = findViewById(R.id.spnJobTitles);
        ArrayList<String> jobTitles = getJobTitles();
        ArrayAdapter<String> adapterJobTitles = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jobTitles);
        spnJobTitles.setAdapter(adapterJobTitles);


        Button btnFooter = findViewById(R.id.btnFooter);
        btnFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmployeeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


    }
//-----------------------------------------------------------------------------------------------------------









//------------------------------------------------------------------------------------------------------------

    private void addDataToFactTable(long empId) {
        String selectedDep = spnDepNames.getSelectedItem().toString();
        String selectedDepId = selectedDep.split(" - ")[0];

        String selectedJob = spnJobTitles.getSelectedItem().toString();
        String selectedJobId = selectedJob.split(" - ")[0];

        Fact fact = new Fact((int)empId, Integer.parseInt(selectedDepId), Integer.parseInt(selectedJobId));

        dbHelperFact = new DBHelperFact(wdb);
        dbHelperFact.addFact(fact);
    }

    private ArrayList<String> getJobTitles() {
        ArrayList<String> jobTitles = new ArrayList<>();
        dbHelperJob = new DBHelperJob(rdb);
        ArrayList<Job> jobList = dbHelperJob.fetchAllJobs();

        if(jobList!=null) {
            for (Job job : jobList) {
                jobTitles.add(Integer.toString(job.getJob_id()) + " - " + job.getJob_title());
            }
        }
        return jobTitles;
    }



    private ArrayList<String> getDepNames() {
        ArrayList<String> depNames = new ArrayList<>();
        dbHelperDepartment = new DBHelperDepartment(rdb);
        ArrayList<Department> departmentList = dbHelperDepartment.fetchAllDepartments();

        if(departmentList!=null) {
            for (Department dep : departmentList) {
                depNames.add(Integer.toString(dep.getDep_id()) + " - " + dep.getDep_name());
            }
        }
        return depNames;
    }



    private void displayRVEmployeeTable() {
        empDepNamesList = dbHelperEmployee.fetchAllEmployeesDepNames();
        setRVAdapterEmployee(empDepNamesList);
    }

    private void setRVAdapterEmployee(ArrayList<Employee> employees) {
        recyclerView = findViewById(R.id.rViewEmployee);
        empRVAdapter = new EmpRVAdapter(employees);
        recyclerView.setAdapter(empRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }



    private Employee getEmployeeFromEditText() {
        EditText tv1 = findViewById(R.id.edtFName1);
        EditText tv2 = findViewById(R.id.edtLName1);
        EditText tv3 = findViewById(R.id.edtPhone1);
        EditText tv4 = findViewById(R.id.edtAddress1);

        Employee employee = new Employee(
                tv1.getText().toString().trim(),
                tv2.getText().toString().trim(),
                tv3.getText().toString().trim(),
                tv4.getText().toString().trim());

        tv1.setText("");tv2.setText("");tv3.setText("");tv4.setText("");

        return employee;
    }
}
