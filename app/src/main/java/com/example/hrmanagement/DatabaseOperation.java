package com.example.hrmanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseOperation extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hrdatabase.db";
    private static final int DATABASE_VERSION = 1;

//    private static final String TABLE_NAME = "TAB_EMPLOYEE";

    private static final String SQL_CREATE_EMPLOYEE =
            "CREATE TABLE TAB_EMPLOYEE (emp_id INTEGER PRIMARY KEY AUTOINCREMENT, emp_fname TEXT, emp_lname TEXT, emp_phone INTEGER, emp_address TEXT)"; //gender

    private static final String SQL_CREATE_DEPARTMENT =
            "CREATE TABLE TAB_DEPARTMENT (dep_id INTEGER PRIMARY KEY AUTOINCREMENT, dep_name TEXT, dep_location TEXT)";

    private static final String SQL_CREATE_TYPE =
            "CREATE TABLE TAB_TYPE (type_id INTEGER PRIMARY KEY AUTOINCREMENT, type_name TEXT)";

    private static final String SQL_CREATE_DOCUMENT =
            "CREATE TABLE TAB_DOCUMENT (doc_id INTEGER PRIMARY KEY AUTOINCREMENT, doc_bin INTEGER)";

    private static final String SQL_CREATE_JOB =
            "CREATE TABLE TAB_JOB (job_title TEXT PRIMARY KEY, job_description TEXT)";

//    private static final String SQL_CREATE_DATE =
//            "CREATE TABLE TAB_DATE (date_id INTEGER PRIMARY KEY, year INTEGER, month INTEGER, day INTEGER)";

    private static final String SQL_CREATE_FACT =
            "CREATE TABLE TAB_FACT (emp_id INTEGER, dep_id INTEGER, doc_id INTEGER, job_title TEXT, salary INTEGER, hourly_rate INTEGER, employment_status INTEGER, " +
                    "FOREIGN KEY(emp_id) REFERENCES TAB_EMPLOYEE(emp_id), " +
                    "FOREIGN KEY(dep_id) REFERENCES TAB_DEPARTMENT(dep_id), " +
//                    "FOREIGN KEY(date_id) REFERENCES TAB_DATE(date_id), " +
                    "FOREIGN KEY(job_title) REFERENCES TAB_JOB(job_title), " +
                    "FOREIGN KEY(doc_id) REFERENCES TAB_DOCUMENT(doc_id))";

    private static final String SQL_CREATE_DOC_TYP =
            "CREATE TABLE TAB_DOC_TYP (doc_id INTEGER, type_id INTEGER, " +
                    "FOREIGN KEY(doc_id) REFERENCES TAB_DOCUMENT(doc_id), " +
                    "FOREIGN KEY(type_id) REFERENCES TAB_TYPE(type_id))";

//    private static final String SQL_DELETE_ENTRIES =
//            "DROP TABLE IF EXISTS TAB_EMPLOYEE";



    //    public EmployeeDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
    public DatabaseOperation(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


//    @Override
//    public void onOpen(SQLiteDatabase db) {
//        super.onOpen(db);
//        if (!db.isReadOnly()) {
//            // Enable foreign key constraints
//            db.execSQL("PRAGMA foreign_keys=ON;");
//        }
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_EMPLOYEE);
        db.execSQL(SQL_CREATE_DEPARTMENT);
        db.execSQL(SQL_CREATE_DOCUMENT);
//        db.execSQL(SQL_CREATE_DATE);
        db.execSQL(SQL_CREATE_JOB);
        db.execSQL(SQL_CREATE_TYPE);
        db.execSQL(SQL_CREATE_FACT);
        db.execSQL(SQL_CREATE_DOC_TYP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TAB_EMPLOYEE");
        db.execSQL("DROP TABLE IF EXISTS TAB_DEPARTMENT");
        db.execSQL("DROP TABLE IF EXISTS TAB_DOCUMENT");
//        db.execSQL("DROP TABLE IF EXISTS TAB_DATE");
        db.execSQL("DROP TABLE IF EXISTS TAB_JOB");
        db.execSQL("DROP TABLE IF EXISTS TAB_TYPE");
        db.execSQL("DROP TABLE IF EXISTS TAB_FACT");
        db.execSQL("DROP TABLE IF EXISTS TAB_DOC_TYP");

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
//        db.execSQL("DROP TABLE IF EXISTS TAB_EMPLOYEE");
//        db.execSQL("DROP TABLE IF EXISTS TAB_DEPARTMENT");
//        db.execSQL("DROP TABLE IF EXISTS TAB_DOCUMENT");
//        db.execSQL("DROP TABLE IF EXISTS TAB_DATE");
//        db.execSQL("DROP TABLE IF EXISTS TAB_TYPE");
//        db.execSQL("DROP TABLE IF EXISTS TAB_FACT");
//        db.execSQL("DROP TABLE IF EXISTS TAB_DOC_TYP");
//        onCreate(db);
    }
}
