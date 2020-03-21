package com.example.hrmanagement.TableSchema;

public interface SchemaType {
    public static final String TABLE_TYPE = "TAB_TYPE";
    static final String COLUMN_TYP_ID = "type_id";
    static final String COLUMN_TYP_NAME = "type_name";


    public static final String SQL_CREATE_TYPE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_TYPE
            + " ("
            + COLUMN_TYP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TYP_NAME + " TEXT"
            + ")";

    public static final String SQL_DROP_TYPE = "DROP TABLE IF EXISTS " + TABLE_TYPE;

    String[] TYPE_COLUMNS = new String[] { COLUMN_TYP_ID, COLUMN_TYP_NAME };
}
