package com.example.hrmanagement.TableSchema;

public interface SchemaFact {
    static final String TABLE_FACT = "TAB_FACT";
    static final String COLUMN_FACT_SALARY = "salary";
    static final String COLUMN_FACT_HOURLY_RATE = "hourly_rate";
    static final String COLUMN_FACT_EMP_STATUS = "employment_status"; // 0 = Quited; 1 = Active.


    public static final String SQL_CREATE_FACT = "CREATE TABLE IF NOT EXISTS "
            + TABLE_FACT
            + " ("
            + SchemaEmployee.COLUMN_EMP_ID + " INTEGER, "
            + SchemaDepartment.COLUMN_DEP_ID + " INTEGER, "
            + SchemaDocument.COLUMN_DOC_ID + " INTEGER, "
            + SchemaJob.COLUMN_JOB_ID + " INTEGER, "
            + COLUMN_FACT_SALARY + " DOUBLE, "
            + COLUMN_FACT_HOURLY_RATE + " DOUBLE, "
            + COLUMN_FACT_EMP_STATUS + " INTEGER, "
            + "FOREIGN KEY(" + SchemaEmployee.COLUMN_EMP_ID + ") REFERENCES " + SchemaEmployee.TABLE_EMPLOYEE + "(" + SchemaEmployee.COLUMN_EMP_ID + "), "
            + "FOREIGN KEY(" + SchemaDepartment.COLUMN_DEP_ID + ") REFERENCES " + SchemaDepartment.TABLE_DEPARTMENT + "(" + SchemaDepartment.COLUMN_DEP_ID + "), "
//          + "FOREIGN KEY(date_id) REFERENCES TAB_DATE(date_id), "
            + "FOREIGN KEY(" + SchemaJob.COLUMN_JOB_ID + ") REFERENCES " + SchemaJob.TABLE_JOB + "(" + SchemaJob.COLUMN_JOB_ID + "), "
            + "FOREIGN KEY(" + SchemaDocument.COLUMN_DOC_ID + ") REFERENCES " + SchemaDocument.TABLE_DOCUMENT + "(" + SchemaDocument.COLUMN_DOC_ID + ")"
            + ")";

    public static final String SQL_DROP_FACT = "DROP TABLE IF EXISTS " + TABLE_FACT;

    String[] FACT_COLUMNS = new String[]
            {
                SchemaEmployee.COLUMN_EMP_ID,
                SchemaDepartment.COLUMN_DEP_ID,
                SchemaDocument.COLUMN_DOC_ID,
                SchemaJob.COLUMN_JOB_ID,
                COLUMN_FACT_SALARY,
                COLUMN_FACT_HOURLY_RATE,
                COLUMN_FACT_EMP_STATUS
            };


}