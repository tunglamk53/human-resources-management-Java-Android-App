package com.example.hrmanagement.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.hrmanagement.TableSchema.SchemaFact;

import java.util.ArrayList;

public class UpdateEmployeeActivity extends AppCompatActivity {

    private EditText edtFName, edtLName, edtPhone, edtAddress, edtSalary, edtHourlyRate;
    private TextView txtEmpId, txtDepartment, txtJob;
    private DBHelperFact dbHelperFact;
    private DBHelperEmployee dbHelperEmployee;
    private DBHelperJob dbHelperJob;
    private DBHelperDepartment dbHelperDepartment;
    private SQLiteDatabase mDb;
    private int empId;

    private AlertDialog.Builder alertDialogBuilder;
    private LayoutInflater popupLayoutInflater;
    private AlertDialog alertDialog;
    private View popupView;

    private Department currentDepartment;
    private Job currentJob;

    private Spinner spnJobList, spnDepList;

    private final String DEPARTMENT_ACTION = "department";
    private final String JOB_ACTION = "job";

    private final String DEPARTMENT_POPUP_TITLE = "Update Employee's Department";
    private final String JOB_POPUP_TITLE = "Update Employee's Job Title";

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
        empId = intent.getIntExtra("extEmpId", 0);


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

        //Button Update EmployeeDepartment
        Button btnUpdateEmpDep = findViewById(R.id.btnUpdateEmpDep);
        btnUpdateEmpDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopUp("department");
            }
        });

        //Button Update EmployeeJob
        Button btnUpdateEmpJob = findViewById(R.id.btnUpdateEmpJob);
        btnUpdateEmpJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopUp("job");
            }
        });

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
                    dbHelperFact.updateFactEmpStatus(empId, SchemaFact.EMP_ACTIVE);
                } else {
                    swiEmpStatus.setText("Inactive");
                    dbHelperFact.updateFactEmpStatus(empId, SchemaFact.EMP_INACTIVE);
                }
            }
        });


        //Get and Display EmployeeDepartment by EmployeeId
        displayEmpDep();
        //Get and Display EmployeeJob by EmployeeId (on job TextView)
        displayEmpJob();



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






    private void createPopUp(final String popupName) {
        alertDialogBuilder = new AlertDialog.Builder(UpdateEmployeeActivity.this);
        alertDialogBuilder.setCancelable(false);
        popupLayoutInflater = LayoutInflater.from(UpdateEmployeeActivity.this);

        Button btnCancel = null;
        Button btnSave = null;
        TextView txtButtonCreate = null;

        if(popupName.equals(DEPARTMENT_ACTION)) {
            alertDialogBuilder.setTitle(DEPARTMENT_POPUP_TITLE);
            //Get View from Popup
            popupView = popupLayoutInflater.inflate(R.layout.pop_up_update_emp_dep, null);
            btnCancel = popupView.findViewById(R.id.btnCancelPopDep);
            btnSave = popupView.findViewById(R.id.btnSavePopDep);
            txtButtonCreate = popupView.findViewById(R.id.txtCreateNewDep);

            //Set Spinner for Departments
            setSpinnerDepartment();

        } else if (popupName.equals(JOB_ACTION)) {
            alertDialogBuilder.setTitle(JOB_POPUP_TITLE);
            //Get View from Popup
            popupView = popupLayoutInflater.inflate(R.layout.pop_up_update_emp_job, null);
            btnCancel = popupView.findViewById(R.id.btnCancelPopJob);
            btnSave = popupView.findViewById(R.id.btnSavePopJob);
            txtButtonCreate = popupView.findViewById(R.id.txtCreateNewJob);

            //Set Spinner for Departments
            setSpinnerJob();

        } else { }

        if(popupView != null) { alertDialogBuilder.setView(popupView); }

        //Create and Show Popup
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();


        //Button Cancel in Popup
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        //Button Save in Popup
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupName.equals(DEPARTMENT_ACTION)) {
                    //Get Selected DepartmentId from Dep Spinner
                    String selectedDepId = spnDepList.getSelectedItem().toString().split(" - ")[0];
                    //Update DepartmentId on Fact Table
                    dbHelperFact.updateFactDepartmentId(empId, Integer.parseInt(selectedDepId));

                    //Close Popup Department
                    alertDialog.cancel();

                    //Change Department TexView (after Updated Department)
                    displayEmpDep();

                } else if (popupName.equals(JOB_ACTION)) {
                    //Get Selected JobId from Job Spinner
                    String selectedJobId = spnJobList.getSelectedItem().toString().split(" - ")[0];
                    //Update JobId on Fact Table
                    dbHelperFact.updateFactJobId(empId, Integer.parseInt(selectedJobId));

                    //Close Popup Job
                    alertDialog.cancel();

                    //Change Department TexView (after Updated Department)
                    displayEmpJob();
                } else {}
            }
        });

        //Button TextView Create in Popup
        txtButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupName.equals(DEPARTMENT_ACTION)) {
                    startActivity(new Intent(v.getContext(), DepartmentActivity.class));
                } else if (popupName.equals(JOB_ACTION)) {
                    startActivity(new Intent(v.getContext(), JobTitleActivity.class));
                } else { }

            }
        });

    }


    //Set Spinner for Departments
    private void setSpinnerDepartment() {
        ArrayList<String> spnStringListDep = new ArrayList<>();

        //Fetch all departments from Department Table
        ArrayList<Department> departments = dbHelperDepartment.fetchAllDepartments();

        if(departments!=null) {
            for (Department dep : departments) {
                spnStringListDep.add(dep.getDep_id() + " - " + dep.getDep_name());
            }
        }

        spnDepList = popupView.findViewById(R.id.spnUpdateEmpDep);
        ArrayAdapter<String> adapterDepartment = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spnStringListDep);
        spnDepList.setAdapter(adapterDepartment);

        //Set Spinner with Current Department (before update)
        spnDepList.setSelection(spnStringListDep.indexOf(currentDepartment.getDep_id() + " - " + currentDepartment.getDep_name()));
    }

    //Set Spinner for Jobs
    private void setSpinnerJob() {
        ArrayList<String> spnStringListJob = new ArrayList<>();

        //Fetch all departments from Department Table
        ArrayList<Job> jobs = dbHelperJob.fetchAllJobs();

        if(jobs!=null) {
            for (Job job : jobs) {
                spnStringListJob.add(job.getJob_id() + " - " + job.getJob_title());
            }
        }

        spnJobList = popupView.findViewById(R.id.spnUpdateEmpJob);
        ArrayAdapter<String> adapterJob = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spnStringListJob);
        spnJobList.setAdapter(adapterJob);

        //Set Spinner with Current Department (before update)
        spnJobList.setSelection(spnStringListJob.indexOf(currentJob.getJob_id() + " - " + currentJob.getJob_title()));
    }

    //Get and Display EmployeeDepartment by EmployeeId (on department TextView)
    private void displayEmpDep(){
        //Fetch EmployeeDepartment by EmployeeId
        currentDepartment = dbHelperDepartment.fetchDepartmentByEmpId(empId);
        //Display Department by EmployeeId
        txtDepartment.setText(currentDepartment.getDep_name() + " (" + currentDepartment.getDep_location() + ")");
    }

    //Get and Display EmployeeJob by EmployeeId (on job TextView)
    private void displayEmpJob(){
        //Fetch EmployeeJob by EmployeeId
        currentJob = dbHelperJob.fetchJobByEmpId(empId);
        //Display Job by EmployeeId
        txtJob.setText(currentJob.getJob_title() + " (" + currentJob.getJob_description() + ")");
    }
}
