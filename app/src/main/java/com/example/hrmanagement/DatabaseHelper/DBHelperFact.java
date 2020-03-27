package com.example.hrmanagement.DatabaseHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.hrmanagement.Entity.Fact;
import com.example.hrmanagement.Service.ServiceFact;
import com.example.hrmanagement.TableSchema.SchemaDepartment;
import com.example.hrmanagement.TableSchema.SchemaDocument;
import com.example.hrmanagement.TableSchema.SchemaEmployee;
import com.example.hrmanagement.TableSchema.SchemaFact;
import com.example.hrmanagement.TableSchema.SchemaJob;

import java.util.ArrayList;

public class DBHelperFact implements ServiceFact {

    private SQLiteDatabase mDb;
    private Cursor cursor;
    private ContentValues mValues;

    public DBHelperFact(SQLiteDatabase db) {
        this.mDb = db;
    }

    @Override
    public Fact fetchFactByIds(int emp_id) {
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
    public boolean addFact(Fact fact) {
        setContentValues(fact);
        try{
            mDb.insert(SchemaFact.TABLE_FACT, null, getContentValues());
            return true;
        } catch (SQLiteException e) {
            return false;
        }
    }

    @Override
    public boolean deleteFact(int emp_id, int dep_id, int doc_id, int job_id) {
        return false;
    }

    @Override
    public boolean updateFact(Fact fact) {
        return false;
    }

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
