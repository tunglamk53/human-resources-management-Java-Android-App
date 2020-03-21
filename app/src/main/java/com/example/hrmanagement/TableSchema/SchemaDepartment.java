package com.example.hrmanagement.TableSchema;

public interface SchemaDepartment {
    static final String TABLE_DEPARTMENT = "TAB_DEPARTMENT";
    static final String COLUMN_DEP_ID = "dep_id";
    static final String COLUMN_DEP_NAME = "dep_name";
    static final String COLUMN_DEP_LOCATION = "dep_location";


    public static final String SQL_CREATE_DEPARTMENT = "CREATE TABLE IF NOT EXISTS "
            + TABLE_DEPARTMENT
            + " ("
            + COLUMN_DEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DEP_NAME + " TEXT, "
            + COLUMN_DEP_LOCATION + " TEXT"
            + ")";

    public static final String SQL_DROP_DEPARTMENT = "DROP TABLE IF EXISTS " + TABLE_DEPARTMENT;

    String[] DEPARTMENT_COLUMNS = new String[] { COLUMN_DEP_ID, COLUMN_DEP_NAME, COLUMN_DEP_LOCATION };
}