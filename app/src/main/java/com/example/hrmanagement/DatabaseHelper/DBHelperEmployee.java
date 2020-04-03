package com.example.hrmanagement.DatabaseHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hrmanagement.Entity.Employee;
import com.example.hrmanagement.DatabaseService.ServiceEmployee;
import com.example.hrmanagement.TableSchema.SchemaDepartment;
import com.example.hrmanagement.TableSchema.SchemaEmployee;
import com.example.hrmanagement.TableSchema.SchemaFact;

import java.util.ArrayList;


public class DBHelperEmployee implements ServiceEmployee {

    //Initialize Parameters
    private SQLiteDatabase mDb;
    private Cursor cursor;
    private ContentValues mValues;

    //Employee Constructor
    public DBHelperEmployee(SQLiteDatabase db) {
        this.mDb = db;
    }

    //Fetch An Employee by Id
    @Override
    public Employee fetchEmployeeById(int employeeId) {
        Employee employee = null;
        try {
            cursor = mDb.query(
                    SchemaEmployee.TABLE_EMPLOYEE,
                    SchemaEmployee.EMPLOYEE_COLUMNS,
                    SchemaEmployee.COLUMN_EMP_ID + " = ?",
                    new String[] { String.valueOf(employeeId) },
                    null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                employee = convertCursorToEntity(cursor);
                cursor.close();
            }
            return employee;
        } catch (Exception e){
            return null;
        }
    }

    //Fetch All Employees
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

    //Add An Employee
    @Override
    public long addEmployee(Employee employee) {
        setContentValues(employee);
        try{
            return mDb.insert(SchemaEmployee.TABLE_EMPLOYEE, null, getContentValues());
        }catch (SQLException e) {
            return -1;
        }
    }

    //Delete An Employee by Id
    @Override
    public boolean deleteEmployee(int employeeId) {
        return mDb.delete(SchemaEmployee.TABLE_EMPLOYEE, SchemaEmployee.COLUMN_EMP_ID + "=?", new String[] { String.valueOf(employeeId) }) > 0;
    }

    //Update New Employee Information
    @Override
    public void updateEmployee(Employee newEmployeeInfo) {
        setContentValues(newEmployeeInfo);
        mDb.update(
                SchemaEmployee.TABLE_EMPLOYEE,
                getContentValues(),
                SchemaEmployee.COLUMN_EMP_ID + " = ? ",
                new String[] { String.valueOf(newEmployeeInfo.getEmp_id()) });
    }

    //Fetch all Employees (and Employees's Departments)
    @Override
    public ArrayList<Employee> fetchAllEmployeesDepNames() {
        ArrayList<Employee> employeeList = new ArrayList<>();
        try {
            cursor = mDb.query(
                    SchemaEmployee.TABLE_EMPLOYEE +
                            " LEFT OUTER JOIN " + SchemaFact.TABLE_FACT + " ON " + "TAB_EMPLOYEE.emp_id=TAB_FACT.emp_id " +
                            " INNER JOIN " + SchemaDepartment.TABLE_DEPARTMENT + " ON " + "TAB_DEPARTMENT.dep_id=TAB_FACT.dep_id",
                    SchemaEmployee.EMPLOYEE_COLUMNS_DEP_NAMES,
                    null, null, null, null,
                    SchemaEmployee.TABLE_EMPLOYEE+"."+SchemaEmployee.COLUMN_EMP_ID);

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








    //Convert Cursor to An Employee
    public Employee convertCursorToEntity(Cursor cursor) {
        if(cursor.getColumnCount() == SchemaEmployee.EMPLOYEE_COLUMNS.length) {
            Employee employee =
                    new Employee(
                            cursor.getInt(cursor.getColumnIndex(SchemaEmployee.COLUMN_EMP_ID)),
                            cursor.getString(cursor.getColumnIndex(SchemaEmployee.COLUMN_EMP_FNAME)),
                            cursor.getString(cursor.getColumnIndex(SchemaEmployee.COLUMN_EMP_LNAME)),
                            cursor.getString(cursor.getColumnIndex(SchemaEmployee.COLUMN_EMP_PHONE)),
                            cursor.getString(cursor.getColumnIndex(SchemaEmployee.COLUMN_EMP_ADDRESS))
                    );
            return employee;

        } else if (cursor.getColumnCount() == SchemaEmployee.EMPLOYEE_COLUMNS_DEP_NAMES.length) {
            Employee employee =
                    new Employee(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5)
                            );
            return employee;
        } else {
            return null;
        }
    }

    //Set Content Values
    public void setContentValues(Employee employee) {
        mValues = new ContentValues();
        mValues.put(SchemaEmployee.COLUMN_EMP_FNAME, employee.getEmp_fname());
        mValues.put(SchemaEmployee.COLUMN_EMP_LNAME, employee.getEmp_lname());
        mValues.put(SchemaEmployee.COLUMN_EMP_PHONE, employee.getEmp_phone());
        mValues.put(SchemaEmployee.COLUMN_EMP_ADDRESS, employee.getEmp_address());
    }
    //Get Content Values
    public ContentValues getContentValues() {
        return mValues;
    }

}
