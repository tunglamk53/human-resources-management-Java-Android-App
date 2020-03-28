package com.example.hrmanagement.DatabaseHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.hrmanagement.Entity.Department;
import com.example.hrmanagement.DatabaseService.ServiceDepartment;
import com.example.hrmanagement.TableSchema.SchemaDepartment;
import com.example.hrmanagement.TableSchema.SchemaEmployee;
import com.example.hrmanagement.TableSchema.SchemaFact;

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
    public Department fetchDepartmentByEmpId(int empId) {
        Department department = null;
        try{
            cursor = mDb.query(
                    SchemaFact.TABLE_FACT + " INNER JOIN " + SchemaDepartment.TABLE_DEPARTMENT + " ON " +
                            SchemaFact.TABLE_FACT + "." + SchemaDepartment.COLUMN_DEP_ID + "=" + SchemaDepartment.TABLE_DEPARTMENT + "." + SchemaDepartment.COLUMN_DEP_ID,
                    new String[] { SchemaDepartment.TABLE_DEPARTMENT + "." + SchemaDepartment.COLUMN_DEP_ID, SchemaDepartment.TABLE_DEPARTMENT + "." + SchemaDepartment.COLUMN_DEP_NAME, SchemaDepartment.TABLE_DEPARTMENT + "." + SchemaDepartment.COLUMN_DEP_LOCATION},
                    SchemaFact.TABLE_FACT + "." + SchemaEmployee.COLUMN_EMP_ID + " = ? ",
                    new String[] { String.valueOf(empId) },
                    null, null, null
            );

            if(cursor != null) {
                cursor.moveToFirst();
                department = convertCursorToEntity(cursor);
            }
            cursor.close();
            return department;
        }catch (Exception e) {
            return null;
        }
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
