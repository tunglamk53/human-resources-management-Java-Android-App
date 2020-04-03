package com.example.hrmanagement.DatabaseHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.hrmanagement.Entity.Fact;
import com.example.hrmanagement.DatabaseService.ServiceFact;
import com.example.hrmanagement.TableSchema.SchemaDepartment;
import com.example.hrmanagement.TableSchema.SchemaDocument;
import com.example.hrmanagement.TableSchema.SchemaEmployee;
import com.example.hrmanagement.TableSchema.SchemaFact;
import com.example.hrmanagement.TableSchema.SchemaJob;

import java.util.ArrayList;

public class DBHelperFact implements ServiceFact {

    //Initialize Parameters
    private SQLiteDatabase mDb;
    private Cursor cursor;
    private ContentValues mValues;

    //Fact Constructor
    public DBHelperFact(SQLiteDatabase db) {
        this.mDb = db;
    }

    //Fetch All Facts
    @Override
    public Fact fetchFactById(int emp_id) {
        Fact fact = null;
        try {
            cursor = mDb.query(
                    SchemaFact.TABLE_FACT,
                    SchemaFact.FACT_COLUMNS,
                    SchemaEmployee.COLUMN_EMP_ID + " = ?",
                    new String[] { String.valueOf(emp_id) },
                    null, null,
                    SchemaEmployee.COLUMN_EMP_ID);

            if (cursor != null) {
                cursor.moveToFirst();
                fact = convertCursorToEntity(cursor);
                cursor.close();
            }
            return fact;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public ArrayList<Fact> fetchAllFacts() {
        return null;
    }

    @Override
    public void updateFact(Fact newFactInfo) {
        setContentValues(newFactInfo);
        mDb.update(
                SchemaFact.TABLE_FACT,
                getContentValues(),
                SchemaEmployee.COLUMN_EMP_ID + " = ? ",
                new String[] { String.valueOf(newFactInfo.getEmp_id()) });
    }

    //Delete Fact by EmployeeId
    @Override
    public boolean deleteFact(int employeeId) {
        return mDb.delete(SchemaFact.TABLE_FACT, SchemaEmployee.COLUMN_EMP_ID + "=?", new String[] { String.valueOf(employeeId) }) > 0;

    }

    //Add a Fact
    @Override
    public boolean addFact(Fact fact) {
        setContentValues(fact);
        try{
            mDb.insert(SchemaFact.TABLE_FACT, null, getContentValues());
            return true;
        } catch (SQLiteException e) {
            return false;
        }
    }

    //Update Salary and Hourly Rate by EmployeeId on Fact Table
    public void updateFactSalaryAndRate(int emp_id, double salary, double hourlyRate) {
        ContentValues values = new ContentValues();
        values.put(SchemaFact.COLUMN_FACT_SALARY, salary);
        values.put(SchemaFact.COLUMN_FACT_HOURLY_RATE, hourlyRate);
        mDb.update(SchemaFact.TABLE_FACT, values, SchemaEmployee.COLUMN_EMP_ID + " = ? ", new String[] { String.valueOf(emp_id) });
    }

    //Update DepartmentId by EmployeeId on Fact Table
    public void updateFactDepartmentId(int emp_id, int dep_id) {
        ContentValues valueDepId = new ContentValues();
        valueDepId.put(SchemaDepartment.COLUMN_DEP_ID, dep_id);
        mDb.update(SchemaFact.TABLE_FACT, valueDepId, SchemaEmployee.COLUMN_EMP_ID + " = ? ", new String[] { String.valueOf(emp_id) });
    }

    //Update JobId by EmployeeId on Fact Table
    public void updateFactJobId(int emp_id, int job_id) {
        ContentValues valueJobId = new ContentValues();
        valueJobId.put(SchemaJob.COLUMN_JOB_ID, job_id);
        mDb.update(SchemaFact.TABLE_FACT, valueJobId, SchemaEmployee.COLUMN_EMP_ID + " = ? ", new String[] { String.valueOf(emp_id) });
    }

    //Update Employment Status by EmployeeId on Fact Table
    public void updateFactEmpStatus (int emp_id, int employment_status) {
        ContentValues statusValues = new ContentValues();
        statusValues.put(SchemaFact.COLUMN_FACT_EMP_STATUS, employment_status);
        mDb.update(SchemaFact.TABLE_FACT, statusValues, SchemaEmployee.COLUMN_EMP_ID + " = ?", new String[] { String.valueOf(emp_id) });
    }




    public Fact convertCursorToEntity(Cursor cursor) {
        Fact fact = new Fact(
                cursor.getInt(cursor.getColumnIndex(SchemaEmployee.COLUMN_EMP_ID)),
                cursor.getInt(cursor.getColumnIndex(SchemaDepartment.COLUMN_DEP_ID)),
                cursor.getInt(cursor.getColumnIndex(SchemaDocument.COLUMN_DOC_ID)),
                cursor.getInt(cursor.getColumnIndex(SchemaJob.COLUMN_JOB_ID)),
                cursor.getDouble(cursor.getColumnIndex(SchemaFact.COLUMN_FACT_SALARY)),
                cursor.getDouble(cursor.getColumnIndex(SchemaFact.COLUMN_FACT_HOURLY_RATE)),
                cursor.getInt(cursor.getColumnIndex(SchemaFact.COLUMN_FACT_EMP_STATUS))
                );

        return fact;
    }

    public void setContentValues(Fact fact) {
        mValues = new ContentValues();
        mValues.put(SchemaEmployee.COLUMN_EMP_ID, fact.getEmp_id());
        mValues.put(SchemaDepartment.COLUMN_DEP_ID, fact.getDep_id());
        mValues.put(SchemaDocument.COLUMN_DOC_ID, fact.getDoc_id());
        mValues.put(SchemaJob.COLUMN_JOB_ID, fact.getJob_id());
        mValues.put(SchemaFact.COLUMN_FACT_SALARY, fact.getSalary());
        mValues.put(SchemaFact.COLUMN_FACT_HOURLY_RATE, fact.getHourly_rate());
        mValues.put(SchemaFact.COLUMN_FACT_EMP_STATUS, fact.getEmployment_status());
    }

    public ContentValues getContentValues() {
        return mValues;
    }
}
