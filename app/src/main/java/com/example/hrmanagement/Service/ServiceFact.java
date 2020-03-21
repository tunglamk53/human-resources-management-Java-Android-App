package com.example.hrmanagement.Service;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.hrmanagement.Entity.Fact;

import java.util.ArrayList;

public interface ServiceFact {
    public Fact fetchJobByIds(int emp_id, int dep_id, int doc_id, int job_id);

    public ArrayList<Fact> fetchAllFacts();

    public boolean addFact(Fact fact);

    public boolean deleteFact(int emp_id, int dep_id, int doc_id, int job_id);

    public boolean updateFact(Fact fact);





    public Fact convertCursorToEntity(Cursor cursor);

    public void setContentValues(Fact fact);

    public ContentValues getContentValues();
}
