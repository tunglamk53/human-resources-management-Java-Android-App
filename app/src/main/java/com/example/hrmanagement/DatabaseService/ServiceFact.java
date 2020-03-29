package com.example.hrmanagement.DatabaseService;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.hrmanagement.Entity.Fact;

import java.util.ArrayList;

public interface ServiceFact {

    public Fact fetchFactById(int emp_id);

    public ArrayList<Fact> fetchAllFacts();

    public boolean addFact(Fact fact);

    public boolean deleteFact(int emp_id, int dep_id, int doc_id, int job_id);

    public void updateFact(Fact fact);





    public Fact convertCursorToEntity(Cursor cursor);

    public void setContentValues(Fact fact);

    public ContentValues getContentValues();
}
