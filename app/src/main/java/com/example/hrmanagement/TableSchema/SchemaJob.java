package com.example.hrmanagement.TableSchema;

public interface SchemaJob {
    public static final String TABLE_JOB = "TAB_JOB";
    static final String COLUMN_JOB_ID = "job_id";
    static final String COLUMN_JOB_TITLE = "job_title";
    static final String COLUMN_JOB_DESCRIPTION = "job_description";


    public static final String SQL_CREATE_JOB = "CREATE TABLE IF NOT EXISTS "
            + TABLE_JOB
            + " ("
            + COLUMN_JOB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_JOB_TITLE + " TEXT, "
            + COLUMN_JOB_DESCRIPTION + " TEXT"
            + ")";

    public static final String SQL_DROP_JOB = "DROP TABLE IF EXISTS " + TABLE_JOB;

    String[] JOB_COLUMNS = new String[] { COLUMN_JOB_ID, COLUMN_JOB_TITLE, COLUMN_JOB_DESCRIPTION };
}
