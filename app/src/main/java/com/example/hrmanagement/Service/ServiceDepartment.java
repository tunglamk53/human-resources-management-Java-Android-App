package com.example.hrmanagement.Service;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.hrmanagement.Entity.Department;

import java.util.ArrayList;

public interface ServiceDepartment {

    public Department fetchDepartmentById(int departmentId);

    public ArrayList<Department> fetchAllDepartments();

    public boolean addDepartment(Department department);

    public boolean deleteDepartment(int departmentId);

    public boolean updateDepartment(Department department);




    public Department convertCursorToEntity(Cursor cursor);

    public void setContentValues(Department department);

    public ContentValues getContentValues();

}
