package com.example.hrmanagement.TableSchema;

public interface SchemaEmployee {

    static final String TABLE_EMPLOYEE = "TAB_EMPLOYEE";
    static final String COLUMN_EMP_ID = "emp_id";
    static final String COLUMN_EMP_FNAME = "emp_fname";
    static final String COLUMN_EMP_LNAME = "emp_lname";
    static final String COLUMN_EMP_PHONE = "emp_phone";
    static final String COLUMN_EMP_ADDRESS = "emp_address";


    public static final String SQL_CREATE_EMPLOYEE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_EMPLOYEE
            + " ("
            + COLUMN_EMP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EMP_FNAME + " TEXT, "
            + COLUMN_EMP_LNAME + " TEXT, "
            + COLUMN_EMP_PHONE + " INTEGER, "
            + COLUMN_EMP_ADDRESS + " TEXT"
            + ")";

    public static final String SQL_DROP_EMPLOYEE = "DROP TABLE IF EXISTS " + TABLE_EMPLOYEE;

    String[] EMPLOYEE_COLUMNS = new String[] { COLUMN_EMP_ID, COLUMN_EMP_FNAME, COLUMN_EMP_LNAME, COLUMN_EMP_PHONE, COLUMN_EMP_ADDRESS };

    String[] EMPLOYEE_COLUMNS_DEP_NAMES = new String[] {
            TABLE_EMPLOYEE+"."+COLUMN_EMP_ID,
            TABLE_EMPLOYEE+"."+COLUMN_EMP_FNAME,
            TABLE_EMPLOYEE+"."+COLUMN_EMP_LNAME,
            TABLE_EMPLOYEE+"."+COLUMN_EMP_PHONE,
            TABLE_EMPLOYEE+"."+COLUMN_EMP_ADDRESS,
            SchemaDepartment.TABLE_DEPARTMENT+"."+SchemaDepartment.COLUMN_DEP_NAME};

}
