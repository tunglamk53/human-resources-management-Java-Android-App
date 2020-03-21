package com.example.hrmanagement.DatabaseHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hrmanagement.Entity.Employee;
import com.example.hrmanagement.Service.ServiceEmployee;
import com.example.hrmanagement.TableSchema.SchemaEmployee;

import java.util.ArrayList;


public class DBHelperEmployee implements ServiceEmployee {

    private SQLiteDatabase mDb;
    private Cursor cursor;
    private ContentValues mValues;


    public DBHelperEmployee(SQLiteDatabase db) {
        this.mDb = db;
    }



    @Override
    public Employee fetchEmployeeById(int employeeId) {
        return null;
    }

    @Override
    public ArrayList<Employee> fetchAllEmployees() {
        ArrayList<Employee> employeeList = new ArrayList<>();
        try {
            cursor = mDb.query(
                    SchemaEmployee.TABLE_EMPLOYEE,
                    SchemaEmployee.EMPLOYEE_COLUMNS,
                    null, null, null, null,
                    SchemaEmployee.COLUMN_EMP_ID);

            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    Employee emp = convertCursorToEntity(cursor);
                    employeeList.add(emp);
                } while (cursor.moveToNext());
                cursor.close();
            }
            return employeeList;
        } catch (Exception e){
            return null;
        }
    }



    @Override
    public long addEmployee(Employee employee) {
        setContentValues(employee);
        try{
            return mDb.insert(SchemaEmployee.TABLE_EMPLOYEE, null, getContentValues());
        }catch (SQLException e) {
            Log.w("Database", e.getMessage());
            return -1;
        }
    }




    @Override
    public boolean deleteEmployee(int employeeId) {
        return false;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        return false;
    }







    public Employee convertCursorToEntity(Cursor cursor) {
        Employee employee =
                new Employee(
                        cursor.getInt(cursor.getColumnIndex(SchemaEmployee.COLUMN_EMP_ID)),
                        cursor.getString(cursor.getColumnIndex(SchemaEmployee.COLUMN_EMP_FNAME)),
                        cursor.getString(cursor.getColumnIndex(SchemaEmployee.COLUMN_EMP_LNAME)),
                        cursor.getString(cursor.getColumnIndex(SchemaEmployee.COLUMN_EMP_PHONE)),
                        cursor.getString(cursor.getColumnIndex(SchemaEmployee.COLUMN_EMP_ADDRESS))
                );
        return employee;
    }

    public void setContentValues(Employee employee) {
        mValues = new ContentValues();
        mValues.put(SchemaEmployee.COLUMN_EMP_FNAME, employee.getEmp_fname());
        mValues.put(SchemaEmployee.COLUMN_EMP_LNAME, employee.getEmp_lname());
        mValues.put(SchemaEmployee.COLUMN_EMP_PHONE, employee.getEmp_phone());
        mValues.put(SchemaEmployee.COLUMN_EMP_ADDRESS, employee.getEmp_address());
    }

    public ContentValues getContentValues() {
        return mValues;
    }

}
