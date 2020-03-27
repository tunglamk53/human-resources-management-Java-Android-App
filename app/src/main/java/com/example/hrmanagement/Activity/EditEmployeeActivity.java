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
import android.widget.ToggleButton;

import com.example.hrmanagement.DatabaseController.DatabaseOperation;
import com.example.hrmanagement.DatabaseHelper.DBHelperFact;
import com.example.hrmanagement.Entity.Fact;
import com.example.hrmanagement.MainActivity;
import com.example.hrmanagement.R;

import java.util.ArrayList;

public class EditEmployeeActivity extends AppCompatActivity {

    private EditText edtFName, edtLName, edtPhone, edtAddress, edtSalary, edtHourlyRate;
    private TextView txtEmpId, txtDepName, txtJob;
    private DatabaseOperation databaseOperation;
    private DBHelperFact dbHelperFact;
    private SQLiteDatabase wdb, rdb;
    private final int ACTIVE = 1, INACTIVE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        Intent intent = getIntent();

        final String empId = intent.getStringExtra("extEmpId");
        String empFName = intent.getStringExtra("extEmpFName");
        String empLName = intent.getStringExtra("extEmpLName");
        String empPhone = intent.getStringExtra("extEmpPhone");
        String empAddress = intent.getStringExtra("extEmpAddress");
        String empDepName = intent.getStringExtra("extEmpDepName");


        txtEmpId = findViewById(R.id.txtEditEmpId);
        edtFName = findViewById(R.id.edtEditEmpFName);
        edtLName = findViewById(R.id.edtEditEmpLName);
        edtPhone = findViewById(R.id.edtEditPhone);
        edtAddress = findViewById(R.id.edtEditAddress);
        txtDepName = findViewById(R.id.txtEditEmpDepartment);

        txtEmpId.setText(empId);
        edtFName.setText(empFName);
        edtLName.setText(empLName);
        edtPhone.setText(empPhone);
        edtAddress.setText(empAddress);
        txtDepName.setText(empDepName);



        databaseOperation = new DatabaseOperation(this);
        wdb = databaseOperation.getWritableDatabase();
        rdb = databaseOperation.getReadableDatabase();

        dbHelperFact = new DBHelperFact(wdb);

        //Switch Button
        final Switch swiEmpStatus = findViewById(R.id.swiEditEmpStatus);

        Fact facts = dbHelperFact.fetchFactByIds(Integer.parseInt(empId));
        if(facts.getEmployment_status() == 1) {
            swiEmpStatus.setChecked(true);
            swiEmpStatus.setText("Active");
        } else {
            swiEmpStatus.setChecked(false);
            swiEmpStatus.setText("Inactive");
        }

        swiEmpStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    swiEmpStatus.setText("Active");
                    dbHelperFact.updateFactEmpStatus(Integer.parseInt(empId), ACTIVE);
                } else {
                    swiEmpStatus.setText("Inactive");
                    dbHelperFact.updateFactEmpStatus(Integer.parseInt(empId), INACTIVE);
                }
            }
        });


        //Footer
        Button btnFooter = findViewById(R.id.btnFooter);
        btnFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditEmployeeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
