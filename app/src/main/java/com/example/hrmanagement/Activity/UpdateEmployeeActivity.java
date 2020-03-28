package com.example.hrmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.hrmanagement.DatabaseController.DatabaseInitialization;
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

public class UpdateEmployeeActivity extends AppCompatActivity {

    private EditText edtFName, edtLName, edtPhone, edtAddress, edtSalary, edtHourlyRate;
    private TextView txtEmpId, txtDepartment, txtJob;
    private DatabaseInitialization databaseInitialization;
    private DBHelperFact dbHelperFact;
    private DBHelperEmployee dbHelperEmployee;
    private DBHelperJob dbHelperJob;
    private DBHelperDepartment dbHelperDepartment;
    private SQLiteDatabase mDb;
    private final int ACTIVE = 1, INACTIVE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        //databaseInitialization = new DatabaseInitialization(this);
        //wdb = databaseInitialization.getWritableDatabase();
        //rdb = databaseInitialization.getReadableDatabase();

        //Open Database
        DatabaseOperation databaseOperation = new DatabaseOperation(this);
        mDb = databaseOperation.openDb();
        //Initialize { Fact,Employee,Job,Department } Database Helper
        dbHelperFact = new DBHelperFact(mDb);
        dbHelperEmployee = new DBHelperEmployee(mDb);
        dbHelperJob = new DBHelperJob(mDb);
        dbHelperDepartment = new DBHelperDepartment(mDb);

        //Get Intent from EmployeeActivity
        Intent intent = getIntent();
        //Key EmployeeId
        final int empId = Integer.parseInt(intent.getStringExtra("extEmpId"));


        //Fetch Employee by EmployeeId
        Employee employee = dbHelperEmployee.fetchEmployeeById(empId);



        //Get TextViews and EditTexts
        txtEmpId = findViewById(R.id.txtEditEmpId);
        edtFName = findViewById(R.id.edtEditEmpFName);
        edtLName = findViewById(R.id.edtEditEmpLName);
        edtPhone = findViewById(R.id.edtEditPhone);
        edtAddress = findViewById(R.id.edtEditAddress);
        edtSalary = findViewById(R.id.edtEditSalary);
        edtHourlyRate = findViewById(R.id.edtEditHourlyRate);
        txtDepartment = findViewById(R.id.txtEditEmpDepartment);
        txtJob = findViewById(R.id.txtEditEmpJob);

        //Display all Employee Information to Layout
        txtEmpId.setText(String.valueOf(employee.getEmp_id()));
        edtFName.setText(employee.getEmp_fname());
        edtLName.setText(employee.getEmp_lname());
        edtPhone.setText(employee.getEmp_phone());
        edtAddress.setText(employee.getEmp_address());

        //Switch Button
        final Switch swiEmpStatus = findViewById(R.id.swiEditEmpStatus);



        //Fetch A Fact Row by EmployeeId
        Fact fact = dbHelperFact.fetchFactById(empId);

        //Display Employee Salary and HourlyRate on EditTexts
        edtSalary.setText(String.format("%.2f", fact.getSalary()));
        edtHourlyRate.setText(String.format("%.2f", fact.getHourly_rate()));

        //Display Employment Status on SwitchButton
        if(fact.getEmployment_status() == 1) {
            swiEmpStatus.setChecked(true);
            swiEmpStatus.setText("Active");
        } else {
            swiEmpStatus.setChecked(false);
            swiEmpStatus.setText("Inactive");
        }

        //SwitchButton for Employment Status
        swiEmpStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    swiEmpStatus.setText("Active");
                    dbHelperFact.updateFactEmpStatus(empId, ACTIVE);
                } else {
                    swiEmpStatus.setText("Inactive");
                    dbHelperFact.updateFactEmpStatus(empId, INACTIVE);
                }
            }
        });



        //Fetch EmployeeDepartment by EmployeeId
        Department department = dbHelperDepartment.fetchDepartmentByEmpId(empId);
        //Display Department by EmployeeId
        txtDepartment.setText(department.getDep_name() + " (" + department.getDep_location() + ")");



        //Fetch EmployeeJob by EmployeeId
        Job job = dbHelperJob.fetchJobByEmpId(empId);
        //Display Job by EmployeeId
        txtJob.setText(job.getJob_title() + " (" + job.getJob_description() + ")");




        //Update Employee Info Button
        Button btnUpdateEmployeeInfo = findViewById(R.id.btnUpdateEmployee);
        btnUpdateEmployeeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get New FirstName, LastName, PhoneNumber, HomeAddress
                Employee newEmployeeInfo = new Employee(
                        empId,
                        edtFName.getText().toString().trim(),
                        edtLName.getText().toString().trim(),
                        edtPhone.getText().toString().trim(),
                        edtAddress.getText().toString().trim()
                );

                //Update Database with New FirstName, LastName, PhoneNumber, HomeAddress by EmployeeId
                dbHelperEmployee.updateEmployee(newEmployeeInfo);


                //Get New Salary and HourlyRate
                Double newSalary = Double.parseDouble(edtSalary.getText().toString().trim());
                Double newHourlyRate = Double.parseDouble(edtHourlyRate.getText().toString().trim());

                //Update Database with New Salary and HourlyRate by EmployeeId
                dbHelperFact.updateFactSalaryAndRate(empId, newSalary, newHourlyRate);

                startActivity(new Intent(UpdateEmployeeActivity.this, EmployeeActivity.class));
            }
        });

        //Footer
        Button btnFooter = findViewById(R.id.btnFooter);
        btnFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateEmployeeActivity.this, MainActivity.class));
            }
        });
    }
}
