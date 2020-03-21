package com.example.hrmanagement.DatabaseHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.hrmanagement.Entity.Department;
import com.example.hrmanagement.Service.ServiceDepartment;
import com.example.hrmanagement.TableSchema.SchemaDepartment;

import java.util.ArrayList;

public class DBHelperDepartment implements ServiceDepartment {

    private SQLiteDatabase mDb;
    private Cursor cursor;
    private ContentValues mValues;

    public DBHelperDepartment(SQLiteDatabase db) {
        this.mDb = db;
    }

    @Override
    public Department fetchDepartmentById(int departmentId) {
        return null;
    }

    @Override
    public ArrayList<Department> fetchAllDepartments() {
        ArrayList<Department> departmentList = new ArrayList<>();
        try {
            cursor = mDb.query(
                    SchemaDepartment.TABLE_DEPARTMENT,
                    SchemaDepartment.DEPARTMENT_COLUMNS,
                    null, null, null, null,
                    SchemaDepartment.COLUMN_DEP_ID);

            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    Department dep = convertCursorToEntity(cursor);
                    departmentList.add(dep);
                } while (cursor.moveToNext());
                cursor.close();
            }
            return departmentList;
        } catch (Exception e){
            return null;
        }
    }



    @Override
    public boolean addDepartment(Department department) {
        setContentValues(department);
        try{
            mDb.insert(SchemaDepartment.TABLE_DEPARTMENT, null, getContentValues());
            return true;
        } catch (SQLException e) {
            return false;
        }
    }



    @Override
    public boolean deleteDepartment(int departmentId) {
        return false;
    }

    @Override
    public boolean updateDepartment(Department department) {
        return false;
    }










/**--------------------------------------------------------------------------------------------*/
    public Department convertCursorToEntity(Cursor cursor) {
        Department department =
                new Department(
                        cursor.getInt(cursor.getColumnIndex(SchemaDepartment.COLUMN_DEP_ID)),
                        cursor.getString(cursor.getColumnIndex(SchemaDepartment.COLUMN_DEP_NAME)),
                        cursor.getString(cursor.getColumnIndex(SchemaDepartment.COLUMN_DEP_LOCATION))
                );
        return department;
    }

    public void setContentValues(Department department) {
        mValues = new ContentValues();
        mValues.put(SchemaDepartment.COLUMN_DEP_NAME, department.getDep_name());
        mValues.put(SchemaDepartment.COLUMN_DEP_LOCATION, department.getDep_location());
    }

    public ContentValues getContentValues() {
        return mValues;
    }
}
