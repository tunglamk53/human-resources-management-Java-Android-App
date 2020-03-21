package com.example.hrmanagement.Service;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.hrmanagement.Entity.Job;

import java.util.ArrayList;

public interface ServiceJob {
    public Job fetchJobById(int jobId);

    public ArrayList<Job> fetchAllJobs();

    public boolean addJob(Job job);

    public boolean deleteJob(int jobId);

    public boolean updateJob(Job job);





    public Job convertCursorToEntity(Cursor cursor);

    public void setContentValues(Job job);

    public ContentValues getContentValues();

}
