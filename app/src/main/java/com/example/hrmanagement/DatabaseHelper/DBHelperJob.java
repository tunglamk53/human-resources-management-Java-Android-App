package com.example.hrmanagement.DatabaseHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.hrmanagement.Entity.Job;
import com.example.hrmanagement.DatabaseService.ServiceJob;
import com.example.hrmanagement.TableSchema.SchemaEmployee;
import com.example.hrmanagement.TableSchema.SchemaFact;
import com.example.hrmanagement.TableSchema.SchemaJob;

import java.util.ArrayList;

public class DBHelperJob implements ServiceJob {

    //Initialize Parameters
    private SQLiteDatabase mDb;
    private Cursor cursor;
    private ContentValues mValues;

    //Job Constructor
    public DBHelperJob(SQLiteDatabase db) {
        this.mDb = db;
    }

    @Override
    public Job fetchJobById(int jobId) { return null; }


    @Override
    public Job fetchJobByEmpId(int empId) {
        Job job = null;
        try{
            cursor = mDb.query(
                    SchemaFact.TABLE_FACT + " INNER JOIN " + SchemaJob.TABLE_JOB + " ON " +
                            SchemaFact.TABLE_FACT + "." + SchemaJob.COLUMN_JOB_ID + "=" + SchemaJob.TABLE_JOB + "." + SchemaJob.COLUMN_JOB_ID,
//                    "TAB_FACT.job_id=TAB_JOB.job_id",
                    new String[] { SchemaJob.TABLE_JOB + "." + SchemaJob.COLUMN_JOB_ID, SchemaJob.TABLE_JOB + "." + SchemaJob.COLUMN_JOB_TITLE, SchemaJob.TABLE_JOB + "." + SchemaJob.COLUMN_JOB_DESCRIPTION},
                    SchemaFact.TABLE_FACT + "." + SchemaEmployee.COLUMN_EMP_ID + " = ? ",
                    new String[] { String.valueOf(empId) },
                    null, null, null
            );

            if(cursor != null) {
                cursor.moveToFirst();
                job = convertCursorToEntity(cursor);
            }
            cursor.close();
            return job;
        }catch (Exception e) {
            return null;
        }
    }

    //Fetch All Jobs
    @Override
    public ArrayList<Job> fetchAllJobs() {
        ArrayList<Job> jobList = new ArrayList<>();
        try {
            cursor = mDb.query(
                    SchemaJob.TABLE_JOB,
                    SchemaJob.JOB_COLUMNS,
                    null, null, null, null,
                    SchemaJob.COLUMN_JOB_ID);

            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    Job job = convertCursorToEntity(cursor);
                    jobList.add(job);
                } while (cursor.moveToNext());
                cursor.close();
            }
            return jobList;
        } catch (Exception e){
            return null;
        }
    }

    //Add An Job
    @Override
    public boolean addJob(Job job) {
        setContentValues(job);
        try{
            mDb.insert(SchemaJob.TABLE_JOB, null, getContentValues());
            return true;
        }catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteJob(int jobId) {
        return false;
    }

    @Override
    public boolean updateJob(Job job) {
        return false;
    }






    public Job convertCursorToEntity(Cursor cursor) {
        Job job =
                new Job(
                        cursor.getInt(cursor.getColumnIndex(SchemaJob.COLUMN_JOB_ID)),
                        cursor.getString(cursor.getColumnIndex(SchemaJob.COLUMN_JOB_TITLE)),
                        cursor.getString(cursor.getColumnIndex(SchemaJob.COLUMN_JOB_DESCRIPTION))
                );
        return job;
    }

    public void setContentValues(Job job) {
        mValues = new ContentValues();
        mValues.put(SchemaJob.COLUMN_JOB_TITLE, job.getJob_title());
        mValues.put(SchemaJob.COLUMN_JOB_DESCRIPTION, job.getJob_description());
    }

    public ContentValues getContentValues() {
        return mValues;
    }
}
