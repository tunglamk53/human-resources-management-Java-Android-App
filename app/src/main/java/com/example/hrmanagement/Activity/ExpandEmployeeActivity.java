package com.example.hrmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.hrmanagement.CustomAdapter.EmpRVAdapter;
import com.example.hrmanagement.DatabaseController.DatabaseOperation;
import com.example.hrmanagement.DatabaseHelper.DBHelperEmployee;
import com.example.hrmanagement.Entity.Employee;
import com.example.hrmanagement.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ExpandEmployeeActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private DBHelperEmployee dbHelperEmployee;
    private ArrayList<Employee> employees;
    private RecyclerView recyclerView;
    private EmpRVAdapter empRVAdapter;
    Spinner spnSortBy;
    RadioGroup radGroup;
    RadioButton radBtnAcs;
    RadioButton radBtnDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_employee);

        //Open Database
        DatabaseOperation databaseOperation = new DatabaseOperation(this);
        mDb = databaseOperation.openDb();

        //Initialize Employee Database Helper
        dbHelperEmployee = new DBHelperEmployee(mDb);

        //Get All Employee Table and display to Recycler View
        displayRVEmployeeTable();

        //EditText Search By Nam
        EditText edtSearch = findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        radGroup = findViewById(R.id.radBtnGroup);
        radBtnAcs = findViewById(R.id.radBtnAsc);
        radBtnDesc = findViewById(R.id.radBtnDesc);
        radBtnAcs.setChecked(true);

        spnSortBy = findViewById(R.id.spnSortBy);
        String[] spnSortByValues = {"ID", "Name", "Department", "Address"};
        ArrayAdapter<String> adapterSpinnerSortBy = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, spnSortByValues);
        spnSortBy.setAdapter(adapterSpinnerSortBy);
        //Spinner SortBy
        spnSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(spnSortBy.getSelectedItem().toString().equals("ID")) {
                        if(radBtnAcs.isChecked()) {
                            sortByIdAsc();
                        } else {
                            sortByIdDesc();
                        }
                        setRVAdapterEmployee(employees);

                    } else if (spnSortBy.getSelectedItem().toString().equals("Name")) {
                        if(radBtnAcs.isChecked()) {
                            sortByNameAsc();
                        } else {
                            sortByNameDesc();
                        }
                        setRVAdapterEmployee(employees);

                    } else if (spnSortBy.getSelectedItem().toString().equals("Department")) {
                        if(radBtnAcs.isChecked()) {
                            sortByDepartmentAsc();
                        } else {
                            sortByDepartmentDesc();
                        }
                        setRVAdapterEmployee(employees);

                    } else if (spnSortBy.getSelectedItem().toString().equals("Address")) {
                        if(radBtnAcs.isChecked()) {
                            sortByAddressAsc();
                        } else {
                            sortByAddressDesc();
                        }
                        setRVAdapterEmployee(employees);

                    } else {

                    }
                } catch (Exception ex) {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        radGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    if(radBtnAcs.isChecked()) {
                        if(spnSortBy.getSelectedItem().toString().equals("ID")) {
                            sortByIdAsc();
                        } else if (spnSortBy.getSelectedItem().toString().equals("Name")) {
                            sortByNameAsc();
                        } else if (spnSortBy.getSelectedItem().toString().equals("Department")) {
                            sortByDepartmentAsc();
                        } else if (spnSortBy.getSelectedItem().toString().equals("Address")) {
                            sortByAddressAsc();
                        }
                        setRVAdapterEmployee(employees);

                    } else if (radBtnDesc.isChecked()) {
                        if(spnSortBy.getSelectedItem().toString().equals("ID")) {
                            sortByIdDesc();
                        } else if (spnSortBy.getSelectedItem().toString().equals("Name")) {
                            sortByNameDesc();
                        } else if (spnSortBy.getSelectedItem().toString().equals("Department")) {
                            sortByDepartmentDesc();
                        } else if (spnSortBy.getSelectedItem().toString().equals("Address")) {
                            sortByAddressDesc();
                        }
                        setRVAdapterEmployee(employees);

                    }
                } catch (Exception ex) {

                }

            }
        });
    }

    private void filter(String text) {
        ArrayList<Employee> filteredList = new ArrayList<>();

        for (Employee emp: employees) {
            String name = emp.getEmp_lname() + emp.getEmp_fname();
            if (name.contains(text.toLowerCase())) {
                filteredList.add(emp);
            }
        }

        setRVAdapterEmployee(filteredList);
    }

    //Display Employee RecyclerView
    private void displayRVEmployeeTable() {
        employees = dbHelperEmployee.fetchAllEmployeesDepNames();
        setRVAdapterEmployee(employees);
    }

    private void setRVAdapterEmployee(ArrayList<Employee> employees) {
        recyclerView = findViewById(R.id.rViewExpEmployee);
        empRVAdapter = new EmpRVAdapter(employees);
        recyclerView.setAdapter(empRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }


    private void sortByIdAsc() {
        Collections.sort(employees, new Comparator<Employee>(){
            public int compare(Employee e1, Employee e2) {
                return Integer.valueOf(e1.getEmp_id()).compareTo(e2.getEmp_id());
            }
        });
    }

    private void sortByDepartmentAsc() {
        Collections.sort(employees, new Comparator<Employee>(){
            public int compare(Employee e1, Employee e2) {
                return e1.getEmp_DepName().compareToIgnoreCase(e2.getEmp_DepName());
            }
        });
    }

    private void sortByAddressAsc() {
        Collections.sort(employees, new Comparator<Employee>(){
            public int compare(Employee e1, Employee e2) {
                return e1.getEmp_address().compareToIgnoreCase(e2.getEmp_address());
            }
        });
    }

    private void sortByNameAsc() {
        Collections.sort(employees, new Comparator<Employee>(){
            public int compare(Employee e1, Employee e2) {
                return e1.getEmp_lname().compareToIgnoreCase(e2.getEmp_lname());
            }
        });
    }

    private void sortByIdDesc() {
        Collections.sort(employees, new Comparator<Employee>(){
            public int compare(Employee e1, Employee e2) {
                return Integer.valueOf(e2.getEmp_id()).compareTo(e1.getEmp_id());
            }
        });
    }

    private void sortByDepartmentDesc() {
        Collections.sort(employees, new Comparator<Employee>(){
            public int compare(Employee e1, Employee e2) {
                return e2.getEmp_DepName().compareToIgnoreCase(e1.getEmp_DepName());
            }
        });
    }

    private void sortByAddressDesc() {
        Collections.sort(employees, new Comparator<Employee>(){
            public int compare(Employee e1, Employee e2) {
                return e2.getEmp_address().compareToIgnoreCase(e1.getEmp_address());
            }
        });
    }

    private void sortByNameDesc() {
        Collections.sort(employees, new Comparator<Employee>(){
            public int compare(Employee e1, Employee e2) {
                return e2.getEmp_lname().compareToIgnoreCase(e1.getEmp_lname());
            }
        });
    }

}
