package com.example.hrmanagement.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

public class EmployeeActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
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

        //Open Database
        DatabaseOperation databaseOperation = new DatabaseOperation(this);
        mDb = databaseOperation.openDb();

        //Initialize Employee Database Helper
        dbHelperEmployee = new DBHelperEmployee(mDb);

        //Get All Employee Table and display to Recycler View
        displayRVEmployeeTable();

        //Button Add an Employee
        Button btnAddEmp = findViewById(R.id.btnAddEmp);
        btnAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Employee from EditTexts
                Employee employee = getEmployeeFromEditText();

                //Add Employee to EmployeeTable, return emId, return -1 if not added
                long empId = dbHelperEmployee.addEmployee(employee);

                //Add Data to FactTable
                if(empId!=-1)
                    addDataToFactTable(empId);

                //Get All Employee Table and display to Recycler View
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
                startActivity(new Intent(EmployeeActivity.this, JobTitleActivity.class));
            }
        });


        //Spinner for Job Title
        spnJobTitles = findViewById(R.id.spnJobTitles);
        ArrayList<String> jobTitles = getJobTitles();
        ArrayAdapter<String> adapterJobTitles = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jobTitles);
        spnJobTitles.setAdapter(adapterJobTitles);




        //Button BackToMainMenu
        Button btnFooter = findViewById(R.id.btnFooter);
        btnFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeActivity.this, MainActivity.class));
            }
        });





    }//-----------------------------------------------------------------------------------------------------------










//------------------------------------------------------------------------------------------------------------
    private void addDataToFactTable(long empId) {
        //Add DepartmentId
        String selectedDep = spnDepNames.getSelectedItem().toString();
        String selectedDepId = selectedDep.split(" - ")[0];
        //Add JobId
        String selectedJob = spnJobTitles.getSelectedItem().toString();
        String selectedJobId = selectedJob.split(" - ")[0];
        //Add Active Status for EmploymentStatus
        int activeStatus = 1;

        Fact fact = new Fact((int)empId, Integer.parseInt(selectedDepId), Integer.parseInt(selectedJobId), activeStatus);

        //Initialize Fact Database Helper
        dbHelperFact = new DBHelperFact(mDb);

        //Add Data to Fact Table
        dbHelperFact.addFact(fact);
    }

    private ArrayList<String> getJobTitles() {
        ArrayList<String> jobTitles = new ArrayList<>();

        //Initialize Job Database Helper
        dbHelperJob = new DBHelperJob(mDb);
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

        //Initialize Department Database Helper
        dbHelperDepartment = new DBHelperDepartment(mDb);
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
