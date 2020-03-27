package com.example.hrmanagement.DatabaseController;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.ExecutionException;

public class DatabaseOperation {

    private SQLiteDatabase mDb;
    private Context context;
    private AsyncTaskHelper asyncTaskHelper;

    public DatabaseOperation(Context context) {
        this.context = context;
    }

    public SQLiteDatabase openDb() {
        //Initialize AsyncTask in Background running
        asyncTaskHelper = new AsyncTaskHelper();
        //Execute AsyncTask in Background running
        asyncTaskHelper.execute(context);

        //Get Database in Background running
        try {
            //Return Database
            return mDb = asyncTaskHelper.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Close Database
    public void closeDb() {
        if (mDb != null)
            mDb.close();
    }

}
