package com.example.hrmanagement.DatabaseController;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.hrmanagement.TableSchema.*;

public class DatabaseOperation extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "hrdatabase.db";
    private static final int DATABASE_VERSION = 1;
//    private SQLiteDatabase wdb, rdb;


    //    public EmployeeDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
    public DatabaseOperation(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.getReadableDatabase();
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
        db.execSQL(SchemaEmployee.SQL_CREATE_EMPLOYEE);
        db.execSQL(SchemaDepartment.SQL_CREATE_DEPARTMENT);
        db.execSQL(SchemaDocument.SQL_CREATE_DOCUMENT);
//        db.execSQL(DatabaseSQLDefinition.SQL_CREATE_DATE);
        db.execSQL(SchemaJob.SQL_CREATE_JOB);
        db.execSQL(SchemaType.SQL_CREATE_TYPE);
        db.execSQL(SchemaFact.SQL_CREATE_FACT);
        db.execSQL(SchemaDocTyp.SQL_CREATE_DOC_TYP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SchemaEmployee.SQL_DROP_EMPLOYEE);
        db.execSQL(SchemaDepartment.SQL_DROP_DEPARTMENT);
        db.execSQL(SchemaDocument.SQL_DROP_DOCUMENT);
//        db.execSQL("DROP TABLE IF EXISTS TAB_DATE");
        db.execSQL(SchemaJob.SQL_DROP_JOB);
        db.execSQL(SchemaType.SQL_DROP_TYPE);
        db.execSQL(SchemaFact.SQL_DROP_FACT);
        db.execSQL(SchemaDocTyp.SQL_DROP_DOC_TYP);

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
