package com.example.hrmanagement.DatabaseController;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class AsyncTaskHelper extends AsyncTask<Context, Void, SQLiteDatabase> {

    private Context context;
    private DatabaseInitialization databaseInitialization;
    private SQLiteDatabase mDb;

    @Override
    protected SQLiteDatabase doInBackground(Context... contexts) {
        //Open and Return Database in Background
        databaseInitialization = new DatabaseInitialization(contexts[0]);
        mDb = databaseInitialization.getReadableDatabase();
        return mDb;
    }
}
