package com.example.hrmanagement.Service;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.hrmanagement.Entity.Employee;

import java.util.ArrayList;

public interface ServiceEmployee {
    public Employee fetchEmployeeById(int employeeId);

    public ArrayList<Employee> fetchAllEmployees();

    public long addEmployee(Employee employee);

    public boolean deleteEmployee(int employeeId);

    public boolean updateEmployee(Employee employee);




    public Employee convertCursorToEntity(Cursor cursor);

    public void setContentValues(Employee employee);

    public ContentValues getContentValues();
}
